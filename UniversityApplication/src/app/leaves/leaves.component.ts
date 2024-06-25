import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { LeaveService } from '../services/leave.service';
import { Dialog1Service } from '../services/dialog1.service';
import { CompdialogService } from '../services/compdialog.service';
import { DialogService } from '../services/dialog.service';
import { LeavetypeService } from '../services/leavetype.service';

interface PendingLeave {
  leaveId: number;
  leaveDates: string;
  leaveType: string | null;
  status: string;
  requestedBy: string | null;
  leaveNote: string | null;
}

interface MonthlyLeaveData {
  totalLeaveTaken: number;
  leaveDays: string[];
  leaveDuration?: number;
}

@Component({
  selector: 'app-leaves',
  templateUrl: './leaves.component.html',
  styleUrls: ['./leaves.component.css']
})
export class LeavesComponent implements OnInit {
  daysOfWeek: string[] = [];
  pendingLeaves:PendingLeave[]|null =null;
  dataSource!: MatTableDataSource<PendingLeave>;
  displayedColumns: string[] = [
    'leaveDates',
    'leaveType',
    'status',
    'requestedBy',
    'leaveNote'
  ];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  leaveTypeOptions: string[] = [];
  selectedLeaveTypes: Set<string> = new Set<string>();
  
  consumedLeaves = 0;
  availableLeaves = 0;
  totalLeave = 12;

  monthlyChartData: { label: string; value: number,duration: number }[] = []; // Array to hold monthly chart data

  constructor(
    private leaveService: LeaveService,
    private dialogService: DialogService,
    private dialogService1: Dialog1Service,
    private compdialogService: CompdialogService,
    private leavetypeService: LeavetypeService
  ) {}

  ngOnInit(): void {
    const employeeId = 2001;
    const pageSize = 5;

    this.fetchLeaveBalance(employeeId);
    this.fetchMonthlyChartData(employeeId);
    this.fetchLeaveData(employeeId, 0, pageSize);

    this.leaveTypeOptions = ['Paid Leave', 'Unpaid Leave', 'Maternity Leave', 'Optional Leave', 'Comp Offs'];
  }
  chartOptions: ApexCharts.ApexOptions = {
    series: [{
      name: 'Leave Days',
      data: []
    }],
    chart: {
      type: 'bar',
      height: 350,
    },
    plotOptions: {
      bar: {
        horizontal: false,
      },
    },
    dataLabels: {
      enabled: false
    },
    xaxis: {
      categories: [],
    },
    tooltip: {
      y: {
        formatter: (val: number, { series, seriesIndex, dataPointIndex, w }) => {
          const monthData = this.monthlyChartData[dataPointIndex];
          return `Duration: ${monthData.duration} days<br/>Leave Days: ${monthData.value} days`;
        }
      }
    }
  };

  fetchLeaveData(employeeId: number, pageIndex: number, pageSize: number): void {
    const pageNumber = pageIndex + 1;

    this.leaveService.fetchLeaveData(employeeId, pageNumber, pageSize).subscribe(
      response => {
        if (response.status === 'SUCCESS' && response.result.length > 0) {
          const pendingLeaves: PendingLeave[] = response.result.map((item: any) => ({
            leaveId: item.leaveId,
            leaveDates: item.leaveDates,
            leaveType: item.leaveType,
            status: item.status,
            requestedBy: item.requestedBy,
            leaveNote: item.leaveNote
          }));
          this.dataSource = new MatTableDataSource<PendingLeave>(pendingLeaves);
          this.dataSource.paginator = this.paginator;
          this.paginator.length = response.totalCount;
            console.log(this.paginator.length) 
        } else {
          console.error('Failed to fetch leave data or no data available'); 
        }
      },
      error => {
        console.error('Error fetching leave data:', error);
      }
    );
  }

  onPageChange(event: PageEvent): void {
    const employeeId = 2001;
    const pageSize = event.pageSize;
    const pageIndex = event.pageIndex;

    this.fetchLeaveData(employeeId, pageIndex, pageSize);
  }

    fetchLeaveBalance(employeeId: number): void {
    this.leavetypeService.fetchLeaveBalance(employeeId).subscribe(
      (response: any) => {
        console.log("fetchLeaveBalance method Response Data : ",response)
        if (response.status === 'SUCCESS' && response.result.length > 0) {
          const result = response.result[0];
          console.log("fetchLeaveBalance method Data : ",result)
          this.pendingLeaves=result.
          console.log(result);
          this.consumedLeaves = result.consumedLeave;
          this.availableLeaves = 12 - result.consumedLeave;

          this.updateMonthlyChartData(result.monthlyLeaveData);

          this.updateChartOptions();
        } else {
          console.error('Failed to fetch leave balance or no data available');
        }
      },
      error => {
        console.error('Error fetching leave balance:', error);
      }
    );
  }


  fetchMonthlyChartData(employeeId: number): void {
    this.leavetypeService.fetchMonthlyLeaveData(employeeId).subscribe(
      (response: any) => {
        if (response.status === 'SUCCESS' && response.result.length > 0) {
          const monthlyData = response.result[0].monthlyLeaveData;
          console.log(monthlyData);
          this.updateMonthlyChartData(monthlyData);
        } else {
          console.error('Failed to fetch monthly leave data or no data available');
        }
      },
      error => {
        console.error('Error fetching monthly leave data:', error);
      }
    );
  }

  updateMonthlyChartData(monthlyLeaveData: { [key: string]: MonthlyLeaveData }): void {
    if (!monthlyLeaveData) {
      console.error('Monthly leave data is undefined or null.');
      return;
    }

    this.monthlyChartData = [];

    
    const allMonths = [
      '2024-01', '2024-02', '2024-03', '2024-04', '2024-05', '2024-06',
      '2024-07', '2024-08', '2024-09', '2024-10', '2024-11', '2024-12'
    ];

    allMonths.forEach(monthKey => {
      const monthLabel = this.getMonthName(monthKey);
      console.log(monthLabel) 
      const dataForMonth = monthlyLeaveData[monthKey];
      console.log(dataForMonth)
      const value = dataForMonth ? dataForMonth.totalLeaveTaken : 0;
      console.log(value);
      const duration = dataForMonth ? dataForMonth.leaveDuration || 0 : 0; // Default to 0 if no duration data
      this.monthlyChartData.push({ label: monthLabel, value, duration });
    });
  }

  private getMonthName(monthKey: string): string {
    const [year, month] = monthKey.split('-');
    const monthIndex = parseInt(month, 10); 
    const months = [
      'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
      'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
    ];
    return `${months[monthIndex-1]} `;
  }

  updateChart(): void {
    this.chartOptions.series = [{
      name: 'Leave Days',
      data: this.monthlyChartData.map(item => item.value)
    }];
    this.chartOptions.xaxis = {
      categories: this.monthlyChartData.map(item => item.label)
    };
    this.chartOptions = { ...this.chartOptions };
  }

  updateChartOptions(): void {
    this.chartOptions1.series = [this.availableLeaves, this.consumedLeaves];
    this.chartOptions1 = { ...this.chartOptions1 };

    this.chartOptions3.series = [this.availableLeaves, this.consumedLeaves];
    this.chartOptions3 = { ...this.chartOptions3 };

    this.chartOptions2.series = [this.consumedLeaves];
    this.chartOptions2 = { ...this.chartOptions2 };
  }

  chartOptions1 = {
    series: [0, 0],
    chart: {
      type: 'donut',
      width: 150,
      height: 220
    },
    labels: ['Available Leaves', 'Consumed Leaves'],
    colors: ['#cdfaf6', '#1eebe7'],
    fill: {
      type: 'solid',
      colors: ['#cdfaf6', '#1eebe7']
    },
    legend: {
      show: false
    },
    dataLabels: {
      enabled: false
    },
    tooltip: {
      enabled: true,
      y: {
        formatter: (val: number) => `${val} days`
      },
      x: {
        show: true
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
    ]
  };

  chartOptions3 = {
    series: [0, 0],
    chart: {
      type: 'donut',
      width: 150,
      height: 180
    },
    labels: ['Available Leaves', 'Consumed Leaves'],
    colors: ['#dde2eb', '#dde2eb'],
    fill: {
      type: 'solid',
      colors: ['#cdfaf6', '#1eebe7']
    },
    legend: {
      show: false
    },
    dataLabels: {
      enabled: false
    },
    tooltip: {
      enabled: true,
      y: {
        formatter: (val: number) => `${val} days`
      },
      x: {
        show :true
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
    ]
  };

  chartOptions2 = {
    series: [0],
    chart: {
      type: 'donut',
      width: 150,
      height: 220
    },
    labels: ['Consumed Leaves'],
    colors: ['#dde2eb'],
    fill: {
      type: 'solid',
      colors: ['#c2a261']
    },
    legend: {
      show: false
    },
    dataLabels: {
      enabled: false
    },
    tooltip: {
      enabled: true,
      y: {
        formatter: (val: number) => `${val} days`
      },
      x: {
        show: true
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
    ]
  };

  openDialog(): void {
    this.dialogService.openDialog();
  }

  openDialog1(): void {
    this.dialogService1.openDialog();
  }

  openDialog2(): void {
    this.compdialogService.openDialog();
  }

  data = [
  
  ]
}
