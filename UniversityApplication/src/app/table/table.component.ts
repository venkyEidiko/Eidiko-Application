import { Component, Input, ViewChild, OnChanges, SimpleChanges } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnChanges {
  @Input() dataSource: any[] = [];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  displayedColumns: string[] = [];
  dataSourceMat!: MatTableDataSource<any>;

  constructor() {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['dataSource'] && changes['dataSource'].currentValue) {
      console.log('New data received:', changes['dataSource'].currentValue);
      this.displayedColumns = Object.keys(changes['dataSource'].currentValue[0]);
      console.log('Displayed columns:', this.displayedColumns);
      this.dataSourceMat = new MatTableDataSource<any>(changes['dataSource'].currentValue);
      console.log('MatTableDataSource created:', this.dataSourceMat);
      
    }
  }
  ngAfterViewInit() {
    this.dataSourceMat.paginator = this.paginator;
  }
}
