import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-comp-dialog',
  templateUrl: './comp-dialog.component.html',
  styleUrls: ['./comp-dialog.component.css']
})
export class CompDialogComponent {
  isOpen: boolean = true;
  constructor(private dialogRef: MatDialogRef<CompDialogComponent>) {}

  closeDialog(): void {
    this.dialogRef.close();
}
}
