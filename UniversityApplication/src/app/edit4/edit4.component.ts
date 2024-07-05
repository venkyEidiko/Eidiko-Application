import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit4',
  templateUrl: './edit4.component.html',
  styleUrls: ['./edit4.component.css']
})
export class Edit4Component {
  isOpen:boolean=true
  selectedRelation = '';
  selectedGender ='';

  setRelation(relation: string,gender:string) {
    this.selectedRelation = relation;
    this.selectedGender = gender;
  }
  constructor( private dialogRef: MatDialogRef<Edit4Component>,)
{}
  closeDialog(): void {
    this.dialogRef.close();
  }
}
