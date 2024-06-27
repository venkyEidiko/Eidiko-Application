import { Component } from '@angular/core';
interface Option {
  name: string;
  checked: boolean;
}
@Component({
  selector: 'app-statusdrop',
  templateUrl: './statusdrop.component.html',
  styleUrls: ['./statusdrop.component.css']
})
export class StatusdropComponent {
  options: Option[] = [
    { name: 'Select All', checked: false },
    { name: 'Approved', checked: false },
    { name: 'Rejected', checked: false },
    { name: 'Cancelled', checked: false },
   
  ];

  constructor() { }

  onOptionChange(): void {
    // Handle checkbox change logic here
    console.log(this.options);
 
  }

}
