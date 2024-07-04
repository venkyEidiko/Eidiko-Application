import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit1',
  templateUrl: './edit1.component.html',
  styleUrls: ['./edit1.component.css']
})

export class Edit1Component {
  isOpen:boolean=true
  constructor(  private dialogRef: MatDialogRef<Edit1Component>,)
{}
  closeDialog(): void {
    this.dialogRef.close();
  }
}
