import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';
import { Holiday } from '../holiday';

import { timestamp } from 'rxjs';
import { LoginService } from '../services/login.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  constructor(private service: DashbordService,private loginService:LoginService){
    this.showCommentBox = new Array(this.imageSrcList.length).fill(false);
  }


  
  post: any;
  like: any;
  
  todaynewJoiners:any
  todayNewJoinersCount:number=0
  lastSevenDaysNewjoiners:any;
  todayAnniversaryCount: number = 0;
  todayAnniversary: any;
  nextSevendaysAnniversarys: any;
  todayBirthday: any;
  nextSevendaysBirthday: any;
  todayBirthdayCount: number = 0;


  noBirthdayMessage:String=''
  noJoinersToday:String=''
  ngOnInit(): void {
    this.fetchworkFromHome();
    this.fethHoliday();
    this.fetchleaveData();
    this.getAnniversaryAndAfterSevenDaysList();
    this.getBirthdayAndAfterSevenDaysList();
    this.loadAllPosts();
    this.fetchPostsAndLikes();
    this.fetchOnLeaveToday();
    this.getNewJoiners();
  }
 // employeeId=this.loginService.getEmployeeData().employeeId;
  imageSrcList: { base64Image: string, timeStamp: string ,description:string,postId:number,mentionEmployee:any}[] = [];
  showIcons: boolean = false;
  isCardExpanded: boolean = false;
  insertedSymbol: string = ''; 
  posts: any[] = [];

  insertSymbol(symbol: string) {
    this.insertedSymbol = symbol;
  }



  //   this.convertImageToBase64('assets/your-image.jpg');
  // }
  openHoliday(): void {
    this.service.openDialog();
  }

  workFromHomeList:any;
  showCommentBox: boolean[] = [];
  onLeaveToday:any[] = [];
  holidayList:any;
  holiday:Holiday={
    id: 12,
    dateOfHoliday:"",
    description:"",
    imageName:"",
  }
  LeaveResponse: any;
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

  fetchworkFromHome() {
    this.service.getWorkFromHome().subscribe(
      response => {
        this.workFromHomeList = response;
        this.workFromHomeList = this.workFromHomeList.result;
      }
    )
  }

  fetchOnLeaveToday(){
    this.service.getOnLeaveToday().subscribe(
      (response:any) => {
        console.log("onLeaveTpday respoanse ",response);
        this.onLeaveToday = response.result;
      }
    )
  }

  fethHoliday() {
    this.service.getHolidays().subscribe(
      response => {
        this.holidayList = response;
        this.holidayList = this.holidayList.result;
        this.holiday = this.holidayList[0];
        console.log("fetch HOliday data: ", this.holidayList)
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
  fetchPostsAndLikes(): void {
    this.service.getPostsAndLikes().subscribe(
      (response) => {
        this.posts = response.result.map((post: { postId: any; }) => ({
          ...post,
          likes: response.likes.filter((like: { postId: any; }) => like.postId === post.postId)
        }));
  
        console.log("Posts with Likes:", this.posts);
      },
      (error) => {
        console.error('Error fetching posts:', error);
      }
    );
  }
  
 
  fetchleaveData(){

    this.service.getLeaveData().subscribe(
      response => {
        console.log("leave data:- ", response);
        this.LeaveResponse = response;
        this.LeaveResponse = this.LeaveResponse.result;
        this.totalAvailableLeave = this.extractAvailablePaidLeave(this.totalAvailableLeave);
      }
    )
  }
  loadAllPosts(): void {
    this.service.getAllPosts().subscribe((response: any) => {
      console.log("posts ",response);
      if (response.status === 'SUCCESS') {
        this.imageSrcList = response.result.map((item: any) => ({
          
          base64Image: 'data:image/jpeg;base64,' + item.base64Image,
          timeStamp: this.formatTime(item.timeStamp),
          description:item.description,
          postId:item.postId,
          
          mentionEmployee:item.mentionEmployee

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

  getNewJoiners(){
    this.service.getNewJoiners().subscribe(
      (response:any)=>{
        this.todaynewJoiners = response.result[0].newJoinersToday;
        this.lastSevenDaysNewjoiners = response.result[0].newJoinersLast7Days;
        this.todayNewJoinersCount = this.todaynewJoiners.length;
      }
    );
    if(this.todaynewJoiners===0){
      this.noJoinersToday='No New Joinings!!'
    }
  }

  getBirthdayAndAfterSevenDaysList() {

    this.service.getBirthdays().subscribe(

      (response: any) => {

        this.todayBirthday = response.result[0].TodayBirthdays;
        this.nextSevendaysBirthday = response.result[0].NextSevenDaysBirthdays;
        this.todayBirthdayCount = this.todayBirthday.length;
      }
     
    );
    if(this.todayBirthday=== 0)
      {
        this.noBirthdayMessage ='No birthdays today';
      }
  }

  getAnniversaryAndAfterSevenDaysList() {

    this.service.getAnniversary().subscribe(

      (response: any) => {
             console.log("Annivwersary",response);
        this.todayAnniversary = response.result[0].TodayAnniversary;
        this.nextSevendaysAnniversarys = response.result[0].NextSevenDaysAnniversary;
        this.todayAnniversaryCount = this.todayAnniversary.length;
        console.log("next seven annevewsiry ",this.nextSevendaysAnniversarys);
        
      }
    );
  }
  getBirthdayDisplayText(birthday: string): string {
    const birthdayDate = new Date(birthday);
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    if (this.isSameDay(birthdayDate, tomorrow)) {
      return 'Tomorrow';
    } else {
      return this.formatDate(birthdayDate);
    }
  }

  private isSameDay(date1: Date, date2: Date): boolean {
    return (
      date1.getFullYear() === date2.getFullYear() &&
      date1.getMonth() === date2.getMonth() &&
      date1.getDate() === date2.getDate()
    );
  }

  private formatDate(date: Date): string {
    const months = ['January', 'February', 'March', 'April', 'May', 'June',
      'July', 'August', 'September', 'October', 'November', 'December'];
    return `${date.getDate()} ${months[date.getMonth()]}`;
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
    series: [12 - this.totalAvailableLeave, 12],
    chart: {
      type: 'donut',
      width: 150,
      height: 220
    },
    labels: ['Consumed Leaves', 'Available Leaves'],
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
          return opts.w.config.labels[opts.seriesIndex] + ' ' + val;
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
