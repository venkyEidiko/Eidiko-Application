// import { Component, ViewChild } from '@angular/core';
// import { ChartComponent } from "ng-apexcharts";

// import {
//   ApexNonAxisChartSeries,
//   ApexResponsive,
//   ApexChart,
//   ApexFill,
//   ApexLegend,
//   ApexDataLabels
// } from "ng-apexcharts";

// export type ChartOptions = {
//   series: ApexNonAxisChartSeries;
//   chart: ApexChart;
//   responsive: ApexResponsive[];
//   colors: string[];
//   fill: ApexFill;
//   legend: ApexLegend;
//   dataLabels?: ApexDataLabels; 
// };

// @Component({
//   selector: 'app-donutchart',
//   templateUrl: './donutchart.component.html',
//   styleUrls: ['./donutchart.component.css']
// })
// export class DonutchartComponent {

//   @ViewChild("chart") chart!: ChartComponent;
//   public chartOptions: ChartOptions;

//   constructor() {
//     const availableLeaves = 15;  
//     const consumedLeaves = 5;    

//     this.chartOptions = {
//       series: [availableLeaves, consumedLeaves],
//       chart: {
//         type: "donut",
//         width: 250,
//         height: 100
//       },
//       colors: ['#00FF00', '#FF0000'], 
//       fill: {
//         type: 'solid',
//         colors: ['#00FF00', '#FF0000']
//       },
//       legend: {
//         show: false
//       },
//       dataLabels: {
//         enabled: true,
//         formatter: function (val: number, opts: any) {
//           return opts.w.config.series[opts.seriesIndex].toString();
//         },
//         dropShadow: {
//           enabled: false
//         },
//         total: {
//           show: true,
//           label: 'Available',
//           formatter: function () {
//             return availableLeaves.toString();
//           }
//         }
//       },
//       responsive: [
//         {
//           breakpoint: 480,
//           options: {
//             chart: {
//               width: 50
//             },
//             legend: {
//               position: "bottom",
//               show: false
//             }
//           }
//         }
//       ]
//     };
//   }
// }
