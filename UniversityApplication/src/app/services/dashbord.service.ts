import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

    return this.http.get<any>("http://10.0.0.38:8082/leave/getEmpLeaveSummaryByEmpId/1102");
  }

  getEmpId() {
    return this.loginService.getEmployeeData().employeeId;
  }

  submitPostRequest(requestData: any,file:File|null): Observable<any> {
    let params = new HttpParams();
    const formData = new FormData();

    // Append requestData fields
    formData.append('description', requestData.description);
    formData.append('postType', requestData.postType);
    formData.append('mentionEmployee', requestData.mentionEmployee);
    formData.append('postEmployee', requestData.postEmployee);

    // Append file if it exists
    if (file) {
      formData.append('file', file, file.name);
    }

   
   

console.log("submitPostRequestData requestData : ",requestData)
    return this.http.post<any>(`http://10.0.0.60:8080/posts/saveimage`, formData);
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