import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Employee } from '../services/employee';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
   employee: Employee|null = null;

   constructor(private loginService:LoginService){}

 ngOnInit(): void {
     this.employee = this.loginService.getEmployeeData();
     console.log('Employee data:', this.employee);
   }

}
