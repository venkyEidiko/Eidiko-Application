import { Component } from '@angular/core';
import { Address, Employee } from '../services/employee';
import { LoginService } from '../services/login.service';
import { MatDialog } from '@angular/material/dialog';
import { Dialog1Service } from '../services/dialog1.service';
import { Edit1Component } from '../edit1/edit1.component';
import { Edit2Component } from '../edit2/edit2.component';
import { Edit3Component } from '../edit3/edit3.component';



@Component({
  selector: 'app-profile-prfl',
  templateUrl: './profile-prfl.component.html',
  styleUrls: ['./profile-prfl.component.css']
})
export class ProfilePrflComponent {
  
  constructor(private loginService:LoginService,private dialog:MatDialog,private dialogService:Dialog1Service){}
  employee: Employee|null = null;
  address:Address|null=null;
currentAddress:Address|null=null;
permanentAddress:Address|null=null;
ngOnInit(): void {
    this.employee = this.loginService.getEmployeeData();
    console.log('Employee data:', this.employee);
   // this.address=this.employee?.addresses[0]
   this.employee?.addresses.forEach(adr=>{
   this.address=adr
   console.log("AddressData: ",adr)
    if(this.address?.addressType=="current address"){
    this.currentAddress=adr
    console.log("Current Address Data : ",this.currentAddress.area)
    }else if(this.address?.addressType=="Permanent Address"){
      this.permanentAddress=adr
      console.log("Permanent Address Data : ",this.permanentAddress.area)
    }
        })
    
  }
 
  openDialog1(): void {
    const dialogRef = this.dialog.open(Edit1Component, {
      width: '400px',
      data: {
        employee: this.employee,
        currentAddress: this.currentAddress,
        permanentAddress: this.permanentAddress
      }
    });
  }
 openDialog2()
 {
  const dialogRef = this.dialog.open(Edit2Component, {
    width: '400px',
    data: {
      employee: this.employee,
      currentAddress: this.currentAddress,
      permanentAddress: this.permanentAddress
    }
  });
 }
 openDialog3()
 {
  const dialogRef = this.dialog.open(Edit3Component, {
    width: '400px',
    data: {
      employee: this.employee,
      currentAddress: this.currentAddress,
      permanentAddress: this.permanentAddress
    }
  });
 }
 openDialog4()
 {
  this.dialogService.openDialog4();
 }
}
