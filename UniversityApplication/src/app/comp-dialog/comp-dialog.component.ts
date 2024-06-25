import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

import {FormControl, FormGroup} from '@angular/forms';

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

onFileSelected(event: any): void {
  const file: File = event.target.files[0];
  if (file) {
    console.log('Selected file:', file.name);
    // You can handle the file upload here, e.g., upload to server or process locally
  }
}

triggerFileInput(fileInput: HTMLInputElement): void {
  fileInput.click();
}


readonly range = new FormGroup({
  start: new FormControl<Date | null>(null),
  end: new FormControl<Date | null>(null),
});



}



