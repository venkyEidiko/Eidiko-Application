import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})


  export class DataService {
    private apiUrl = 'http://10.0.0.38:8082/leave/getEmpLeaveSummaryByEmpId/2001'
  
    constructor(private http: HttpClient) { }
  
    getDateStrings(): Observable<string[]> {
      return this.http.get<string[]>(this.apiUrl);
    }
  }