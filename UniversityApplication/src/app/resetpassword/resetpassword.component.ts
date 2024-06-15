import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent {
  
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }
  
    const requestBody = {
      email: this.email,
      newPassword: this.password,
      confirmPassword: this.confirmPassword
    };
  
    this.authService.resetPassword(requestBody).subscribe({
      next: (response) => {
        console.log('Password reset successful');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        if (typeof error === 'string' && error === 'Password updated successfully') {
        
          console.log('Password reset successful');
          this.router.navigate(['/resetPwdConfirmation']);
        } else {
          console.error('Password reset failed:', error);
          if (error.status === 400 && error.error && error.error.status === 'Passwords do not match') {
            this.errorMessage = 'Passwords do not match. Please try again.';
          } else {
            this.errorMessage = 'Failed to reset password. Please try again later.';
          }
        }
      }
    });
  }
}  
