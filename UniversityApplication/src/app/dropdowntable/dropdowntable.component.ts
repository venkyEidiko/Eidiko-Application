import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
interface Option {
  name: string;
  checked: boolean;
}
@Component({
  selector: 'app-dropdowntable',
  templateUrl: './dropdowntable.component.html',
  styleUrls: ['./dropdowntable.component.css']
})
export class DropdowntableComponent {
  options: Option[] = [
    { name: 'Select All', checked: false },
    { name: 'Comp offs', checked: false },
    { name: 'Maternity Leave', checked: false },
    { name: 'Paid Leave', checked: false },
    { name: 'Unpaid Leave', checked: false },
    { name: 'Optional Leave', checked: false },

  ];

  constructor() { }

  onOptionChange(): void {
    // Handle checkbox change logic here
    console.log(this.options);
 
  }


}
