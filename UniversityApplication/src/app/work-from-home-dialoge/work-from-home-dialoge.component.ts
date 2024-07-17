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
import { DatePipe } from '@angular/common';
import { SnackbarService } from '../snackbar.service';

interface Employee {
  employeeId: number;
  firstName: string;
  lastName: string;
  email: string;
}


@Component({
  selector: 'app-work-from-home-dialoge',
  templateUrl: './work-from-home-dialoge.component.html',
  styleUrls: ['./work-from-home-dialoge.component.css']
})
export class WorkFromHomeDialogeComponent {
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

  constructor(
    private dialogRef: MatDialogRef<WorkFromHomeDialogeComponent>,
    private leaveRequestService: LeavereqService,
    private http: HttpClient,
    private loginService:LoginService,
    private datePipe: DatePipe,
    private snackbarservice:SnackbarService
  ) {}

  ngOnInit(): void {
    
const employee=this.loginService.getEmployeeData();
   this.employeeId=employee.employeeId;
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
    let fromhalfRequest;
    let toHalfrequest;
    if(this.selectedHalfDay == "fullDay"){
      fromhalfRequest = 'first half';
      toHalfrequest = 'secondhalf';
    }
    else{
      fromhalfRequest = this.selectedHalfDay;
      toHalfrequest = this.selectedHalfDay;
    }
    console.log("date - ",this.fromDate );
    
    const leave: WorkFromHomeRequest = {
      fromDate: this.datePipe.transform(this.fromDate,"yyyy-MM-dd"),
      toDate: this.datePipe.transform(this.toDate,"yyyy-MM-dd"),
      requestType: "WORK FROM HOME",
      notify: this.notifyTo.value,
      reason: this.leaveNote,
      employeeId: this.employeeId,
      fromHalf: fromhalfRequest,
      toHalf: toHalfrequest
    };

    console.log("work from request body - ",leave);
    

    this.leaveRequestService.workfromHomeRequest(leave).subscribe(
      (response) => {

        console.log('Wfh  submitted successfully', response);
        if (response.error == null && response.statusCode == 201)
        {
          this.snackbarservice.showSuccess("Work from home request sent!")
        }
       this.dialogRef.close();
      },
      (error) => {
        console.error('Error submitting request', error);
        this.snackbarservice.showError("Wfh request not sent try again!")
      }
    );
  }
  dateFilter = (date: Date | null): boolean => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return date ? date >= today : false;
  }

  calculateDays(): number {
    if (this.fromDate && this.toDate) {
      const diffTime = this.toDate.getTime() - this.fromDate.getTime();
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      return diffDays+1;
    }
    return 0;
  }
  disableddata() {
    console.log("inside  disabled button")
    
    if (this.calculateDays() <= 0) {
      return true;
    } else {
      return false;
    }
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
    this.notifyTo.setValue(`${employee.firstName} ${employee.lastName}`);
    this.searchResults = [];
  }
}
export interface WorkFromHomeRequest {
    "fromDate": any,
    "toDate": any,
    "requestType": string,
    "notify": any,
    "fromHalf":any,
    "toHalf":any,
    "reason": any,
    "employeeId": any
}