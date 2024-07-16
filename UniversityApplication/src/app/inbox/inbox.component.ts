import { Component, OnInit } from '@angular/core';
import { DashbordService } from '../services/dashbord.service';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

  pendingLeaves: any[] = [];
  showRejectionReason: number | null = null;
  rejectionReason: string = '';
constructor(private service:DashbordService){}

ngOnInit(): void {
  this.fetchPendingLeaves();
}  
empLeaveDto: EmpLeaveDto = {
  leaveId: 0,
  leaveDates: null,
  fromDate: null,
  toDate: null,
  status: '',
  leaveType: '',
  requestedBy: '',
  notifyTo: [],
  actionTakenBy: '',
  leaveNote: '',
  rejectionReason: '',
  employeeId: 123,
  customDayStatus: '',
  leaveAttachment: [],
  durationInDays: 0
};
fetchPendingLeaves() {
  this.service.pendingRequest().subscribe(res => {
    console.log("Dashboard component pending request : ", res);
this.pendingLeaves=res.result
console.log("Inbox component pending request : ", this.pendingLeaves)
  })
}
  approveLeave(leaveId: number): void {
    console.log("leave id for approve : ",leaveId)
    this.empLeaveDto.status='Approved'
    this.service.updateLeaveByApprover(leaveId, this.empLeaveDto)
      .subscribe((res) => {
        console.log("After approved leave response : ",res);
        
        this.fetchPendingLeaves();
      });
  }

  rejectLeave(leaveId: number, reason: string): void {
    if (!reason) {
      alert('Rejection reason is required');
      return;
    }
    this.empLeaveDto.status='Rejected'
    this.empLeaveDto.rejectionReason=reason;
    this.service.updateLeaveByApprover(leaveId, this.empLeaveDto)
      .subscribe(() => {
        this.fetchPendingLeaves();
        this.showRejectionReason = null;
        this.rejectionReason = '';
      });
  }
  toggleRejectionReason(leaveId: number): void {
    this.showRejectionReason = this.showRejectionReason === leaveId ? null : leaveId;
  }   
  
  // for work from home 

  workFromHomeData:any=null;
  getWorkfromHomeRequest(){

  }
    
}
interface EmpLeaveDto {
  leaveId: number; // Add this property as it was in the Java DTO
  leaveDates: Date | null;
  fromDate: Date | null;
  toDate: Date | null;
  status: string; 
  leaveType: string;
  requestedBy: string;
  notifyTo: number[];
  actionTakenBy: string;
  leaveNote: string;
  rejectionReason: string;
  employeeId: number;
  customDayStatus: string;
  leaveAttachment: Attachment[]; 
  durationInDays: number;
}

interface Attachment {
  // Define properties of Attachment interface based on your Java class
}