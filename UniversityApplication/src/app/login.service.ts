import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ObservableInput } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url = "http://localhost:8081/api/save";

  constructor(private http:HttpClient) { }

  register(data:any):Observable<any>{
    return this.http.post<any>(this.url,data);
  }
}
