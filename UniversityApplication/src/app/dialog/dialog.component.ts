import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { LeavereqService } from '../services/leavereq.service';
import { LeaveRequest } from '../leaverequest';
import { debounceTime, switchMap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

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
  employeeId: number = 2001;
  showCustomDropdown: boolean = false;
  selectedHalfDay: string = 'fullDay';
  searchResults: Employee[] = [];

  constructor(
    private dialogRef: MatDialogRef<DialogComponent>,
    private leaveRequestService: LeavereqService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
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

  submitRequest(): void {
    const leave: LeaveRequest = {
      leaveDates: new Date(),
      fromDate: this.fromDate,
      toDate: this.toDate,
      status: 'string',
      leaveType: this.leaveType,
      requestedBy: 'string',
      notifyTo: [this.notifyTo.value],
      actionTakenBy: 'any',
      customDayStatus: this.selectedHalfDay,
      leaveNote: this.leaveNote,
      rejectionReason: 'any',
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
      return diffDays;
    }
    return 0;
  }

  searchEmployees(keyword: string): Observable<Employee[]> {
    if (!keyword.trim()) {
     
      return of([]);
    }

    const isNumeric = /^\d+$/.test(keyword);
    const apiUrl = isNumeric 
      ? `http://10.0.0.81:8082/api/searchByKeyword/${keyword}`
      : `http://10.0.0.81:8082/api/searchByKeyword/${keyword}`;
      
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
    this.notifyTo.setValue(`${employee.firstName} ${employee.lastName}`);
    this.searchResults = [];
  }
}