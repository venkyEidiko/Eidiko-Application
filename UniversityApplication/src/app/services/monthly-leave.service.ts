import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MonthlyLeaveService {

  private apiUrl = 'http://10.0.0.73:8082/leave/getEmpLeaveSummaryByEmpId'

  constructor(private http: HttpClient) {}
  fetchMonthlyChartData(employeeId:number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${employeeId}`);
  }
}
