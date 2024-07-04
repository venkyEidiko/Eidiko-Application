import { Component, OnInit } from "@angular/core";
import { SummaryService } from "../services/summary.service";
import { DashbordService } from "../services/dashbord.service";

@Component({
  selector: "app-summary",
  templateUrl: "./summary.component.html",
  styleUrls: ["./summary.component.css"],
})
export class SummaryComponent implements OnInit {

  constructor(private Summaryservice: SummaryService, private dashBoradService:DashbordService){}

  ngOnInit(): void {
    this.fetchMyTeam();
    this.fetchworkFromHome();
    this.fetchOnLeaveToday()
  }

  fetchMyTeam(){
    this.Summaryservice.getMyTeam().subscribe(
      (response:any)=>{
        this.employees = response.result;
        
      },
      (error:any)=>{
          
      }
    )
  }

  workFromHomeList:any[] = [];

  fetchworkFromHome() {
    this.dashBoradService.getWorkFromHome().subscribe(
      (response:any) => {
        this.workFromHomeList = response.result;
        
      },
      (error:any)=>{
        
      }
    )
  }

  onLeaveToday:any[] = [];

  fetchOnLeaveToday(){
    this.dashBoradService.getOnLeaveToday().subscribe(
      (response:any) => {
        this.onLeaveToday = response.result;
        
      },
      (error:any)=>{
      }
    )
  }
  

  employees:any[]=[];
  // totalteamMember = this.employees.length;

  cardData = [
    {
      heading: "Employee On Time today",
      data: 4,
      color: "rgb(100, 195, 209)",
    },
    {
      heading: "Late Arrival Today",
      data: 4,
      color: "rgb(203, 124, 192)",
    },
    {
      heading: "Work Remotely Today",
      data: 4,
      color: "rgb(152, 180, 51)",
    },
    {
      heading: "Web Clock ins Today",
      data: 4,
      color: "rgb(251, 192, 45)",
    },
  ];

  

months = [
  {'name':'January','days':31},{'name':'fabruary','days':28},{'name':'March','days':31},{'name':'April','days':30},
  {'name':'May','days':31},{'name':'June','days':30},{'name':'July','days':31},{'name':'August','days':31},
  {'name':'September','days':30},{'name':'octobur','days':31},{'name':'November','days':30},{'name':'December','days':31}
];

weekDays = ['Su','Mo','Tu','We','Th','Fr','St'];
onLeaveEmployee=['Nand kumar','Satya','Narendra'];
currentMonthIndex = new Date().getMonth();
currentMonth = this.months[this.currentMonthIndex].name;
daysArray:number[] = [];


getDay(year:number,month:string,date:number){
  const montIndex = this.getMonthIndex(month);
  return this.weekDays[new Date(year,montIndex,date).getDay()];
}
getDaysArray(month:string){
  this.daysArray = [];
  const montIndex = this.getMonthIndex(month);
  const days = this.months[montIndex].days;
  for(let i=1;i<=days;i++){
    this.daysArray.push(i);
  }
  return this.daysArray;
}

getMonthIndex(month:string){
  return this.months.findIndex(monthName => monthName.name === month);
}

previous(month:string){
  if(this.currentMonthIndex <= 0){

  }else{
    this.currentMonthIndex = this.currentMonthIndex-1;
  }
  this.currentMonth = this.months[this.currentMonthIndex].name;

}
next(month:string){
  if(this.currentMonthIndex ==new Date().getMonth()){

  }
  else{
    this.currentMonthIndex+=1;
    this.currentMonth = this.months[this.currentMonthIndex].name;
  }
  

}
}




export interface Employee {
  firstName: string;
  lastName: string;
  position: string;
  location: string;
  department: string;
  email: string;
  mobile: number;
}
