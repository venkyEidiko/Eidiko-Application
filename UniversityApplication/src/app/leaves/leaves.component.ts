import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { LeaveService } from '../services/leave.service';
import { Dialog1Service } from '../services/dialog1.service';
import { CompdialogService } from '../services/compdialog.service';
import { DialogService } from '../services/dialog.service';
import { LeavetypeService } from '../services/leavetype.service';
import { TableService } from '../services/table.service';
import { LoginService } from '../services/login.service';
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
interface LeaveResponse {
  totalElements: number;
  totalPages: number;
  size: number;
  content: PendingLeave[];
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  first: boolean;
  last: boolean;
  numberOfElements: number;
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  empty: boolean;
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
  filteredData!: MatTableDataSource<PendingLeave>;
  selectedStatuses: Set<string> = new Set<string>();
  statusOptions: string[] = ['Pending', 'Approved', 'Rejected', 'Cancelled']
  
  selectedOptions: Set<string> = new Set<string>();

  options: string[] = ['Paid Leave', 'Unpaid Leave', 'Maternity Leave','Optional Leave','Comp Offs']; 
 
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
  statusTypeOptions:string[]=[]
  selectedLeaveTypes: Set<string> = new Set<string>();
  //selectedStatus:Set<string>=new Set<string>();
  
    selectedStatus: string[] = [];
  consumedLeaves = 0;
  availableLeaves = 0;
  totalLeave = 12;
  pendingLeave: LeaveRequest[] | null = null;
  consumedPaidLeave = 0;
  availablePaidLeave = 0;
  consumedCompOffs = 0;
  availableCompOffs = 0;
  consumedUnpaidLeave =0;
  availableUnpaidLeave = 0;
  paidLeaveCount: number = 0;
  unpaidLeaveCount: number = 0;




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
    private loginService: LoginService,
    private dialogService: DialogService,
    private dialogService1: Dialog1Service,
    private compdialogService: CompdialogService,
    private leavetypeService: LeavetypeService,
    private tableService:TableService,
    

  ) {}
   employeeId = this.loginService.getEmployeeData().employeeId;
   
  ngOnInit(): void {
   
    const pageSize = 5;
   
    this.fetchLeaveBalance(this.employeeId);
    this.fetchMonthlyChartData(this.employeeId);
    this.fetchLeaveData([],[],0,pageSize);

    this.leaveTypeOptions = ['Paid Leave', 'unpaid', 'Maternity Leave', 'Optional Leave', 'comp offs'];

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

  fetchLeaveData(leaveTypes: string[], statuses: string[], page: number, size: number): void {
  
    this.tableService.fetchFilteredLeaveData(this.employeeId, leaveTypes, statuses, page, size).subscribe(
      (response: LeaveResponse) => {
        if (response && response.content) {
          this.pendingLeaves = response.content
        
          this.dataSource = new MatTableDataSource<PendingLeave>(this.pendingLeaves);
          this.dataSource.paginator = this.paginator;
          console.log("paginator",this.dataSource.paginator)
          this.applyFilter();
          

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
    const pageSize = event.pageSize;
    const pageIndex = event.pageIndex;
    const leaveTypes = Array.from(this.selectedOptions);
    const statuses = Array.from(this.selectedStatuses);
    this.fetchLeaveData(leaveTypes, statuses, pageIndex, pageSize);
  }
  // aggregateLeaveCounts(): void {
  //   this.paidLeaveCount = 0;
  //   this.unpaidLeaveCount = 0;
  //   if (this.pendingLeaves) {
  //     this.pendingLeaves.forEach(leave => {
  //       if (leave.leaveType === 'Paid Leave') {
  //         this.paidLeaveCount++;
  //       } else if (leave.leaveType === 'unpaid') {
  //         this.unpaidLeaveCount++;
          
  //       }
  //     });
  //   }
  //   this.updateChartOptions();
  // }

  
  fetchLeaveBalance(employeeId: number): void {
    this.leavetypeService.fetchLeaveBalance(employeeId).subscribe(
      (response: any) => {
        console.log("employee ", employeeId);
        console.log("fetchLeaveBalance method Response Data : ", response);
  
        if (response.status === 'SUCCESS' && response.result.length > 0) {
          response.result.forEach((leave: any) => {
            console.log(`Leave Type: ${leave.leaveType}`);
            console.log(`Consumed Leave: ${leave.consumedLeave}`);
            console.log(`Available Leave: ${leave.availableLeave}`);
  
            if (leave.leaveType === 'Paid Leave') {
              this.consumedPaidLeave = leave.consumedLeave;
              this.availablePaidLeave = leave.totalLeave - leave.consumedLeave;
              this.updateMonthlyChartData(leave.monthlyLeaveData);
              this.updateChartOptions();
            } else if (leave.leaveType === 'comp offs') {
              this.consumedCompOffs = leave.consumedLeave;
              this.availableCompOffs = this.totalLeave - leave.consumedLeave;
              this.updateMonthlyChartData(leave.monthlyLeaveData);
                this.updateChartOptions();
            } else if (leave.leaveType == 'unpaid') {
              this.consumedUnpaidLeave = leave.consumedLeave;
             
              console.log("consumedunpaidleave",this.consumedUnpaidLeave);
              
              this.updateMonthlyChartData(leave.monthlyLeaveData);
              this.updateChartOptions();
            }
  
            // pending leave data
            this.pendingLeave = leave.pendingLeave;
            console.log("Pending leave: ", this.pendingLeave);
            this.pendingLeave?.forEach(pending => {
              console.log("Pending leave Object: ", pending);
            });
          });
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

    const dayIndex = date.getDay(); 
    return this.weekday[dayIndex];
  }

  dayIndexCountMap = new Map<string, number>();

  fetchMonthlyChartData(employeeId: number): void {
    this.leavetypeService.fetchMonthlyLeaveData(employeeId).subscribe(
      (response: any) => {
        if (response.status === 'SUCCESS' && response.result.length > 0) {
          response.result.forEach((resultItem: { leaveType: any; monthlyLeaveData: any; }) => {
            const leaveType = resultItem.leaveType;
            const monthlyData = resultItem.monthlyLeaveData;
  
            if (monthlyData) {
              const keys = Object.keys(monthlyData);
              console.log("Leave Type: ", leaveType);
              console.log("Month keys: ", keys);
  
              // Update monthly chart data for each leave type
              this.updateMonthlyChartData(monthlyData);
  
              // Process leave days for each month
              Object.keys(monthlyData).forEach(month => {
                const leaveDays: string[] = monthlyData[month].leaveDays;
  
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
            } else {
              console.warn(`No monthly leave data available for ${leaveType}`);
            }
          });
  
          // Example of updating weeklyChartData based on dayIndexCountMap
          this.dayIndexCountMap.forEach((count, dayName) => {
            const dayObject = this.weeklyChartData.find(day => day.label === dayName);
            if (dayObject) {
              dayObject.value = count * 10; // Example value adjustment
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
  

    this.updateChart();
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
    this.chartOptions1.series = [this.availablePaidLeave, this.consumedPaidLeave];
    this.chartOptions1 = { ...this.chartOptions1 };

    this.chartOptions3.series = [this.consumedPaidLeave, this.consumedUnpaidLeave];
    
    console.log("chartOptions3",this.consumedUnpaidLeave)
    this.chartOptions3 = { ...this.chartOptions3 };

    this.chartOptions2.series = [this.consumedUnpaidLeave];
    this.chartOptions2 = { ...this.chartOptions2 };
    this.chartOptions4.series = [this.availableCompOffs,this.consumedCompOffs];
    this.chartOptions4 = { ...this.chartOptions4};
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
  chartOptions4 = {
    series: [0, 0],
    chart: {
      type: 'donut',
      width: 150,
      height: 220
    },
    labels: ['AvailableCompOffLeaves', 'ConsumedCompOffLeaves'],
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
    labels: ['Paid Leaves', 'unpaid'],
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

  applyFilter(): void {
    if (!this.dataSource) return;

    let filteredData = this.dataSource.data;

    if (this.selectedOptions.size > 0) {
      filteredData = filteredData.filter(item => this.selectedOptions.has(item.leaveType || ''));
    }

    if (this.selectedStatuses.size > 0) {
      filteredData = filteredData.filter(item => this.selectedStatuses.has(item.status));
    }

    this.filteredData = new MatTableDataSource<PendingLeave>(filteredData);
    this.filteredData.paginator = this.paginator;
  }

  onSelectionChange(selectedOptions: Set<string>): void {
    this.selectedOptions = selectedOptions;
    this.applyFilter();
  }

  onStatusSelected(status: string): void {
    if (this.selectedStatuses.has(status)) {
      this.selectedStatuses.delete(status);
    } else {
      this.selectedStatuses.add(status);
    }
    this.applyFilter();
  }

  isStatusSelected(status: string): boolean {
    return this.selectedStatuses.has(status);
  }
 
  isAllSelected(): boolean {
    return this.statusOptions.length === this.selectedStatus.length;
  }

  toggleAllSelection(): void {
    if (this.isAllSelected()) {
      this.selectedStatus = [];
    } else {
      this.selectedStatus = [...this.statusOptions];
    }
  }
}







