import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OtpService {
  private apiUrl = 'http://localhost:8082/api/sendMail';
  private isAuthenticated: boolean = false;

  constructor(private http: HttpClient) { }
  sendOtpEmail(toEmail: string): Observable<any> {
    this.isAuthenticated = true
    return this.http.get<any>(`${this.apiUrl}?toMail=${toEmail}`);
  }

  setUnAuthenticatedUser(){
    this.isAuthenticated = false;
  }
  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }
}
