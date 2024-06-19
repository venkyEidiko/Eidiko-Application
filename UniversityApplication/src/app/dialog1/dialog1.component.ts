import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-dialog1',
  templateUrl: './dialog1.component.html',
  styleUrls: ['./dialog1.component.css']
})
export class Dialog1Component {

  isOpen: boolean = true;
  leavetable: any[] = [];
  maternitytable: any[] = [];
  optionaltable: any[] = [];
  paidtable: any[] = [];
  unpaidtable: any[] = [];
  showBalanceHistory: boolean = false;
  showPolicy: boolean = false;
  pol: any={};

  constructor(private dialogRef: MatDialogRef<Dialog1Component>) {}

  closeDialog(): void {
    this.dialogRef.close();
  }

  onCompoff(): void {
    this.leavetable = [
      {
        "transaction date": "01 jan 2024",
        "change": 0,
        "balance": 0,
        "reason": "Leave accural at the start of the year"
      }
    ];
    this.maternitytable = [];
    this.optionaltable = [];
    this.paidtable = [];
    this.unpaidtable = [];
    this.showBalanceHistory = true;
    this.showPolicy = false; 
  }

  onMaternity(): void {
    this.maternitytable = [
      {
        "transaction date": "01 jan 2024",
        "change": 182,
        "balance": 0,
        "reason": "Leave accural at the start of the year"
      }
    ];
    this.leavetable = [];
    this.optionaltable = [];
    this.paidtable = [];
    this.unpaidtable = [];
    this.showBalanceHistory = true;
    this.showPolicy = false;
  }

  onOptional(): void {
    this.optionaltable = [
      {
        "transaction date": "8th April 2024 ",
        "change": 0,
        "balance": 0,
        "reason": "Leave applied for Tuesday, 09 April 2024"
      }
    ];
    this.leavetable = [];
    this.maternitytable = [];
    this.paidtable = [];
    this.unpaidtable = [];
    this.showBalanceHistory = true;
    this.showPolicy = false;
  }

  onPaid(): void {
    this.paidtable = [
      {
        "transaction date": "8th April 2024 ",
        "change": 5,
        "balance": 5,
        "reason": "Leave applied for Tuesday, 09 April 2024"
      }
    ];
    this.leavetable = [];
    this.maternitytable = [];
    this.optionaltable = [];
    this.unpaidtable = [];
    this.showBalanceHistory = true;
    this.showPolicy = false; 
  }

  onUnpaid(): void {
    this.unpaidtable = [
      {
        "transaction date": "6th April 2024 ",
        "change": -1,
        "balance": -1,
        "reason": "Penalty due to less work hours on 4 April 2024"
      }
    ];
    this.leavetable = [];
    this.maternitytable = [];
    this.paidtable = [];
    this.optionaltable = [];
    this.showBalanceHistory = true;
    this.showPolicy = false; 
  }

  toggleBalanceHistory(): void {
    this.showBalanceHistory = true;
    this.showPolicy = false;
  }

  togglePolicy(): void {
    this.showPolicy = !this.showPolicy; // Toggle accordion visibility
    this.showBalanceHistory = false;

    // Update pol object based on table content
    if (this.leavetable.length > 0) {
      this.pol = {
        type: 'Comp Offs',
       
        expanded: this.showPolicy
      };
    } else if (this.maternitytable.length > 0) {
      this.pol = {
        type: 'Maternity Leave',
        content: "Maternity policy information...",
        expanded: this.showPolicy
      };
    } else if (this.optionaltable.length > 0) {
      this.pol = {
        type: 'Optional Leave',
        content: "Optional leave policy information...",
        expanded: this.showPolicy
      };
    } else if (this.paidtable.length > 0) {
      this.pol = {
        type: 'Paid Leave',
        content: "Paid leave policy information...",
        expanded: this.showPolicy
      };
    } else if (this.unpaidtable.length > 0) {
      this.pol = {
        type: 'Unpaid Leave',
        content: "Unpaid leave policy information...",
        expanded: this.showPolicy
      };
    } else {
      this.pol = {}; // Reset pol object if no table has data
    }
  }

}
