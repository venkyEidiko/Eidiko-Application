import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private resetPasswordUrl = 'http://10.0.0.63:2000/password/forgotPassword'; 

  constructor(private http: HttpClient) { }

  resetPassword(password: string): Observable<any> {
   
    return this.http.post<any>(this.resetPasswordUrl, { password });
  }
}
