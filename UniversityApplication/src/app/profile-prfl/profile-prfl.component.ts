import { Component } from '@angular/core';
import { Address, Employee } from '../services/employee';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-profile-prfl',
  templateUrl: './profile-prfl.component.html',
  styleUrls: ['./profile-prfl.component.css']
})
export class ProfilePrflComponent {
  employee: Employee|null = null;

  constructor(private loginService:LoginService){}
  address:Address|null=null;
currentAddress:Address|null=null;
permanentAddress:Address|null=null;
ngOnInit(): void {
    this.employee = this.loginService.getEmployeeData();
    console.log('Employee data:', this.employee);
   // this.address=this.employee?.addresses[0]
   this.employee?.addresses.forEach(adr=>{
   this.address=adr
    if(this.address?.addressType=="Current Address"){
    this.currentAddress=adr
    console.log("Current Address Data : ",this.currentAddress.area)
    }else if(this.address?.addressType=="Permanent Address"){
      this.permanentAddress=adr
      console.log("Permanent Address Data : ",this.permanentAddress.area)
    }
        })
    
  }
}
