import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Address, Employee } from '../services/employee';
import { DialogService } from '../services/dialog.service';

@Component({
  selector: 'app-edit2',
  templateUrl: './edit2.component.html',
  styleUrls: ['./edit2.component.css']
})
export class Edit2Component {
  isOpen:boolean=true
  employee: Employee | null = null;
  currentAddress: Address | null = null;
  permanentAddress: Address | null = null;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialogRef: MatDialogRef<Edit2Component>,private dialogService:DialogService)

{
  this.employee = data.employee;
    this.currentAddress = data.currentAddress;
    this.permanentAddress = data.permanentAddress;
}

onclick(){
this.dialogService.updateContactDetails(this.employee)
}

  closeDialog(): void {
    this.dialogRef.close();
  } 
}
