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
   updateProfileDetails(employee:any) {
    console.log("Employee primary details : ",employee);
    console.log("Employee primary details employeeId : ",employee);
     this.http.put<any>(`${this.url}updateEmployeePrimaryDetailsByEmpId/${employee.employeeId}`, employee).subscribe(res=>{
      console.log("updateProfileDetail response: ",res)
      this.loginService.setEmployeeData(employee.employeeId)
    });  
   }

   updateContactDetails(employee:any){
    console.log("Employee Contact details : ",employee);
    this.http.put<any>(`${this.url}updateEmployeeContactDetailsByEmpId/${employee.employeeId}`, employee).subscribe(res=>{
      console.log("updateProfileDetail response: ",res)
      this.loginService.setEmployeeData(employee.employeeId)
    }); 
   }
   updateAddressDetails(employee:any){
console.log('Employee Address details : ',employee);
this.loginService.setEmployeeData(employee.employeeId)
   }
  }

