import { Component, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Employee, Address } from '../services/employee';
import { DialogService } from '../services/dialog.service';
@Component({
  selector: 'app-edit1',
  templateUrl: './edit1.component.html',
  styleUrls: ['./edit1.component.css']
})

export class Edit1Component {
  isOpen:boolean=true
   employee: Employee | null = null;
  currentAddress: Address | null = null;
  permanentAddress: Address | null = null;
  

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,private dialogRef: MatDialogRef<Edit1Component>,private dialogService:DialogService) {
    this.employee = data.employee;
    this.currentAddress = data.currentAddress;
    this.permanentAddress = data.permanentAddress;
  }
 
  onClick(){
    this.dialogService.updateProfileDetails(this.employee)
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
