import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://10.0.0.38:8082/api/password/forgotPassword';
  private isAuthenticated: boolean = false;

  constructor(private http: HttpClient) { }

  resetPassword(requestBody: any): Observable<any> {
    this.isAuthenticated = true
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    return this.http.post<any>(this.apiUrl, requestBody, httpOptions).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Failed to reset password. Please try again later.';
        if (error.error instanceof ErrorEvent) {

          errorMessage = `An error occurred: ${error.error.message}`;
        } else {
          
          if (error.status === 200 && error.statusText === 'OK') {
            
          
            return throwError('Password updated successfully');
          } else {
            console.error(`Server returned error code ${error.status}, message: ${error.error}`);
          }
        }
        return throwError(errorMessage);
      })
    ); 

  }

  setUnAuthenticatedUser(){
    this.isAuthenticated = false;
  }
  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }

}
