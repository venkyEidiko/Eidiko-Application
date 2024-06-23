import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { LeavereqService } from '../services/leavereq.service';
import { LeaveRequest } from '../leaverequest';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent {

  isOpen: boolean = true;
  fromDate!: Date;
  toDate!: Date;
  leaveType: string='';
  leaveNote!: string;
  notifyTo!: any;
  employeeId:number=2001;
  showCustomDropdown: boolean = false;
  selectedHalfDay: string = ''; 
  constructor(
    private dialogRef: MatDialogRef<DialogComponent>,
    private leaveRequestService: LeavereqService
  ) {

    this.selectedHalfDay = 'fullDay';
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  toggleCustomDropdown(): void {
    this.showCustomDropdown = !this.showCustomDropdown;
  }

  submitRequest(): void {
   
    const leave: LeaveRequest = {
      leaveDates: new Date(),
      fromDate: this.fromDate,
      toDate: this.toDate,
      status: 'string',
      leaveType: this.leaveType,
      requestedBy: 'string',
      notifyTo: [this.notifyTo], 
      actionTakenBy: 'any', 
      customDayStatus: this.selectedHalfDay,
      leaveNote: this.leaveNote,
      rejectionReason: 'any',
      employeeId: this.employeeId
    };

    this.leaveRequestService.submitLeaveRequest(leave).subscribe(
      (response) => {
        console.log('Request submitted successfully', response);
        this.dialogRef.close();
      },
      (error) => {
        console.error('Error submitting request', error);
      }
    );
  }
  calculateDays(): number {
    if (this.fromDate && this.toDate) {
      const diffTime = Math.abs(this.toDate.getTime() - this.fromDate.getTime());
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      return diffDays;
    }
    return 0;
  }


}
