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
  private apiUrl1 = 'http://10.0.0.60:8080/posts/savelike'; 
  private apiUrl2='http://10.0.0.60:8080/posts/saveComment'

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

  getAllPosts():Observable<any[]>
  {
    return this.http.get<any>("http://10.0.0.38:8082/posts/getAllPostByTime")
  }
  saveLike(postId: number, emojiId: number, empId: number): Observable<any> {
    const url = `${this.apiUrl1}/${postId}`;
    const body = {
      emoji: emojiId,
      empId: empId
    };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post(url, body, { headers });
  }
  postComment(postId: number, comment: string, empId: number): Observable<any> {
    const payload = { comment, empId };
    return this.http.post(`${this.apiUrl2}/${postId}`, payload);
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
