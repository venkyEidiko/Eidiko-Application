import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashbordService } from './dashbord.service';
@Injectable({
  providedIn: 'root'
})
export class LeavereqService {
  private apiUrl = 'http://localhost:8082/leave/saveEmpLeave';
  private apiUrl1 = 'http://localhost:8082/api/leave/requestCompensatory';
  private baseUrl = 'http://localhost:8082/';
  constructor(private http: HttpClient,private dashboardService:DashbordService) {}
  submitLeaveRequest(requestData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, requestData);
  }

  postCompoff(fromDate: string, toDate: string, note: string | undefined, files: File[],employeeId:string): Observable<any> {
    const formData = new FormData();
    formData.append('fromDate', fromDate);
    formData.append('toDate', toDate);
    formData.append('note', note || '');
    formData.append('employeeId', employeeId); 
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }

    return this.http.post<any>(this.apiUrl1, formData);
  }

  workfromHomeRequest(requestBody:any):Observable<any>{
  requestBody.employeeId=this.dashboardService.getEmpId()
    const url = this.baseUrl + 'api/wfh';
    return this.http.post<any>(url, requestBody);

  }

}