

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface LeaveStats {
  consumedLeave: number;
  availableLeave: number;
  totalLeave:number
}

@Injectable({
  providedIn: 'root'
})
export class LeavetypeService {

  private apiUrl = 'http://10.0.0.81:8082/leave/getEmpLeaveSummaryByEmpId'

  constructor(private http: HttpClient) {}

  fetchLeaveBalance(employeeId:number): Observable<LeaveStats> {
    return this.http.get<LeaveStats>(`${this.apiUrl}/${employeeId}`);
  }
  fetchMonthlyLeaveData(employeeId:number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${employeeId}`);
  }

}
