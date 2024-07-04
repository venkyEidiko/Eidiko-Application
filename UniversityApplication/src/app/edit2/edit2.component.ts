import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit2',
  templateUrl: './edit2.component.html',
  styleUrls: ['./edit2.component.css']
})
export class Edit2Component {
  isOpen:boolean=true
  constructor( private dialogRef: MatDialogRef<Edit2Component>,)
{}
  closeDialog(): void {
    this.dialogRef.close();
  } 
}
