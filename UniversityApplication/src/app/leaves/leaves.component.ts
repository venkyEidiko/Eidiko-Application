import { Component } from '@angular/core';
import { DialogService } from '../services/dialog.service';
import { Dialog1Service } from '../services/dialog1.service';
import { CompdialogService } from '../services/compdialog.service';

@Component({
  selector: 'app-leaves',
  templateUrl: './leaves.component.html',
  styleUrls: ['./leaves.component.css']
})
export class LeavesComponent {
  constructor(private dialogService: DialogService,private dialogService1:Dialog1Service,private compdialogService:CompdialogService) {}



  data = [
    { label: 'Mon', value: 50 },
    { label: 'Tue', value: 80 },
    { label: 'Wed', value: 30 },
    { label: 'Thu', value: 60 },
    { label: 'Fri', value: 30 },
    { label: 'Sat', value: 0 },
    { label: 'Sun', value: 0 }
  ];

  data1 = [
    { label: 'Jan', value: 10 },
    { label: 'Feb', value: 30 },
    { label: 'Mar', value: 40 },
    { label: 'Apr', value: 50 },
    { label: 'May', value: 0 },
    { label: 'June', value: 0 },
    { label: 'July', value: 0 },
    { label: 'Aug', value: 0 },
    { label: 'Sep', value: 0 },
    { label: 'Oct', value: 0 },
    { label: 'Nov', value: 0 },
    { label: 'Dec', value: 0 }
  ];

  chartData = [
    { label: 'Category A', value: 30 },
    { label: 'Category B', value: 50 },
    { label: 'Category C', value: 20 }
  ];

  


  
  public chartOptions = {
    series: [7, 1, 1], 
    chart: {
      type: 'donut',
      width: 130,
      height: 220
    },
    labels: ['Unpaid Leaves', 'Paid Leaves', 'Optional Leaves'],
    colors: ['#1eebe7', 'green', 'grey'],
    fill: {
      type: 'solid',
      colors: ['#1eebe7', '#6096a1', 'blue'] 
    },
    legend: {
      show: false
    },
    dataLabels: {
      enabled: false,
      formatter: function (val: number, opts: any) {
        return opts.w.config.labels[opts.seriesIndex] + ': ' + val;
      },
      dropShadow: {
        enabled: false
      }
    },
    tooltip: {
      enabled: true,
      y: {
        formatter: function (val: number, opts: any) {
          return val + ' ' + opts.w.config.labels[opts.seriesIndex].toLowerCase();
        }
      },
      x: {
        show: true,
        formatter: function (val: any, opts: any) {
          return opts.w.config.labels[opts.seriesIndex] +' '+ val;
        }
      }
    },
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: 200
          }
        }
      }
    ],
    plotOptions: {
      pie: {
        dataLabels: {
          enabled: true,
          minAngleToShowLabel: 10
        }
      }
    }
  };




  public chartOptions1 = {
    series: [7, 3], 
    chart: {
      type: 'donut',
      width: 150,
      height: 220
    },
    labels: ['Consumed Leaves','Available Leaves'],
    colors: ['#cdfaf6', '#1eebe7'],
    fill: {
      type: 'solid',
      colors: ['#cdfaf6', '#1eebe7']
    },
    legend: {
      show: false
    },
    dataLabels: {
      enabled: false,
      formatter: function (val: number, opts: any) {
        return opts.w.config.labels[opts.seriesIndex] + ': ' + val;
      },
      dropShadow: {
        enabled: false
      }
    },
    tooltip: {
      enabled: true,
      y: {
        formatter: function (val: number, opts: any) {
          return val + ' ' + opts.w.config.labels[opts.seriesIndex].toLowerCase();
        }
      },
      x: {
        show: true,
        formatter: function (val: any, opts: any) {
          return opts.w.config.labels[opts.seriesIndex] +' '+ val;
        }
      }
    },
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: 200
          }
        }
      }
    ],
    plotOptions: {
      pie: {
        dataLabels: {
          enabled: true,
          minAngleToShowLabel: 10
        }
      }
    }
  };



  public chartOptions2 = {
    series: [1], 
    chart: {
      type: 'donut',
      width: 150,
      height: 220
    },
    labels: ['Consumed Leaves'],
    colors: ['#c2a261'],
    fill: {
      type: 'solid',
      colors: ['#c2a261']
    },
    legend: {
      show: false
    },
    dataLabels: {
      enabled: false,
      formatter: function (val: number, opts: any) {
        return opts.w.config.labels[opts.seriesIndex] + ': ' + val;
      },
      dropShadow: {
        enabled: false
      }
    },
    tooltip: {
      enabled: true,
      y: {
        formatter: function (val: number, opts: any) {
          return val + ' ' + opts.w.config.labels[opts.seriesIndex].toLowerCase();
        }
      },
      x: {
        show: true,
        formatter: function (val: any, opts: any) {
          return opts.w.config.labels[opts.seriesIndex] +' '+ val;
        }
      }
    },
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: 200
          }
        }
      }
    ],
    plotOptions: {
      pie: {
        dataLabels: {
          enabled: true,
          minAngleToShowLabel: 10
        }
      }
    }
  };
  datatable:any=[
  {
    'leavedate':"April 09 2024",
    'leave type':"Paid Leave",
    'Status':'Approved',
    'Requested By':'Nalla Harshini',
    'Action TakenOn':'April 09 2024',
    'Leave Note':'Hello',
    'Cancellation Request':'',
    'Actions':'Reject'
  
  },
  {
    
    'leavedate':"April 09 2024",
    'leave type':"Paid Leave",
    'Status':'Approved',
    'Requested By':'Nalla Harshini',
    'Action TakenOn':'April 09 2024',
    'Leave Note':'Hello',
    'Cancellation Request':'',
    'Actions':'Reject'
  }

  ]

 

  openDialog(): void {
    this.dialogService.openDialog();
  }
  openDialog1():void{
    this.dialogService1.openDialog();
  }
  openDialog2():void{
    this.compdialogService.openDialog();
  }




}  