import { Component } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-attandance',
  templateUrl: './attandance.component.html',
  styleUrls: ['./attandance.component.css']
})
export class AttandanceComponent {
  time = new Date();
  rxTime = new Date();
  intervalId : any;
  subscription: Subscription | undefined;

  currentDate: Date;

  weekdata = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];

  constructor() {
    this.currentDate = new Date();
  }
 
  ngOnInit() {
   
    this.intervalId = setInterval(() => {
      this.time = new Date();
    }, 1000);

  }

  ngOnDestroy() {
    clearInterval(this.intervalId);
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
  duty_duration:number = 6;
  progress: number = ((6/9)*100);

  getColor() {
    if (this.progress < 60) {
      return 'warn'; 
    } else if (this.progress < 80) {
      return 'accent'; 
    } else {
      return 'primary'; 
    }
  }

  attandanceLog:boolean=true;
  shiftSehedule:boolean=false;

  toggleAttandanceLogMenu(){
    this.shiftSehedule=false;
    this.attandanceLog = !this.attandanceLog;
  }
  toggleShiftSeheduleMenu(){
    this.attandanceLog=false;
    this.shiftSehedule = !this.shiftSehedule;
  }

}
