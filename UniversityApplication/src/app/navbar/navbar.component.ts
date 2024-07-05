
import { LoginService } from '../services/login.service';
import { Employee } from '../services/employee';
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  search: string = '';
  searchData: any = [];
  searchData1: Employee[] = [];

  showDropdown: boolean = false;

  constructor(private loginService: LoginService, private dialog: MatDialog) { }
  emp = this.loginService.getEmployeeData();
  empName = this.emp.firstName + " " + this.emp.lastName

  ngOnInit(): void { }

  onChange(search: string) {
    if (this.search.length > 1) {
      console.log("Search Data : ", search)
      this.showDropdown = true;
      this.loginService.searchEmployee(search).subscribe(data => {
        this.searchData = data
        this.searchData1 = this.searchData.result[0]
        console.log("Search DataList : ", this.searchData1)
        this.searchData1?.forEach(emp => {
          console.log("Search Data first name : ", emp.firstName)          
        })

      });
    } else {
      this.searchData1 = [];
      this.showDropdown = false;
    }
  }


  selectResult(result: any): void {
    this.search = result;
    this.showDropdown = false;
  }

logout(){
  localStorage.removeItem('jwt-token');
  localStorage.removeItem('employee-data');
  localStorage.removeItem('refresh-token');
}

  @HostListener('document:click', ['$event'])
  onOutsideClick(event: MouseEvent): void {
    const targetElement = event.target as HTMLElement;
    if (!targetElement.closest('.navbar-search')) {
      this.showDropdown = false;
    }
  }

}

