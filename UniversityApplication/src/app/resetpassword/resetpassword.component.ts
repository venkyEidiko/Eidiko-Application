import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent {
  password: string = '';
  confirmPassword: string = '';

  constructor(private authService: AuthService) { }

  resetPwdConfirmation() {
    if (this.password !== this.confirmPassword) {
      // Handle password mismatch error
      return;
    }

    this.authService.resetPassword(this.password).subscribe({
      next: (response) => {
        console.log('Password reset successful');
      },
      // error: (error) => {
      //   console.error('Password reset failed:', error);
      // }
    });
  }
}
