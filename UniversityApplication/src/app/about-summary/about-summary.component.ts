import { Component, OnInit } from '@angular/core';
import { Employee } from '../services/employee';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-about-summary',
  templateUrl: './about-summary.component.html',
  styleUrls: ['./about-summary.component.css']
})
export class AboutSummaryComponent implements OnInit {
  employee: Employee|null = null;

  constructor(private loginService:LoginService){}

ngOnInit(): void {
    this.employee = this.loginService.getEmployeeData();
    console.log('Employee data:', this.employee);
  }
}
