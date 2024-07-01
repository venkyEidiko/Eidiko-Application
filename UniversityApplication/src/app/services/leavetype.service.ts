

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

interface LeaveStats {
  consumedLeave: number;
  availableLeave: number;
  totalLeave: number
}

@Injectable({
  providedIn: 'root'
})
export class LeavetypeService {

  private apiUrl = 'http://10.0.0.38:8082/leave/getEmpLeaveSummaryByEmpId'

  constructor(private http: HttpClient, private loginService: LoginService) { }
   employee = this.loginService.getEmployeeData();
   employeeId=this.employee.employeeId;
  fetchLeaveBalance(employeeId: number): Observable<LeaveStats> {
   console.log("fetch leave balance",employeeId)
    return this.http.get<LeaveStats>(`${this.apiUrl}/${this.employeeId}`);
  }
  fetchMonthlyLeaveData(employeeId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${this.employeeId}`);
  }
  getEmployeeLeaveData(employeeId: number): Observable<any> {

    return this.http.get<any>(`${this.apiUrl}/${this.employeeId}`);
  }
  private apiUrl1 = 'http://10.0.0.38:8082'
  fetchLeaveData(employeeId: number, pageNumber: number, pageSize: number): Observable<any> {
   console.log("employee id in leavetpye service: ",this.employeeId)
    let params = new HttpParams()
      .set('employeeId', employeeId)
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString());

    
    return this.http.get<any>(`${this.apiUrl1}/leave/getAllEmpLeave`, { params: params });
  }
}
