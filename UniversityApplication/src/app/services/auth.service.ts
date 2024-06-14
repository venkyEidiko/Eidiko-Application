import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:2000/password/forgotPassword';

  constructor(private http: HttpClient) { }

  resetPassword(requestBody: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, requestBody);
    
  }
}
