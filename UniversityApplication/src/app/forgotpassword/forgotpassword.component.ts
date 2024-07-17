import { Component } from '@angular/core';

import { Router } from '@angular/router';

import { EmailCheckService } from '../services/email-check.service';

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.css']
})
export class ForgotpasswordComponent {
  email: string = '';

  isEmailMatch: boolean = false;
  errorMessage: string = '';

  constructor(private router: Router, private apiService: EmailCheckService) { }

  Otppage() {
    this.router.navigate(['/otp']);

  }

  onSubmit() {
    console.log("forgot pwd",this.email);

    this.apiService.checkEmail(this.email).subscribe({
      next: (response: any) => {

        const backendEmail = response.result[0]; 
        console.log("backEnd email : ",backendEmail);
        console.log("input email : ",this.email);
        
        
        if (backendEmail === this.email) {
          console.log('Email matches');
          this.Otppage();
          
        } else {
          console.log('Email does not match');
          this.errorMessage = 'Email does not match';

        }
      },
      error: (error) => {
        console.error('Error:', error);
       
    console.log('Backend Error Message:', error.error.error);
          this.errorMessage = error.error.error; 
       
        

      }
    });
  }
}
