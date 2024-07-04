import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class SummaryService {

  baseUrl = "http://10.0.0.38:8082/api/";

  constructor(private http: HttpClient,private loginService : LoginService) { }

  getMyTeam():Observable<any>{
    const url = this.baseUrl + "getByAllEmployeeByEmployeeRportsTo/" + this.loginService.getEmployeeData().employeeId;
    console.log("myTeam url - ",url);
    return this.http.get<any>(url);
  }
}
