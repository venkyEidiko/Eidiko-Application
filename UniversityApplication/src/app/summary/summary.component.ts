import { Component } from '@angular/core';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent {

  totalteamMember = 44;

  cardData = [{
    heading: "Employee On Time today",
    data: 4,
    color: "rgb(100, 195, 209)"
  },
  {
    heading: "Employee On Time today",
    data: 4,
    color: "rgb(203, 124, 192)"
  },
  {
    heading: "Employee On Time today",
    data: 4,
    color: "rgb(152, 180, 51)"
  },
  {
    heading: "Employee On Time today",
    data: 4,
    color: "rgb(251, 192, 45)"
  }
]

}
