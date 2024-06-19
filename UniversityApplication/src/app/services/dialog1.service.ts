import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Dialog1Component } from '../dialog1/dialog1.component';
@Injectable({
  providedIn: 'root'
})
export class Dialog1Service {
constructor(private dialog: MatDialog) { }
openDialog(): void {
    this.dialog.open(Dialog1Component, {
    })
  }
}
