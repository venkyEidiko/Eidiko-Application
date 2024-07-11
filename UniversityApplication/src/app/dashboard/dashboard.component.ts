import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';
import { Holiday } from '../holiday';

import { Employee } from '../services/employee';

import { timestamp } from 'rxjs';

import { LoginService } from '../services/login.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {


  showEmoticonPicker = false;
  LeaveResponse: any;
  totalAvailableLeave = 12;
  selectedTab: string = 'organization';
  selectedContent: string = 'post';
  option1Checked: boolean = true;
  option2Checked: boolean = false;
  selectedOption: string = 'Organization';
  currentDate = new Date();
  selectedEmojiCategory = 'smileys';
  files: File[] | null = null;
  textMessage: string | null = null;
  showCommentBox: boolean[] = [];
  hideDate: Date | null = null;
 
  workFromHomeList: any;
  onLeaveToday: any[] = [];
  holidayList: any;
  todayAnniversaryCount: number = 0;
  todayAnniversary: any;
  nextSevendaysAnniversarys: any;
  todayBirthday: any;
  nextSevendaysBirthday: any;
  todayBirthdayCount: number = 0;
  todayJoineesCount: number = 0;
  noBirthdayMessage: String = ''
  noJoinersToday: String = ''
  searchStatus:boolean=false
  showIcons: boolean = false;
  isCardExpanded: boolean = false;
  insertedSymbol: string = '';
  searchData: any = [];
  searchData1: Employee[] = [];
  showDropdown: boolean = false;
  emp = this.loginService.getEmployeeData();
  empName = this.emp.firstName + " " + this.emp.lastName
  todaysNewJoinees: any;
  lastSevenDaysNewJoinees: any;

  postRequestData: PostRequest = {
    description: "",
    postType: this.selectedOption,
    mentionEmployee: [],
    postEmployee: this.service.getEmpId()
  }

  constructor(private service: DashbordService, private loginService: LoginService) {
    this.showCommentBox = new Array(this.imageSrcList.length).fill(false);
  }

  ngOnInit(): void {
    this.fetchworkFromHome();
    this.fethHoliday();
    this.fetchleaveData();
    this.getAnniversaryAndAfterSevenDaysList();
    this.getBirthdayAndAfterSevenDaysList();
    this.loadAllPosts();

    this.fetchOnLeaveToday();
    this.workFromHomeList();
    //this.fetchPostsAndLikes();
    this.fetchOnLeaveToday();
    this.fetchNewJoinees();


  }

  // employeeId=this.loginService.getEmployeeData().employeeId;
  imageSrcList: {
    base64Image: string,
    timeStamp: string,
    description: string, postId: number,
    mentionEmployee: any, likes: any,
    emojiIds: any,
    emojiIdsCount: any,
    commentIdsCount: any,
    commentIds: any,
    comments: any,
    showComments: any
  }[] = [];


 

  insertSymbol(symbol: string) {
    this.insertedSymbol = symbol;
  }
  openHoliday(): void {
    this.service.openDialog();
  }

  holiday: Holiday = {
    id: 12,
    dateOfHoliday: "",
    description: "",
    imageName: ""
  }

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
  fetchOnLeaveToday() {
    this.service.getOnLeaveToday().subscribe(
      (response: any) => {
        console.log("onLeaveTpday respoanse ", response);
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

  fetchleaveData() {
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

      console.log("posts ", response);

      if (response.status === 'SUCCESS') {
        this.imageSrcList = response.result.map((item: any) => {

          console.log("Image ",this.imageSrcList);
          
          let emojiIds = item.likes.map((like: any) => like.emoji);
          let emojiIdsCount = emojiIds.length;

          let commentIds=item.comments.map((p:any)=>p.comment);
          let commentIdsCount=commentIds.length;


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


  extractAvailablePaidLeave(data: any) {
    for (let leaveData of data) {
      if (leaveData.leaveType === "Paid Leave") {
        return leaveData.availableLeave;
      } else {
        console.error('No result found in the response');
      }
    }
  }

  formatTime(timestamp: string): string {
    const date = new Date(timestamp);
    return date.toLocaleTimeString();
  }

  getBirthdayAndAfterSevenDaysList() {
    this.service.getBirthdays().subscribe(
      (response: any) => {
        this.todayBirthday = response.result[0].TodayBirthdays;
        this.nextSevendaysBirthday = response.result[0].NextSevenDaysBirthdays;
        this.todayBirthdayCount = this.todayBirthday.length;
      }
    );
    if (this.todayBirthday === 0) {
      this.noBirthdayMessage = 'No birthdays today';
    }
  }

  getAnniversaryAndAfterSevenDaysList() {
    this.service.getAnniversary().subscribe(
      (response: any) => {
        console.log("Annivwersary", response);
        this.todayAnniversary = response.result[0].TodayAnniversary;
        this.nextSevendaysAnniversarys = response.result[0].NextSevenDaysAnniversary;
        this.todayAnniversaryCount = this.todayAnniversary.length;
        console.log("next seven annevewsiry ", this.nextSevendaysAnniversarys);
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

  onEmojiClick(postId: number, emojiId: number): void {
    const empId = 1111;
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

  selectResult(result: any, textarea: HTMLTextAreaElement): void {
    let name: string = result.firstName + " " + result.lastName;
    let textValue: string = textarea.value;
    let lastIndex: number = textValue.lastIndexOf('@');
    let newTextValue: string = textValue.slice(0, lastIndex + 1) + name;
    textarea.value = newTextValue;
    textarea.selectionStart = textarea.selectionEnd = lastIndex + 1 + name.length; // Place cursor after inserted name
    textarea.focus();  
    this.showDropdown = false;
    this.searchStatus = false;
    this.textMessage = newTextValue;
    this.postRequestData.description = newTextValue;
    this.postRequestData.mentionEmployee.push(name);
}

  onTextChange(event: Event) {
    const inputElement = event.target as HTMLTextAreaElement;
    const search = inputElement.value
    const atIndex = search.lastIndexOf('@');
    console.log(inputElement.value);
    if (atIndex !== -1 && search.length > atIndex + 1 && this.searchStatus==true) {
      // Extract text after '@' symbol
      const searchText = search.substring(atIndex + 1);
      if (searchText.length > 1) {
        console.log("Search Data : ", searchText)
        this.showDropdown = true;
        this.loginService.searchEmployee(searchText).subscribe(data => {
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
    } else {
      this.searchData1 = [];
      this.showDropdown = false;
    }
    this.textMessage = inputElement.value;
    this.postRequestData.description = this.textMessage;
   
  }

  addAtSymbol(textarea: HTMLTextAreaElement) {
    textarea.setRangeText('@', textarea.selectionStart, textarea.selectionEnd, 'end');
    textarea.focus();
    this.searchStatus=true
    this.textMessage = textarea.value;
    this.postRequestData.description = this.textMessage;
    console.log("addAtSymbol method text area: ", textarea.value)
  }

  onFileSelected(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    const selectedFiles: File[] = [];
    if (inputElement.files) {
      for (let i = 0; i < inputElement.files.length; i++) {
        const file = inputElement.files[i];
        selectedFiles.push(file);
      }
      this.files = selectedFiles
      console.log(this.files);
    }
  }

  toggleEmoticonPicker() {
    this.showEmoticonPicker = !this.showEmoticonPicker;
  }

  addEmoticon(textarea: HTMLTextAreaElement, event: any) {
    const emoji = event.emoji.native;
    textarea.setRangeText(emoji, textarea.selectionStart, textarea.selectionEnd, 'end');
    textarea.focus();
    this.textMessage = textarea.value;
    this.postRequestData.description = this.textMessage;
  }
  selectEmojiCategory(category: string) {
    this.selectedEmojiCategory = category;
  }

  postRequest() {
    if (this.files && this.files.length > 0) {
      const file: File = this.files[0];
      this.service.submitPostRequest(this.postRequestData, file).subscribe(response => {
        console.log("PostRequset response : ", response)
      },
        (error => {
          console.log(error);
        })
      );
    } else {
      const file = null
      this.service.submitPostRequest(this.postRequestData, file);
      console.error('No files selected.');
    }
  }


  fetchNewJoinees() {
    this.service.getNewJoinees().subscribe(
      (response: any) => {
        const result = response.result[0];
        this.todaysNewJoinees = result['newJoinersToday'];
        this.lastSevenDaysNewJoinees = result['newJoinersLast7Days'];
        this.todayJoineesCount = this.todaysNewJoinees.length;
        console.log("Today's New Joinees: ", this.todaysNewJoinees);
        console.log("Last 7 Days New Joinees: ", this.lastSevenDaysNewJoinees);
      },
      (error) => {
        console.error('Error fetching new joinees:', error);
      }
    );
  }
}

interface PostRequest {
  description: string
  postType: string | null
  mentionEmployee: string[]
  postEmployee: number

}