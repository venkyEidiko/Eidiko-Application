import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';
import { Holiday } from '../holiday';

import { LoginService } from '../services/login.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  constructor(private service: DashbordService,private dashservice:DashbordService,private loginService:LoginService){
    this.showCommentBox = new Array(this.imageSrcList.length).fill(false);
  }

  ngOnInit(): void {
    this.fetchworkFromHome();
    this.fethHoliday();
    this.fetchleaveData();
    this.loadAllPosts();
    
  }
 // employeeId=this.loginService.getEmployeeData().employeeId;
  imageSrcList: { base64Image: string, timeStamp: string ,description:string,postId:number,mentionEmployee:any,likes:any,emojiIds:any,emojiIdsCount:any,commentIdsCount:any,commentIds:any,comments:any,showComments:any}[] = [];
  showIcons: boolean = false;
  isCardExpanded: boolean = false;
  insertedSymbol: string = ''; 

 

  insertSymbol(symbol: string) {
    this.insertedSymbol = symbol;
  }



  
  openHoliday(): void {
    this.service.openDialog();
  }

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
  expandCard() {
    this.isCardExpanded = true;
  }
  collapseCard() {
    this.isCardExpanded = false;
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
  
      
      this.base64Image = canvas.toDataURL('image/jpeg');
    };
    
    
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
  loadAllPosts(): void {
    this.dashservice.getAllPosts().subscribe((response: any) => {
      console.log("posts ", response);
      if (response.status === 'SUCCESS') {
        this.imageSrcList = response.result.map((item: any) => {
         
          let emojiIds = item.likes.map((like: any) => like.emoji);
          let emojiIdsCount = emojiIds.length;
          let commentIds=item.comments.map((p:any)=>p.comment);
          
          let commentIdsCount=commentIds.length
  
          return {
            base64Image: 'data:image/jpeg;base64,' + item.base64Image,
            timeStamp: this.formatTime(item.timeStamp),
            description: item.description,
            postId: item.postId,
            likes: item.likes,
            emojiIds: emojiIds,
            emojiIdsCount: emojiIdsCount, 
            mentionEmployee: item.mentionEmployee,
            comments:item.comments,
            commentIds:item.commentIds,
            commentIdsCount:commentIdsCount,


          };
        });
  
        // Log the entire imageSrcList for verification
        console.log('Modified imageSrcList:', this.imageSrcList);
        
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

 
  onEmojiClick(postId: number, emojiId: number): void {
    
   const empId=1111;
    this.service.saveLike(postId, emojiId, empId).subscribe(
      response => {
        console.log('Like saved successfully', response);

      },
      error => {
        console.error('Error saving like', error);

      }
    );
  }

  
  

  postComment1(index: number, comment: string, event?: Event): void {
    if (event instanceof KeyboardEvent && event.key !== 'Enter') {
      return;
    }

    const empId = 1111; 
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
