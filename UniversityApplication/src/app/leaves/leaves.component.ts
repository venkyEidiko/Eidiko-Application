import { Component } from '@angular/core';

@Component({
  selector: 'app-leaves',
  templateUrl: './leaves.component.html',
  styleUrls: ['./leaves.component.css']
})
export class LeavesComponent {

    data = [
    { label: 'Mon', value: 50 },
    { label: 'Tue', value: 80 },
    { label: 'Wed', value: 30 },
    { label: 'Thu', value: 60 },
    { label: 'Fri', value: 30 },
    { label: 'Sat', value: 0 },
    {label:  'Sun', value:0}
  ];
  data1=[
    
     {label:'Jan',value:10},
      {label:'Feb',value:30},
      {label:'Mar',value:40},
      {label:'Apr',value:50},
      {label:'May',value:0},
      {label:'June',value:0},
      {label:'July',value:0},
      {label:'Aug',value:0},
      {label:'Sep',value:0},
      {label:'Oct',value:0},
      {label:'Nov',value:0},
      {label:'Dec',value:0},

    
  ]
  
    
  chartData = [
    { label: 'Category A', value: 30 },
    { label: 'Category B', value: 50 },
    { label: 'Category C', value: 20 }
  ];

  
 


}
