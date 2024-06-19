import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent {

  isOpen: boolean = true;

  constructor(private dialogRef: MatDialogRef<DialogComponent>) {}

  closeDialog(): void {
    this.dialogRef.close();
}
}