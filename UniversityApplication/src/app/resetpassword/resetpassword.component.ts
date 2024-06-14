import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent {
  password: string = '';
  confirmPassword: string = '';

  constructor(private router:Router) { }

  resetPwdConfirmation() {
    if (this.password !== this.confirmPassword) {
      // Handle password mismatch error
      return;
    }
    else{
      this.router.navigate(['/resetPwdConfirmation'])
    }

    
  }
}
