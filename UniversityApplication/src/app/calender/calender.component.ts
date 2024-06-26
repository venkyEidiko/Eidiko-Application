
import { Component, OnInit } from '@angular/core';
import { ShiftRequestService } from '../services/shift-request.service';
import { format } from 'date-fns';


@Component({
  selector: 'app-calender',
  templateUrl: './calender.component.html',
  styleUrls: ['./calender.component.css']
})
export class CalenderComponent implements OnInit {
  days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  weeks: any[] = [];
  currentMonth: string = '';
  currentYear: number = 0;
  public currentDate: Date = new Date();
onlyDate=this.currentDate.getDate().toString();
onlyMonth=this.currentDate.getMonth();
 //shift form request *1
 constructor(private shiftRequestService: ShiftRequestService) {
  
 }
 openShiftRequest(): void {
   this.shiftRequestService.openDialog();
 }
 //*1
  ngOnInit() {
    this.currentMonth = this.getMonthName(this.currentDate.getMonth());
    this.currentYear = this.currentDate.getFullYear();
    this.generateCalendar();
  }
  getMonthName(monthIndex: number): string {
    const monthNames = [
      'January', 'February', 'March', 'April', 'May', 'June',
      'July', 'August', 'September', 'October', 'November', 'December'
    ];
    return monthNames[monthIndex];
  }
  generateCalendar() {
    const startDate = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), 1);
    let currentDate = new Date(startDate);
    const endDate = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 0);


    this.weeks = [];
    let week: any[] = new Array(7).fill(null).map(() => ({}));


    // Adjust starting date to the previous Monday
    while (currentDate.getDay() !== 1) {
      currentDate.setDate(currentDate.getDate() - 1);
    }

    while (currentDate <= endDate || currentDate.getDay() !== 1) {
      const day = {
        date: currentDate.getDate(),
        shift: this.getShift(currentDate),
        wOff: currentDate.getDay() === 0 || currentDate.getDay() === 6 // Sunday or Saturday
      };


      const dayIndex = (currentDate.getDay() + 6) % 7; // Adjust day index to start from Monday
      week[dayIndex] = day;

      if (dayIndex === 6) {
        this.weeks.push(week);
        week = new Array(7).fill(null).map(() => ({}));
      }
      currentDate.setDate(currentDate.getDate() + 1);
    }
    if (week.some(day => day.date)) {
      this.weeks.push(week);
    }
  }

  getShift(date: Date): string {
    const shifts = ['10:00 AM - 7:00 PM'];
    return date.getDay() !== 0 && date.getDay() !== 6 ? shifts[0] : '';
  }

  prevMonth() {
    this.currentDate.setMonth(this.currentDate.getMonth() - 1);
    this.currentMonth = this.getMonthName(this.currentDate.getMonth());
    this.currentYear = this.currentDate.getFullYear();
    this.generateCalendar();
  }

  nextMonth() {
    this.currentDate.setMonth(this.currentDate.getMonth() + 1);
    this.currentMonth = this.getMonthName(this.currentDate.getMonth());
    this.currentYear = this.currentDate.getFullYear();
    this.generateCalendar();
  }

  goToToday() {
    this.currentDate = new Date();
    this.currentMonth = this.getMonthName(this.currentDate.getMonth());
    this.currentYear = this.currentDate.getFullYear();
    this.generateCalendar();
  }
}
