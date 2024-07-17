import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { LeavereqService } from '../services/leavereq.service';
import { FormGroup, FormControl } from '@angular/forms';
import { LoginService } from '../services/login.service';
import { SnackbarService } from '../snackbar.service';

@Component({
  selector: 'app-comp-dialog',
  templateUrl: './comp-dialog.component.html',
  styleUrls: ['./comp-dialog.component.css']
})
export class CompDialogComponent {
  isOpen: boolean = true;
  fromDate: Date | null = null;
  toDate: Date | null = null;
  note: string | undefined;
  files: File[] = [];
  

  constructor(
    private dialogRef: MatDialogRef<CompDialogComponent>,
    private compdialogService: LeavereqService,
    private loginService:LoginService,
    private snackbarservice:SnackbarService
  ) {}

  dateFilter = (date: Date | null): boolean => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return date ? date >= today : false;
  }
  employeeId = this.loginService.getEmployeeData().employeeId;
  

  closeDialog(): void {
    this.dialogRef.close();
  }

  onFileSelected(event: any): void {
    const files: FileList = event.target.files;
    for (let i = 0; i < files.length; i++) {
      this.files.push(files[i]);
      console.log('Selected file:', files[i].name);
    }
  }

  calculateDays(): number {
    if (this.fromDate && this.toDate) {
      const diffTime = this.toDate.getTime() - this.fromDate.getTime();
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      return diffDays+1;
    }
    return 0;
  }
  disableddata() {
    console.log("inside  disabled button")
    
    if (this.calculateDays() <= 0) {
      return true;
    } else {
      return false;
    }
  }

  onSubmit(): void {
    if (!this.fromDate || !this.toDate || !this.note) {
      console.log('Form is incomplete.');
      return;
    }

  
    const formattedFromDate = this.fromDate.toISOString().split('T')[0];
    const formattedToDate = this.toDate.toISOString().split('T')[0];

   
    this.compdialogService.postCompoff( formattedFromDate, formattedToDate, this.note, this.files,this.employeeId).subscribe(
      response => {
        console.log('POST request successful:', response);
        if(response.error == null && response.statusCode == 200)
        {
          this.snackbarservice.showSuccess("Request for Compoff is Successful!")
        }
        else{
          this.snackbarservice.showError("Request not sent!TryAgain")
        }
        this.dialogRef.close("success");
      },
      error => {
        console.error('Error in POST request:', error);
     
      }
    );
  }
}