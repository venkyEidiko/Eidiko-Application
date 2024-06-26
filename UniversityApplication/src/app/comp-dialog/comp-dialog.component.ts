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

  
    const formattedFromDate = this.fromDate;
    const formattedToDate = this.toDate;

    const compOffRequestData:any = {

      "fromdate":formattedFromDate,
       "toDate":formattedToDate,
       "note":this.note,
       "file":this.files,
       "employeeId": this.employeeId
     }

    this.compdialogService.postCompoff( compOffRequestData).subscribe(
      response => {
        console.log('POST request successful:', response);
        this.dialogRef.close();
      },
      error => {
        console.error('Error in POST request:', error);
     
      }
    );
  }
}
export interface compOffrequest {
  "fromdate":string,
  "toDate":string,
  "note":string,
  "file":File,
  "employeeId":number

}
