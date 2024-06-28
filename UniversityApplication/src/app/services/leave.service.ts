// leave.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LeaveService {

  private apiUrl = 'http://10.0.0.38:8082/leave/getAllEmpLeave'; 

  constructor(private http: HttpClient) { }

  
}
