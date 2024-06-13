import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmailCheckService{
  constructor(private http: HttpClient) { }

  checkEmail(email: string) {
  
    return this.http.get<any>(`http://10.0.0.81:8080/api/getByEmail/${email}`);
  }
}
