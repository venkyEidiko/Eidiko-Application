import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ObservableInput } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url = "http://10.0.0.52:8080/api/save";
  loginUrl = "http://10.0.0.52:8080/login1";

  constructor(private http:HttpClient) { }

  register(data:any):Observable<any>{
    return this.http.post<any>(this.url,data);
  }
  login(data:any):Observable<any> {
    return this.http.post<any>(this.loginUrl,data);
  }
}
