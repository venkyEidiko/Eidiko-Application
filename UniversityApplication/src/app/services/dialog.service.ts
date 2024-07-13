import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog: MatDialog,private http:HttpClient,private loginService:LoginService) { }

  openDialog(): void {
    this.dialog.open(DialogComponent, {
      
      })
   }

   employee =this.loginService.getEmployeeData()
    url="http://localhost:8082/api/"
   updateProfileDetails(employee:any) : Observable<any>{
    console.log("Employee primary details : ",employee);
    console.log("Employee primary details employeeId : ",employee);
    return this.http.put(`${this.url}updateEmployeePrimaryDetailsByEmpId/${employee.employeeId}`, employee);  

    
   }
  }

