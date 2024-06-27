import { Injectable, Renderer2, RendererFactory2 } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ShiftRequestFormComponent } from '../shift-request-form/shift-request-form.component';
import { tap, catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { LoginService } from './login.service';
@Injectable({
  providedIn: 'root'
})
export class ShiftRequestService {


  private renderer: Renderer2;


  constructor(private dialog: MatDialog, 
    rendererFactory: RendererFactory2,
    private http: HttpClient,
  private loginService:LoginService) {
    this.renderer = rendererFactory.createRenderer(null, null);
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(ShiftRequestFormComponent, {
      width: '600px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (mainContent) {
        this.renderer.removeClass(mainContent, 'blur');
      }
    });

    const mainContent = document.getElementById('main-content');
    if (mainContent) {
      this.renderer.addClass(mainContent, 'blur');  
  }
  }
  url='http://localhost:3000/'
  public emp=this.loginService.getEmployeeData();
  requestForm(data:any,key:string):Observable<any>{
    console.log("shift request service : ",data);
    if (key === "Shift Request") {
      return this.http.post(this.url+"", data);
    } else if (key === "Another Condition") {
      return this.http.post(this.url, data);
    } else {
      return this.http.post(this.url, data);
    }
   
    }
     attandance(): Observable<any[]> {
      //const employeeId = 101;
      const url = `http://10.0.0.81:8082/api/getByEmployeeid/${this.emp.employeeId}`;
      console.log('Calling URL:', url); // Check the constructed URL
       return this.http.get<any[]>(url);
    }
    AverageDayAndOnTimeArrival(startDate: Date, endDate: Date): Observable<any[]> {
      // Format dates as yyyy-MM-dd for the URL
      const formattedStartDate = this.formatDate(startDate);
      const formattedEndDate = this.formatDate(endDate);
      const url = `http://10.0.0.81:8082/api/AvgPerDayAndOntimeArrival/${this.emp.employeeId}/${formattedStartDate}/${formattedEndDate}`;
  
      return this.http.get<any[]>(url);
    }
  
    private formatDate(date: Date): string {
      // Format date as yyyy-MM-dd
      const year = date.getFullYear();
      const month = this.padNumber(date.getMonth() + 1); // Month is zero-indexed, hence +1
      const day = this.padNumber(date.getDate());
  
      return `${year}-${month}-${day}`;
    }
  
    private padNumber(num: number): string {
      return num < 10 ? `0${num}` : num.toString();
    }   
  }
