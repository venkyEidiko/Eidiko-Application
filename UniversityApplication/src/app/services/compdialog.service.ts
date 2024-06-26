import { Injectable } from '@angular/core';
import { CompDialogComponent } from '../comp-dialog/comp-dialog.component';
import { MatDialog } from '@angular/material/dialog';
@Injectable({
  providedIn: 'root'
})
export class CompdialogService {

  constructor(private dialog: MatDialog) { }

  openDialog(): void {
    this.dialog.open(CompDialogComponent, {
      
      })
   }
}
