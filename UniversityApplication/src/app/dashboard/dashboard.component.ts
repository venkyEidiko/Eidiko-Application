import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';
import { Holiday } from '../holiday';
import { LoginService } from '../services/login.service';
import { Employee } from '../services/employee';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {

  constructor(private service: DashbordService, private loginService: LoginService) { }
  showEmoticonPicker = false;
  LeaveResponse: any;
  totalAvailableLeave = 12;
  selectedTab: string = 'organization';
  selectedContent: string = 'post';
  currentDate = new Date();
  selectedEmojiCategory = 'frequentlyUsed';
  files: File[] | null = null;
  textMessage: string | null = null;
 
  postRequestData: PostRequest =
    {
      description:"",
      postType: this.selectedTab,
      mentionEmployee: [],
      postEmployee: this.service.getEmpId()
    }

  ngOnInit(): void {
    this.fetchworkFromHome();
    this.fethHoliday();
    this.fetchleaveData();
    this.convertImageToBase64('assets/your-image.jpg');
  }
  openHoliday(): void {
    this.service.openDialog();
  }

  workFromHomeList: any;
  holidayList: any;
  holiday: Holiday = {
    id: 12,
    dateOfHoliday: "",
    description: "",
    imageName: ""
  }


  selectTab(tab: string) {
    this.selectedTab = tab;
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

  extractAvailablePaidLeave(data: any) {
    for (let leaveData of data) {
      if (leaveData.leaveType === "Paid Leave") {
        return leaveData.availableLeave;
      }
    }
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

  onTextChange(event: Event) {
    const inputElement = event.target as HTMLTextAreaElement;
    this.textMessage = inputElement.value; 
    this.postRequestData.description = this.textMessage; 
    console.log(inputElement.value);
  }


  addAtSymbol(textarea: HTMLTextAreaElement) {
    textarea.setRangeText('@', textarea.selectionStart, textarea.selectionEnd, 'end');
    textarea.focus();
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
      this.service.submitPostRequest(this.postRequestData, file).subscribe(response=>{
        console.log("PostRequset response : ",response)
      },
    (error=>{
      console.log(error);
      
    })
    );
    } else {
      // Handle case where this.files is null or empty as needed
      const file = null
      this.service.submitPostRequest(this.postRequestData, file);
      console.error('No files selected.');
    }
  }

  
  searchData: any = [];
  searchData1: Employee[] = [];
  showDropdown: boolean = false;
  emp = this.loginService.getEmployeeData();
  empName = this.emp.firstName + " " + this.emp.lastName

  onChange(search: string) {
    const atIndex = search.indexOf('@');
    if (atIndex !== -1 && search.length > atIndex + 1) {
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
  }

}

interface PostRequest {
  description: string 
  postType: string | null
  mentionEmployee: string[]
  postEmployee: number
}