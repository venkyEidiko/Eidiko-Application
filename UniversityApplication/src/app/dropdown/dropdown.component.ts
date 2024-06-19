import { Component } from '@angular/core';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.css']
})
export class DropdownComponent {

  selectedOption: string = '';
  options = [
    { value: 'Compoffs', viewValue: 'Compoffs' },
    { value: 'Maternity Leave', viewValue: 'Maternity Leave' },
    { value: 'Optional Leave', viewValue: 'Optional Leave' },
    {value:'Paid Leave',viewValue:'Paid Leave' },
    {value:'Unpaid Leave',viewValue:'Unpaid Leave'}
  ];
}





