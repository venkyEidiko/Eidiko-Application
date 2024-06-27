// leave.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LeaveService {

  private apiUrl = 'http://10.0.0.73:8082/leave/getAllEmpLeave'; 

  constructor(private http: HttpClient) { }

  fetchLeaveData(employeeId: number, pageNumber: number, pageSize: number): Observable<any> {
   
    let params = new HttpParams()
      .set('employeeId', employeeId.toString())
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString());

    
    return this.http.get<any>(`${this.apiUrl}`, { params: params });
  }
}
