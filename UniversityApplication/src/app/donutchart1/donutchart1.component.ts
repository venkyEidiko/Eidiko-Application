import { Component, Input } from '@angular/core';


@Component({
  selector: 'app-donutchart1',
  templateUrl: './donutchart1.component.html',
  styleUrls: ['./donutchart1.component.css']
})
export class Donutchart1Component {
  @Input() chartLabels: string[] = [];
  @Input() chartData: number[][] = [];
  @Input() chartType: string = 'doughnut';

  // Events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }
}
