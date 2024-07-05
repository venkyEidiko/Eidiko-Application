import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Address, Employee } from '../services/employee';

@Component({
  selector: 'app-edit3',
  templateUrl: './edit3.component.html',
  styleUrls: ['./edit3.component.css']
})
export class Edit3Component {
  isOpen: boolean = true;
  employee: Employee | null = null;
  currentAddress: Address | null = null;
  permanentAddress: Address | null = null;
  address!: Address;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<Edit3Component>
  ) {
    if (data) {
      this.employee = data.employee || null;
      console.log("employee",this.employee)
      this.employee?.addresses.forEach(adr=>{
        this.address=adr
       
         if(this.address?.addressType=="currentAddress"){
         this.currentAddress=adr
         
         
         }
      this.permanentAddress = data.permanentAddress || null;
    }
  )
  }
}

  






  
  closeDialog(): void {
    this.dialogRef.close();
  }
}
