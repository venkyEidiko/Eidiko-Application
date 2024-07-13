import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { WorkFromHomeDialogeComponent } from '../work-from-home-dialoge/work-from-home-dialoge.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog: MatDialog) { }

  openDialog(): void {
    this.dialog.open(DialogComponent, {

    })
  }

  openWorkFromHomeDialog(): void {
    this.dialog.open(WorkFromHomeDialogeComponent, {

    })
  }


}

