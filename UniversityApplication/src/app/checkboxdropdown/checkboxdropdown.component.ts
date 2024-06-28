import { Component ,Input,Output,EventEmitter} from '@angular/core';

@Component({
  selector: 'app-checkboxdropdown',
  templateUrl: './checkboxdropdown.component.html',
  styleUrls: ['./checkboxdropdown.component.css']
})
export class CheckboxdropdownComponent {
  @Input() options: string[] = [];
  @Output() selectionChange = new EventEmitter<string[]>();

  selectedOptions: string[] = [];
  dropdownVisible = false;

  toggleDropdown() {
      this.dropdownVisible = !this.dropdownVisible;
  }

  toggleOption(option: string) {
      if (this.isSelected(option)) {
          this.selectedOptions = this.selectedOptions.filter(opt => opt !== option);
      } else {
          this.selectedOptions.push(option);
      }
      this.selectionChange.emit(this.selectedOptions);
  }

  isSelected(option: string): boolean {
      return this.selectedOptions.includes(option);
  }

  closeDropdown() {
      this.dropdownVisible = false;
  }
}

