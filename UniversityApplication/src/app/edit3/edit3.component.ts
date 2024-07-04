import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit3',
  templateUrl: './edit3.component.html',
  styleUrls: ['./edit3.component.css']
})
export class Edit3Component {
  isOpen:boolean=true
  constructor( private dialogRef: MatDialogRef<Edit3Component>,)
{}
  closeDialog(): void {
    this.dialogRef.close();
  }
}
