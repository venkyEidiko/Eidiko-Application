import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, forkJoin } from 'rxjs';

import { LoginService } from './login.service';

import { HolidayDialogComponent } from '../holiday-dialog/holiday-dialog.component';
import { MatDialog } from '@angular/material/dialog';

import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DashbordService {
  private apiUrl1 = 'http://localhost:8082/';
  private apiUrl2 = 'http://localhost:8082/posts/saveComment'
  private apiUrl3 = 'http://localhost:8082/posts/saveimage'
  private apiUrl4 = 'http://localhost:8082/posts/savelike/${postId}'

  constructor(private http: HttpClient, private loginService: LoginService, private dialog: MatDialog,) { }
  url = "http://localhost:8082/";

  getWorkFromHome(): Observable<any[]> {
    return this.http.get<any>(this.url + "api/getemployeesdata/Work From Home");


  }

  getOnLeaveToday(): Observable<any> {
    return this.http.get<any>(this.apiUrl1 + 'leave/empOnLeaveToday');
  }

  getHolidays(): Observable<any[]> {
    return this.http.get<any>(this.apiUrl1 + "api/getAllHolidays");
  }
  getLeaveData(): Observable<any[]> {
    const empId = this.getEmpId();

    console.log("employeeid - ", empId);
    return this.http.get<any>(this.apiUrl1 + "leave/getEmpLeaveSummaryByEmpId/" + empId);
  }

  getEmpId() {
    return this.loginService.getEmployeeData().employeeId;
  }

  submitPostRequest(requestData: any, file: File | null): Observable<any> {
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
    console.log("submitPostRequestData requestData : ", requestData)
    return this.http.post<any>(this.url + `posts/saveimage`, formData);

  }


  getBirthdays(): Observable<any> {
    return this.http.get<any[]>(this.url + "api/todayAndNextSevenDaysBirthdaysList");
  }



  getAnniversary(): Observable<any> {
    return this.http.get<any>(this.url + "api/todayAndNextSevenDaysAnniversaryList");
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

  getAllPosts(): Observable<any[]> {
    return this.http.get<any>("http://localhost:8082/posts/getAllPostByTime")
  }
  saveLike(postId: number, emojiId: number, empId: number): Observable<any> {
    const body = {
      emoji: emojiId,
      empId: empId
    };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post(`${this.apiUrl1}posts/savelike/${postId}`, body, { headers });
  }
  postComment(postId: number, comment: string, empId: number): Observable<any> {
    const payload = { comment, empId };
    return this.http.post(`${this.apiUrl2}/${postId}`, payload);
  }
  postImage(description: string, postType: string, mentionEmployee: any, postEmployee: any, files: File[]): Observable<any> {
    const formData1 = new FormData();
    formData1.append('description', description);
    formData1.append('postType', postType);
    formData1.append('mentionEmployee', mentionEmployee);
    formData1.append('postEmployee', postEmployee);
    for (let i = 0; i < files.length; i++) {
      formData1.append('files', files[i])
    }
    return this.http.post<any>(this.apiUrl3, formData1)
  }

  //for pending leave
  pendingRequest(): Observable<any> {
    return this.http.get<any>(`${this.url}leave/getPendingLeaveByNotifiedEmployee/${this.getEmpId()}`)
  }
  //for updating leave 
  updateLeaveByApprover(leaveId: number, empLeaveDto: any): Observable<any> {
    const actionTakenBy = this.loginService.getEmployeeData().firstName + " " + this.loginService.getEmployeeData().lastName
    return this.http.put<any>(
      `${this.url}leave/updateLeaveByApprover/${leaveId}/${actionTakenBy}`,
      empLeaveDto
    );
  }

  //for pending work Form home request
  getPendingWFHRequest() {
    return this.http.get<any>(`${this.url}api/findPendingRequestByNotify/${this.getEmpId()}`)
  }

  //update work from home request
  updateWFHRequest(wfhId:number, wfhRequest:any ){
    
    return this.http.put<any>(
      `${this.url}api/updateStatusByWfhId/${wfhId}`,
      wfhRequest
    );
  }

  getPostsAndLikes(): Observable<any> {
    console.log("inside service")
    return this.http.get<any>(this.url + 'posts/getAllPostByTime');
  }
  getNewJoinees(): Observable<any> {
    return this.http.get<any>(this.apiUrl1 + "api/newJoineesAndLast7Days");
  }
}




