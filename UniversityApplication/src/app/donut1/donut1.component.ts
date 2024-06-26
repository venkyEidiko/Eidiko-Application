import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-donut1',
  templateUrl: './donut1.component.html',
  styleUrls: ['./donut1.component.css']
})
export class Donut1Component {
  @Input() chartOptions: any; 
  constructor() {}

}
