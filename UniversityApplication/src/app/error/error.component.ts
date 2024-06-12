import { Component } from '@angular/core';
import { Location } from '@angular/common';
@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent {

  errorDetails = {
    type: 'Not Found',
    status: "404",
    message: "The page you are looking for cannot found or you do not have access to this page."
  }
  constructor(private location: Location) { }
  showError(errorDetails: any) {
    this.errorDetails = errorDetails
  }
  goBack(): void {
    this.location.back();
  }
}
