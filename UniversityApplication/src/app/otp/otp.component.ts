import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { OtpService } from '../services/otp.service';
import { EmailCheckService } from '../services/email-check.service';

@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.css']
})
export class OtpComponent {
  otp: string = '';

  errorMessage: string = '';
  toEmail: string =this.emailService.getEmail();
  generatedOtp:string|null='';

  constructor(private router: Router, private otpService: OtpService,private emailService:EmailCheckService) { }

  generateOtp() {
    this.otpService.sendOtpEmail(this.toEmail).subscribe({
      next: (response) => {
        console.log('OTP sent successfully:', response);
        if (response.status === 'SUCCESS' && response.result && response.result.length > 0) {
          this.generatedOtp=response.result[0];
          localStorage.setItem('generatedOtp',JSON.stringify(this.generateOtp));
          this.generatedOtp=localStorage.getItem('generatedOtp');
          console.log('Generated OTP:', this.generatedOtp);
        } else {
          console.log('Failed to send OTP');
          this.errorMessage = 'Failed to send OTP. Please try again later.';
        }
      },
      error: (error) => {
        console.error('Error sending OTP:', error.errorMessage);
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