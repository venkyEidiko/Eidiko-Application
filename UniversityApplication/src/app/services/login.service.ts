import { Injectable } from '@angular/core';
import { tap, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private employeeData: any = null; 
  private jwtToken: string | null = null;


  url = "http://10.0.0.81:8082/";
  constructor(private http: HttpClient) {}
  register(data: any): Observable<any> {
    return this.http.post<any>(this.url+"api/save", data);
  }

  login(data: any): Observable<any> {
    return this.http.post<any>(this.url+"login1", data);
  }

  setEmployeeData(data: any) {
    this.employeeData = data;
    localStorage.setItem('employee-data', JSON.stringify(data));
  }
searchEmployee(search:any):Observable<any[]>{
  return this.http.get<any[]>(this.url+"api/searchByKeyword/"+search)
};
  getEmployeeData(): any {
    if (!this.employeeData) {
      const storedData = localStorage.getItem('employee-data');
      if (storedData) {
        try {
          this.employeeData = JSON.parse(storedData);
        } catch (error) {
          console.error('Error parsing employee data from localStorage:', error);
          
        }
        }
        }
      return this.employeeData;
  }
  

  setJwtToken(token: string) {
    this.jwtToken = token;
    localStorage.setItem('jwt-token', token);
  }

  getJwtToken(): string | null {
    if (!this.jwtToken) {
      this.jwtToken = localStorage.getItem('jwt-token');
    }
    return this.jwtToken;
  }

  getAuthHeaders(): HttpHeaders {
    const token = this.getJwtToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }
}

