import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OtpService } from '../services/otp.service';

@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.css']
})
export class OtpComponent {
  otp: string = '';
  errorMessage: string = '';
  toEmail: string = 'nalla.harshini@gmail.com';
  generatedOtp: string = '';

  constructor(private router: Router, private otpService: OtpService) { }

  generateOtp() {
    this.otpService.sendOtpEmail(this.toEmail).subscribe({
      next: (response) => {
        console.log('OTP sent successfully:', response);
        if (response.status === 'SUCCESS' && response.result && response.result.length > 0) {
          this.generatedOtp = response.result[0];
          console.log('Generated OTP:', this.generatedOtp);
        } else {
          console.log('Failed to send OTP');
          this.errorMessage = 'Failed to send OTP. Please try again later.';
        }
      },
      error: (error) => {
        console.error('Error sending OTP:', error);
        this.errorMessage = 'Error sending OTP. Please try again later.';
      }
    });
  }

 onSubmit() {
    if (this.otp === this.generatedOtp) {
      console.log('OTP matched');
      this.router.navigate(['/reset']);
    } else {
      console.log('OTP does not match');
      this.errorMessage = 'Invalid OTP. Please try again.';
    }
  }
}
