import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Employee } from '../services/employee';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
 search:string='';
  searchData:any=[];
  searchData1:Employee[]=[];
  constructor(private loginService:LoginService,private dialog: MatDialog) {  }
  emp=this.loginService.getEmployeeData();
  empName=this.emp.firstName+" "+this.emp.lastName
  
  ngOnInit(): void {
  }
  onChange(search:string){
    console.log("Search Data : ",search)
this.loginService.searchEmployee(search).subscribe(data=>{
  this.searchData=data
  this.searchData1=this.searchData.result
  console.log("Search Data : ",this.searchData1)
  console.log("Search Data : ",this.searchData1.forEach(emp=>emp.firstName))
 
});
  }

  
}
