import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import{differenceInCalendarDays} from "date-fns"; 
import { ShiftRequestService } from '../services/shift-request.service';

@Component({
  selector: 'app-shift-request-form',
  templateUrl: './shift-request-form.component.html',
  styleUrls: ['./shift-request-form.component.css']
})
export class ShiftRequestFormComponent implements OnInit {
  shiftRequestForm!: FormGroup;
  shifts = ['Morning', 'Afternoon', 'Night'];
  totalDays: number = 0;
  formType: string = "Shift Request";
 
  requestForm: RequestForm = {
    startDate: null,
    endDate: null,
    newShift: '',
    reason: '',
    notify: ''
  };

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ShiftRequestFormComponent>,
    private shiftRequestService: ShiftRequestService
  ) { }


  ngOnInit(): void {
    this.shiftRequestForm = this.fb.group({
      startDate: null,
      endDate: null,
      newShift: '',
      reason: '',
      notify: ''
    });


    this.shiftRequestForm.valueChanges.subscribe(values => {
      this.calculateTotalDays(values.startDate, values.endDate);
      this.requestForm.startDate = values.startDate;
      this.requestForm.endDate = values.endDate;
      this.requestForm.newShift = values.newShift;
      this.requestForm.reason = values.reason;
      this.requestForm.notify = values.notify;
      console.log("shiftRequestForm: ", this.requestForm);
    });
  }


  calculateTotalDays(startDate: Date, endDate: Date): void {
    if (startDate && endDate) {
      this.totalDays = differenceInCalendarDays(new Date(endDate), new Date(startDate)) + 1;
    } else {
      this.totalDays = 0;
    }
  }


  onCancel(): void {
    this.dialogRef.close();
  }


  onRequest(): void {
    if (this.shiftRequestForm.valid) {
      this.shiftRequestService.requestForm(this.requestForm, this.formType).subscribe(
        response => {
          console.log('Shift request submitted successfully:', response);
          this.dialogRef.close(this.shiftRequestForm.value);
        },
        error => {
          console.error('Error submitting shift request:', error);
        }
      );
    }
  }
}


export interface RequestForm {
  startDate: Date | null,
  endDate: Date | null,
  newShift: string,
  reason: string,
  notify: string
};
