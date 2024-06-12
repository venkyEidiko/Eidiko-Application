import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';

import { RouterModule, Routes } from '@angular/router';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { ResetPasswordConfirmationComponent } from './reset-password-confirmation/reset-password-confirmation.component';
import { OtpComponent } from './otp/otp.component';
const routes: Routes = [
  { path: 'home', component: NavbarComponent },
  { path: 'forgotpassword', component: ForgotpasswordComponent },
  { path: 'reset', component: ResetpasswordComponent },
  { path: 'resetPwdConfirmation', component: ResetPasswordConfirmationComponent },
  { path: 'otp', component: OtpComponent }
]



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ]
})
export class AppRoutingModule { }
