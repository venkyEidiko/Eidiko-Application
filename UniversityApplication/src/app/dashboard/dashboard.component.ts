import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';
import { Holiday } from '../holiday';
import { timestamp } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private service: DashbordService,private dashservice:DashbordService){
    this.showCommentBox = new Array(this.imageSrcList.length).fill(false);
  }

  ngOnInit(): void {
    this.fetchworkFromHome();
    this.fethHoliday();
    this.fetchleaveData();
    this.loadAllPosts();
  }
  imageSrcList: { base64Image: string, timeStamp: string ,description:string,postId:number}[] = [];
  showIcons: boolean = false;
  workFromHomeList:any;
  showCommentBox: boolean[] = [];
  
  holidayList:any;
  holiday:Holiday={
    id: 12,
    dateOfHoliday:"",
    description:"",
    imageName:"",
  
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
  loadAllPosts(): void {
    this.dashservice.getAllPosts().subscribe((response: any) => {
      console.log(response);
      if (response.status === 'SUCCESS') {
        this.imageSrcList = response.result.map((item: any) => ({
          base64Image: 'data:image/jpeg;base64,' + item.base64Image,
          timeStamp: this.formatTime(item.timeStamp),
          description:item.description,
          postId:item.postId
        }));
      } else {
        console.error('No result found in the response');
      }
    });
  }

formatTime(timestamp: string): string {
  const date = new Date(timestamp);
  return date.toLocaleTimeString();
}
  
  extractAvailablePaidLeave(data:any){
    for(let leaveData of data){
      if(leaveData.leaveType==="Paid Leave"){
        return leaveData.availableLeave;
      }
    }
  }

  toggleCommentBox(index: number): void {
    this.showCommentBox[index] = !this.showCommentBox[index];
  }

  // postComment(index: number, event?: Event): void {
  //   if (event instanceof KeyboardEvent) {
  //     event.preventDefault(); 
  //   }
  //   console.log(`Comment posted for image index: ${index}`);
  //   this.showCommentBox[index] = false; 
  // }
  onEmojiClick(postId: number, emojiId: number): void {
    const empId = 1110; // Replace with the actual employee ID

    this.service.saveLike(postId, emojiId, empId).subscribe(
      response => {
        console.log('Like saved successfully', response);
        // Handle successful response, e.g., update UI or show a message
      },
      error => {
        console.error('Error saving like', error);
        // Handle error, e.g., show an error message
      }
    );
  }

  postComment1(index: number, comment: string, event?: Event): void {
    if (event instanceof KeyboardEvent && event.key !== 'Enter') {
      return;
    }

    const empId = 1110; // Replace with actual employee ID
    const postId = this.imageSrcList[index]?.postId;
    this.service.postComment(postId, comment, empId).subscribe(
      response => {
        console.log('Comment posted successfully', response);
        this.showCommentBox[index] = false; 
      },
      error => {
        console.error('Error posting comment', error);
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
