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
export interface LeaveRequest {
  actionTakenBy: string;
  customDayStatus: string;
  durationInDays: number;
  employeeId: number;
  fromDate: Date;
  leaveDates: Date;
  leaveId: number;
  leaveNote: string;
  leaveType: string;
  notifyTo: string;
  rejectionReason: string;
  requestedBy: string;
  status: string;
  toDate: Date;
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

  pendingLeaves: PendingLeave[] | null = null;
  dataSource!: MatTableDataSource<PendingLeave>;
  displayedColumns: string[] = [
    'leaveDates',
    'leaveType',
    'status',
    'requestedBy',
    'leaveNote'
  ];
  totalDays: any = []

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  leaveTypeOptions: string[] = [];
  selectedLeaveTypes: Set<string> = new Set<string>();

  consumedLeaves = 0;
  availableLeaves = 0;
  totalLeave = 12;
  pendingLeave: LeaveRequest[] | null = null;

  monthlyChartData: { label: string; value: number, duration: number }[] = []; // Array to hold monthly chart data
  weeklyChartData: { label: string; value: number }[] = [
    { label: 'Mon', value: 0 },
    { label: 'Tue', value: 0 },
    { label: 'Wed', value: 0 },
    { label: 'Thu', value: 0 },
    { label: 'Fri', value: 0 },
    { label: 'Sat', value: 0 },
    { label: 'Sun', value: 0 }
  ]
  constructor(
    private leaveService: LeaveService,
    private dialogService: DialogService,
    private dialogService1: Dialog1Service,
    private compdialogService: CompdialogService,
    private leavetypeService: LeavetypeService
  ) { }

  ngOnInit(): void {
    const employeeId = 101;
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

    this.leavetypeService.fetchLeaveData(employeeId, pageNumber, pageSize).subscribe(
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
          // console.log(this.paginator.length) 
          this.updateDaysOfWeek(pendingLeaves);

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
    const employeeId = 101;
    const pageSize = event.pageSize;
    const pageIndex = event.pageIndex;

    this.fetchLeaveData(employeeId, pageIndex, pageSize);
  }

  fetchLeaveBalance(employeeId: number): void {
    this.leavetypeService.fetchLeaveBalance(employeeId).subscribe(
      (response: any) => {
        console.log("fetchLeaveBalance method Response Data : ", response)
        if (response.status === 'SUCCESS' && response.result.length > 0) {
          const result = response.result[0];
          console.log("fetchLeaveBalance method Data : ", result)
          this.consumedLeaves = result.consumedLeave;
          this.availableLeaves = 12 - result.consumedLeave;
          this.updateMonthlyChartData(result.monthlyLeaveData);
          this.updateChartOptions();

          //pending leave data
          this.pendingLeave = result.pendingLeave
          console.log("Pending leave: ", this.pendingLeave);
          this.pendingLeave?.forEach(pending=>{
            console.log("Panding leave Object: ",pending)
          })

        } else {
          console.error('Failed to fetch leave balance or no data available');
        }
      },
      error => {
        console.error('Error fetching leave balance:', error);
      }
    );
  }

  weekday = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  getDayOfWeek(date: Date): string {

    const dayIndex = date.getDay(); // 0 (Sunday) to 6 (Saturday)
    return this.weekday[dayIndex];
  }


  dayIndexCountMap = new Map<string, number>();

  fetchMonthlyChartData(employeeId: number): void {
    this.leavetypeService.fetchMonthlyLeaveData(employeeId).subscribe(

      (response: any) => {
        if (response.status === 'SUCCESS' && response.result.length > 0) {
          const monthlyData = response.result[0].monthlyLeaveData;
          const keys = Object.keys(monthlyData)

          console.log("value of month name: ", keys);
          console.log("fetchMonthlyChartData Response Data : ", response)

          this.updateMonthlyChartData(monthlyData);

          Object.keys(monthlyData).forEach(month => {
            const leaveDays: string[] = monthlyData[month].leaveDays;

            if (!this.totalDays) {
              this.totalDays = leaveDays.slice(); // Make a copy of leaveDays array
            } else {
              this.totalDays = this.totalDays.concat(leaveDays);
            }
            leaveDays.forEach(dateStr => {
              const date = new Date(dateStr);
              const dayIndex = this.getDayOfWeek(date);

              if (this.dayIndexCountMap.has(dayIndex)) {
                this.dayIndexCountMap.set(dayIndex, this.dayIndexCountMap.get(dayIndex)! + 1);
              } else {
                this.dayIndexCountMap.set(dayIndex, 1);
              }
            });
          });


          console.log("weekly chart before map : ", this.weeklyChartData);


          this.dayIndexCountMap.forEach((count, dayName) => {
            const dayObject = this.weeklyChartData.find(day => day.label === dayName);
            if (dayObject) {
              dayObject.value = count * 10;
            }



          });
          console.log(`Total Leave days: `, this.totalDays);
          console.log(`Day Index Count Map: `, this.dayIndexCountMap);


          console.log(`weeklyChartData : `, this.weeklyChartData);

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
      // console.log(monthLabel) 
      const dataForMonth = monthlyLeaveData[monthKey];
      // console.log(dataForMonth)
      const value = dataForMonth ? dataForMonth.totalLeaveTaken * 10 : 0;
      // console.log( value);
      const duration = dataForMonth ? dataForMonth.leaveDuration || 0 : 0; // Default to 0 if no duration data
      this.monthlyChartData.push({ label: monthLabel, value, duration });
      // console.log(" monthly chart values : ",this.monthlyChartData)
    });
  }

  private getMonthName(monthKey: string): string {
    const [year, month] = monthKey.split('-');
    const monthIndex = parseInt(month, 10);
    const months = [
      'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
      'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
    ];
    return `${months[monthIndex - 1]} `;
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
  updateDaysOfWeek(pendingLeaves: PendingLeave[]): void {
    const daysOfWeekCount: { [key: string]: number } = {};

    pendingLeaves.forEach(leave => {
      const leaveDates = leave.leaveDates.split(',');
      leaveDates.forEach(dateStr => {
        const dayOfWeek = new Date(dateStr).toLocaleDateString('en-US', { weekday: 'long' });
        if (daysOfWeekCount[dayOfWeek]) {
          daysOfWeekCount[dayOfWeek]++;
        } else {
          daysOfWeekCount[dayOfWeek] = 1;
        }
      });
    });

    // Prepare data for chart
    this.daysOfWeek = Object.keys(daysOfWeekCount);
    const daysOfWeekData = this.daysOfWeek.map(day => daysOfWeekCount[day]);

    this.chartOptions.series = [{
      name: 'Leave Days',
      data: daysOfWeekData
    }];
    this.chartOptions.xaxis = {
      categories: this.daysOfWeek
    };

    // Update chart options to reflect the changes
    this.chartOptions = { ...this.chartOptions };
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
