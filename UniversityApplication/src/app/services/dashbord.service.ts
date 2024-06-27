import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';
import { BirthdayResponse } from '../dashboard/dashboard-interface';

@Injectable({
  providedIn: 'root'
})
export class DashbordService {

  constructor(private http:HttpClient,private loginService: LoginService) { }

  getWorkFromHome():Observable<any[]>{
    return this.http.get<any>("http://localhost:8080/api/getemployeesdata/Work From Home");

  }

  getHolidays():Observable<any[]>{
    return this.http.get<any>("http://10.0.0.81:8082/api/getAllHolidays");

  }
  getLeaveData():Observable<any[]>{
    const empId = this.getEmpId();
    console.log("employeeid - ",empId);
    
    return this.http.get<any>("http://10.0.0.81:8082/leave/getEmpLeaveSummaryByEmpId/1102");
  }

  getEmpId(){
    return this.loginService.getEmployeeData().employeeId;
  }
  
  // this url is for birthday and anniversary in dashboard
  getBirthdayAndAnniversary():Observable<BirthdayResponse>{
   
    return this.http.get<BirthdayResponse>("http://localhost:8080/api/getBirthDayAnniversaryTodayList");
  }

  getPosts():Observable<any>
  {
     console.log("post service");
     return this.http.get<any>("http://localhost:8080/posts/getAllPostByTime");
  }
  
}
