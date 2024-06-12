
import { Component, OnInit, OnDestroy } from "@angular/core";
import { Subscription } from "rxjs";
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-clock',
  templateUrl: './clock.component.html',
  styleUrls: ['./clock.component.css'],
  
})



export class ClockComponent implements OnInit, OnDestroy {
  time = new Date();
  rxTime = new Date();
  intervalId : any;
  subscription: Subscription | undefined;

  currentDate: Date;

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

}
