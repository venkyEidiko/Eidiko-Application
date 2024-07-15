import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { LeavereqService } from '../services/leavereq.service';
import { FormGroup, FormControl } from '@angular/forms';
import { LoginService } from '../services/login.service';

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
    private loginService:LoginService
  ) {}
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
        this.dialogRef.close("success");
      },
      error => {
        console.error('Error in POST request:', error);
     
      }
    );
  }
}