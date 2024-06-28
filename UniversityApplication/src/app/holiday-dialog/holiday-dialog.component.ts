import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';

@Component({
  selector: 'app-holiday-dialog',
  templateUrl: './holiday-dialog.component.html',
  styleUrls: ['./holiday-dialog.component.css'],
  providers:[]
})
export class HolidayDialogComponent implements OnInit{
holidays:any|null=null;
holidaysPart1:any|null=null;
holidaysPart2:any|null=null;
   constructor(private dashboardService:DashbordService,
   
  ) {}
  ngOnInit(): void {
     this.dashboardService.getHolidays().subscribe(response=>{
      this.holidays=response
      this.holidays=this.holidays.result
      //this.holidays.sort((a:string, b:string) => new Date(a.dateOfHoliday).getTime() - new Date(b.dateOfHoliday).getTime());

      console.log("holiday in holiday component Data : ",this.holidays);
      this.splitHolidays()
     })    
  }
  splitHolidays(): void {
    const halfLength = Math.ceil(this.holidays.length / 2);
    this.holidaysPart1 = this.holidays.slice(0, halfLength);
    this.holidaysPart2 = this.holidays.slice(halfLength);
  }
}
