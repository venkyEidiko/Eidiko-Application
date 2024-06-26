import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { ShiftRequestService } from '../services/shift-request.service';




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
   startDate = new Date(2024, 5, 15); // Months are zero-indexed (5 = June)
   endDate = new Date(2024, 5, 25);

 data:any=[];
  currentDate: Date;
attandanceStatus:any=[]
averageHour:String='';
arrival:String='';
  weekdata = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];


  constructor(private shiftService:ShiftRequestService) {
    this.currentDate = new Date();
  }

  ngOnInit() {
   this.shiftService.attandance()
   .subscribe(response =>{ 
    this.data=response;
    this.data=this.data.result;
    console.log("Attandance Component - ",this.data)
  });
    this.intervalId = setInterval(() => {
      this.time = new Date();
    }, 1000)

   this.shiftService.AverageDayAndOnTimeArrival( this.startDate,this.endDate).subscribe(
    response=>{console.log(response)
this.attandanceStatus=response;
this.attandanceStatus=this.attandanceStatus.result;
this.averageHour=this.attandanceStatus.avgPerDay
this.arrival=this.attandanceStatus.avgArivalPer
    }
   );
  }

  ngOnDestroy() {
    clearInterval(this.intervalId);
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
  duty_duration:number = 9;
  progress: number = ((9/9)*100);


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
