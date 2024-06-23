import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class LeavereqService {

 private apiUrl = 'http://10.0.0.81:8082/leave/saveEmpLeave'

  constructor(private http: HttpClient) {}

  submitLeaveRequest(requestData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, requestData);
  }
}
