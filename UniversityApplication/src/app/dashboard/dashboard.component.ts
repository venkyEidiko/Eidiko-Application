import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';
import { Holiday } from '../holiday';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  constructor(private service: DashbordService){}
  todayBirthday:any;
  nextSevendays:any;
  image:string =''
  imagePost:any;
  postsList:any;

  ngOnInit(): void {
    this.fetchworkFromHome();
    this.fethHoliday();
    this.fetchleaveData();

    this.uploadImage();
    this.getBirthdayAndAfterSevenDaysLIst();

 
    this.convertImageToBase64('assets/your-image.jpg');
  }
  openHoliday(): void {
    this.service.openDialog();
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
        console.log("fetch HOliday data: ",this.holidayList)
        this.convertImageToBase64(this.holidayList[0].imageBase64)
      }
    )
  }
   base64Image!: string;
   currentHolidayIndex = 0;
   updateCurrentHoliday() {
    this.holiday = this.holidayList[this.currentHolidayIndex];
    this.convertImageToBase64(this.holidayList[this.currentHolidayIndex].imageBase64);
  }
  convertImageToBase64(imagePath: string): void {
    const img = new Image();
    img.onload = () => {
      const canvas = document.createElement('canvas');
      canvas.width = img.width;
      canvas.height = img.height;
  
      const ctx = canvas.getContext('2d');
      ctx?.drawImage(img, 0, 0); // Draw the entire image starting from (0, 0)
  
      // Convert canvas content to base64 URL and assign to base64Image
      this.base64Image = canvas.toDataURL('image/jpeg');
    };
    
    // Set src attribute of the image to load from the base64 string directly
    img.src = 'data:image/jpeg;base64,' + imagePath;
  }
  previousHoliday() {
    if (this.currentHolidayIndex > 0) {
      this.currentHolidayIndex--;
      this.updateCurrentHoliday();
    }
  }

  nextHoliday() {
    if (this.currentHolidayIndex < this.holidayList.length - 1) {
      this.currentHolidayIndex++;
      this.updateCurrentHoliday();
    }
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
  uploadImage()
  {
    this.service.getPosts().subscribe(
         
       response =>{
           console.log(response);
           this.postsList=response;
           let img =this.postsList.result[0].base64Image;
          
            this.image = 'data:image/png;base64,' + img;
          
       }
    )
  }

  getBirthdayAndAfterSevenDaysLIst()
  {
    this.service.getBirthdays().subscribe(

      (response: any) =>{

               this.todayBirthday=response.result[0].TodayBirthdays;
               this.nextSevendays=response.result[0].NextSevenDaysBirthdays;
         console.log("Birthday and holidays ",response.result[0].TodayBirthdays);
      }
        );
        
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
