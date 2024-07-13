import { Component ,Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-barchart',
  templateUrl: './barchart.component.html',
  styleUrls: ['./barchart.component.css']
})
export class BarchartComponent implements OnInit{
   @Input() chartData: { label: string; value: number }[] = [];
   
   ngOnInit(): void {
       
    console.log("Bar Chart Data 1: ",this.chartData)
   }
}
