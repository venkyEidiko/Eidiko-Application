import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';
import { Holiday } from '../holiday';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private service: DashbordService){}

  ngOnInit(): void {
    this.fetchworkFromHome();
    this.fethHoliday();
    this.fetchleaveData();
  }
  
  workFromHomeList:any;
  holidayList:any;
  holiday:Holiday={
    id: 12,
    dateOfHoliday:"",
    description:"",
    imageName:""
  }
  LeaveResponse:any;
  totalAvailableLeave = 12;
  selectedTab: string = 'organization';
  selectedContent: string = 'announcement';
  currentDate = new Date();
  
  selectTab(tab: string) {
    this.selectedTab = tab;
  }

  showContent(event: Event, content: string) {
    event.preventDefault();
    this.selectedContent = content;
  }

  fetchworkFromHome(){
    this.service.getWorkFromHome().subscribe(
      response => {this.workFromHomeList = response;
        this.workFromHomeList = this.workFromHomeList.result;
      }
    )
  }
  
  fethHoliday(){
    this.service.getHolidays().subscribe(
      response => {
        this.holidayList = response;
        this.holidayList = this.holidayList.result;
        this.holiday = this.holidayList[0];
        
      }
    )
  }


  fetchleaveData(){
    this.service.getLeaveData().subscribe(
      response =>{
        console.log("leave data:- ",response);
        this.LeaveResponse = response;
        this.LeaveResponse = this.LeaveResponse.result;
        this.totalAvailableLeave = this.extractAvailablePaidLeave(this.totalAvailableLeave);
      }
    )
  }

  extractAvailablePaidLeave(data:any){
    for(let leaveData of data){
      if(leaveData.leaveType==="Paid Leave"){
        return leaveData.availableLeave;
      }
    }
  }

  
  public chartOptions1 = {
    series: [12-this.totalAvailableLeave, 12], 
    chart: {
      type: 'donut',
      width: 150,
      height: 220
    },
    labels: ['Consumed Leaves','Available Leaves'],
    colors: ['#cdfaf6', '#1eebe7'],
    fill: {
      type: 'solid',
      colors: ['#cdfaf6', '#1eebe7']
    },
    legend: {
      show: false
    },
    dataLabels: {
      enabled: false,
      formatter: function (val: number, opts: any) {
        return opts.w.config.labels[opts.seriesIndex] + ': ' + val;
      },
      dropShadow: {
        enabled: false
      }
    },
    tooltip: {
      enabled: true,
      y: {
        formatter: function (val: number, opts: any) {
          return val + ' ' + opts.w.config.labels[opts.seriesIndex].toLowerCase();
        }
      },
      x: {
        show: true,
        formatter: function (val: any, opts: any) {
          return opts.w.config.labels[opts.seriesIndex] +' '+ val;
        }
      }
    },
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: 200
          }
        }
      }
    ],
    plotOptions: {
      pie: {
        dataLabels: {
          enabled: true,
          minAngleToShowLabel: 10
        }
      }
    }
  };


  
}
