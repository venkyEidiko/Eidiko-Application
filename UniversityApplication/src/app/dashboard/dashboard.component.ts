import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';
import { Holiday } from '../holiday';

import { Person,BirthdayResponse } from './dashboard-interface';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  imageData: String ='';
  birthday: Person[] = [];
  selectedContent: string = 'announcement';
  anniversary:Person[]=[];
  selectedPerson: any = null;
  base64Image: string='';
  constructor(private service: DashbordService,private sanitizer:DomSanitizer){}

  ngOnInit(): void {
    this.fetchworkFromHome();
    this.fethHoliday();
    this.fetchleaveData();
    this.getBirthdayAndAnniversary();
    this.uploadPosts()
  }
  
  workFromHomeList:any;
  holidayList:any;

  birthdayAndAnniversary1: any;

  holiday:Holiday={
    id: 12,
    dateOfHoliday:"",
    description:"",
    imageName:""
  }
  LeaveResponse:any;
  totalAvailableLeave = 12;
  selectedTab: string = 'organization';
  // selectedContent: string = 'announcement';
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


  getBirthdayAndAnniversary() {
    this.service.getBirthdayAndAnniversary().subscribe(
      (response: BirthdayResponse) => {
      
        if (response && response.result && response.result[0]) {
          this.birthday = response.result[0].BirthDayToday;
          this.anniversary = response.result[0].WorkAnniversaries;
        }
        else {
          console.error('Unexpected response structure:', response);
        }
      },
      error => {
        console.error('Error fetching data', error);
      }
    );
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



  uploadPosts() {
    this.service.getPosts().subscribe(
      (response: any) => {
        console.log("upload images method ", response.result[0].base64Image);
        let base64Image = response.result[0].base64Image;
  
        // Check if the base64Image already contains the prefix 'data:image/png;base64,'
        if (!base64Image.startsWith('data:image/png;base64,')) {
          this.base64Image = 'data:image/png;base64,' + base64Image;
        }
  
        this.imageData = base64Image;
        return this.sanitizer.bypassSecurityTrustResourceUrl(base64Image);
      }
    );
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
