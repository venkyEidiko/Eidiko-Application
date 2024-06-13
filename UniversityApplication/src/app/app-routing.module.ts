import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';

import { RouterModule, Routes } from '@angular/router';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { ResetPasswordConfirmationComponent } from './reset-password-confirmation/reset-password-confirmation.component';
import { OtpComponent } from './otp/otp.component';
import { RegistrationComponent } from './registration/registration.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { MeComponent } from './me/me.component';
import { InboxComponent } from './inbox/inbox.component';
import { MyteamComponent } from './myteam/myteam.component';
import { OrgComponent } from './org/org.component';
import { MyfinancesComponent } from './myfinances/myfinances.component';

import { LayoutComponent } from './layout/layout.component';


import { LeavesComponent } from './leaves/leaves.component';

import { ErrorComponent } from './error/error.component';

const routes: Routes = [
  {path:'',component:RegistrationComponent},
  {path:'login',component:LoginComponent},
  
  { path: 'forgotpassword', component: ForgotpasswordComponent },
  { path: 'reset', component: ResetpasswordComponent },
  { path: 'resetPwdConfirmation', component: ResetPasswordConfirmationComponent },
  { path: 'otp', component: OtpComponent },
  {path:'layout',component:LayoutComponent,
    children:
    [
   {
    path:'home',component:HomeComponent,
    children:[
      {
        path:'dashboard',component:DashboardComponent
      },
      {
        path:'welcome',component:WelcomeComponent
      }
    ]
   },
   {
    path:'me',component:MeComponent,
    children:[
      {
       path:'leaves',component:LeavesComponent
            }
    ]
   },
   {
    path:'inbox',component:InboxComponent
   }
   ,{
    path:'myteam',component:MyteamComponent
   },
   {
    path:'myfinance',component:MyfinancesComponent
   }
   ,{
    path:'org',component:OrgComponent
   }
    ]
  },
  {path:'**', component:ErrorComponent}
]


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ]
})
export class AppRoutingModule { }
