import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Dialog1Component } from '../dialog1/dialog1.component';
import { Edit1Component } from '../edit1/edit1.component';
import { Edit2Component } from '../edit2/edit2.component';
import { Edit3Component } from '../edit3/edit3.component';
import { Edit4Component } from '../edit4/edit4.component';

@Injectable({
  providedIn: 'root'
})
export class Dialog1Service {
constructor(private dialog: MatDialog) { }
openDialog(): void {
    this.dialog.open(Dialog1Component, {
    })
  }
  openDialog1():void{
    this.dialog.open(Edit1Component,
      {

      }
    )
  }
  openDialog2():void{
    this.dialog.open(Edit2Component,{})
  }
  openDialog3():void 
  {
    this.dialog.open(Edit3Component,{})
  }
  openDialog4():void{
    this.dialog.open(Edit4Component,{})
  }
  }

