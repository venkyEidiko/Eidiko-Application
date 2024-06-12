import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.css']
})
export class OtpComponent {
  otp: string = '';

  constructor(private router:Router) { }

  onSubmit() {
    
    console.log('OTP submitted:', this.otp);
    
}
resetPwdPage()
{
  this.router.navigate(['/reset'])
}
}
