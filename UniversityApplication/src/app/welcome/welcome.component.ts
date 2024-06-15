import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent  {
  employee: any = null;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.employee = this.loginService.getEmployeeData();
    console.log('Employee data:', this.employee);
  }
  
}
