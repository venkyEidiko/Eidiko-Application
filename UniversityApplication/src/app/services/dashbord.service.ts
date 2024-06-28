import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

import { HolidayDialogComponent } from '../holiday-dialog/holiday-dialog.component';
import { MatDialog } from '@angular/material/dialog';


@Injectable({
  providedIn: 'root'
})
export class DashbordService {

  constructor(private http: HttpClient, private loginService: LoginService, private dialog: MatDialog,) { }
  url = "http://10.0.0.38:8082/api/";
  getWorkFromHome(): Observable<any[]> {
    return this.http.get<any>(this.url + "getemployeesdata/Work From Home");

  }


  getHolidays(): Observable<any[]> {
    return this.http.get<any>(this.url + "getAllHolidays");

  }
  getLeaveData(): Observable<any[]> {
    const empId = this.getEmpId();

    console.log("employeeid - ", empId);


    console.log("employeeid - ", empId);

    return this.http.get<any>("http://10.0.0.38:8082/leave/getEmpLeaveSummaryByEmpId/1102");
  }

  getEmpId() {
    return this.loginService.getEmployeeData().employeeId;
  }


  getPosts(): Observable<any[]> {

    return this.http.get<any>("http://localhost:8082/posts/getAllPostByTime");
  }

  getBirthdays(): Observable<any[]> {

    console.log("------------------------------------");

    return this.http.get<any[]>("http://10.0.0.60:8080/api/todayAndNextSevenDaysBirthdaysList");
  }






openDialog(): void {
  const dialogRef = this.dialog.open(HolidayDialogComponent, {
    width: '600px'
  });

  dialogRef.afterClosed().subscribe(result => {
    // if (mainContent) {
    //   this.renderer.removeClass(mainContent, 'blur');
    // }
  });
}
}


