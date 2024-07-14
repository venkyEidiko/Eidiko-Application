
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { LeavereqService } from '../services/leavereq.service';
import { LeaveRequest } from '../leaverequest';
import { debounceTime, switchMap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { LoginService } from '../services/login.service';

interface Employee {
  employeeId: number;
  firstName: string;
  lastName: string;
  email: string;
}


@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})

export class DialogComponent implements OnInit {
  isOpen: boolean = true;
  fromDate!: Date;
  toDate!: Date;
  leaveType: string = '';
  leaveNote!: string;
  notifyTo = new FormControl();
  employeeId:number=0
  showCustomDropdown: boolean = false;
  selectedHalfDay: string = 'fullDay';
  searchResults: Employee[] = [];
fromDateInput: any;

  constructor(
    private dialogRef: MatDialogRef<DialogComponent>,
    private leaveRequestService: LeavereqService,
    private http: HttpClient,
    private loginService:LoginService
  ) {}
employee:any='';
  ngOnInit(): void {
    
 this.employee=this.loginService.getEmployeeData();
   this.employeeId=this.employee?.employeeId;
    this.notifyTo.valueChanges.pipe(
      debounceTime(300), 
      switchMap(value => this.searchEmployees(value))
    ).subscribe(results => {
      this.searchResults = results;
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  toggleCustomDropdown(): void {
    this.showCustomDropdown = !this.showCustomDropdown;
  }

  status:string='';
  actionTakenBy:string='';
  rejectionReason:string='';         
  submitRequest(): void {
    const leave: LeaveRequest = {
      leaveDates: new Date(),
      fromDate: this.fromDate,
      toDate: this.toDate,
      status: this.status,
      leaveType: this.leaveType,
      requestedBy: this.employee?.firstName +" "+this.employee.lastName,
      notifyTo: [this.notifyTo.value],
      actionTakenBy: this.actionTakenBy,
      customDayStatus: this.selectedHalfDay,
      leaveNote: this.leaveNote,
      rejectionReason: this.rejectionReason,
      employeeId: this.employeeId
    };

    this.leaveRequestService.submitLeaveRequest(leave).subscribe(
      (response) => {
        console.log('Request submitted successfully', response);
        this.dialogRef.close();
      },
      (error) => {
        console.error('Error submitting request', error);
      }
    );
  }

  calculateDays(): number {
    if (this.fromDate && this.toDate) {
      const diffTime = Math.abs(this.toDate.getTime() - this.fromDate.getTime());
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      return diffDays+1;
    }
    return 0;
  }

  searchEmployees(keyword: string): Observable<Employee[]> {
    if (!keyword.trim()) {
      return of([]);
    }

    const apiUrl =`http://localhost:8082/api/searchByKeyword/${keyword}`;

      
    return this.http.get<any>(apiUrl).pipe(
      map(response => response.status === 'SUCCESS' ? response.result[0] : []),
      catchError(this.handleError<Employee[]>('searchEmployees', []))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

  selectEmployee(employee: Employee): void {
    this.notifyTo.setValue(employee.employeeId);
    this.searchResults = [];
  }

}

