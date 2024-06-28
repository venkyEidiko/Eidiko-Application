
import { Component, EventEmitter, Input, Output } from '@angular/core';


@Component({
  selector: 'app-dropdowntable',
  templateUrl: './dropdowntable.component.html',
  styleUrls: ['./dropdowntable.component.css']
})
export class DropdowntableComponent {

  @Input() options: string[] = [];
 
  @Input() selectedOptions: Set<string> = new Set<string>();
  
  @Output() selectionChange = new EventEmitter<Set<string>>();
 
  isOpen = false;
 

  toggleDropdown(): void {
    this.isOpen = !this.isOpen;
  }
 
  toggleOption(option: string): void {
    if (this.selectedOptions.has(option)) {
      this.selectedOptions.delete(option);
    } else {
      this.selectedOptions.add(option);
    }
    this.selectionChange.emit(this.selectedOptions);
  }
 
  clearSelection(): void {
    this.selectedOptions.clear();
    this.selectionChange.emit(this.selectedOptions);
  }

  trackByFn(index: number, item: string): string {
    return item;
  }
}