import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent {
  password: string = '';
  confirmPassword: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  resetPwdConfirmation() {
    if (this.password !== this.confirmPassword) {
      return;
    }
    else{
      this.router.navigate(['/resetPwdConfirmation'])
    }

    const requestBody = {
      newPassword: this.password,
      confirmPassword: this.confirmPassword
    };

    this.authService.resetPassword(requestBody).subscribe({
      next: (response) => {
        console.log('Password reset successful');
     
        this.router.navigate(['/resetPwdConfirmation']); 
      },
      error: (error) => {
        console.error('Password reset failed:', error);
      
      }
    });

  }
}
