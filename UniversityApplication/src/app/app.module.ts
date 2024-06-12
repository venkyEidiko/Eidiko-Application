import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatToolbarModule} from '@angular/material/toolbar';
import { AppComponent } from './app.component';

import { SidenavComponent } from './sidenav/sidenav.component';

import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { MatIconModule} from  '@angular/material/icon';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { ResetPasswordConfirmationComponent } from './reset-password-confirmation/reset-password-confirmation.component'
import { FormsModule } from '@angular/forms';
import { OtpComponent } from './otp/otp.component';
import { HttpClientModule } from '@angular/common/http';



import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTabsModule } from '@angular/material/tabs';
import {MatGridListModule} from '@angular/material/grid-list';
import { HomeComponent } from './home/home.component';

import { DashboardComponent } from './dashboard/dashboard.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { RouterModule, Routes } from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import { MeComponent } from './me/me.component';
import { InboxComponent } from './inbox/inbox.component';
import { MyteamComponent } from './myteam/myteam.component';
import { MyfinancesComponent } from './myfinances/myfinances.component';
import { OrgComponent } from './org/org.component';
import { ClockComponent } from './clock/clock.component';
import { DonutchartComponent } from './donutchart/donutchart.component';
import * as ApexCharts from 'apexcharts';
import { NgApexchartsModule } from 'ng-apexcharts';



const approutes:Routes=[
  {
    path:'home',component:HomeComponent,children:[
      {
        path:'dashboard', component:DashboardComponent
      },
      {
        path:'welcome',component:WelcomeComponent
      },
      {
        path: '', redirectTo: 'dashboard', pathMatch: 'full' 
      }
    ]
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path:'me',component:MeComponent
  },
  {
    path:'inbox',component:InboxComponent
  },
  {
    path:'myteam',component:MyteamComponent
  },
  {
    path:'org',component:OrgComponent
  },
  {
    path:'myfinance',component:MyfinancesComponent
  }
]
@NgModule({
  declarations: [
    AppComponent,

    SidenavComponent,
    
    HomeComponent,
          
          DashboardComponent,
          WelcomeComponent,
          MeComponent,
          InboxComponent,
          MyteamComponent,
          MyfinancesComponent,
          OrgComponent,
          ClockComponent,
          DonutchartComponent,OtpComponent
          
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatGridListModule,
    MatCardModule,
    RouterModule.forRoot(approutes),
    NgApexchartsModule

    NavbarComponent,
    ForgotpasswordComponent,
    ResetpasswordComponent,
    ResetPasswordConfirmationComponent,
    
    MatToolbarModule,
    FormsModule,HttpClientModule, MatIconModule,
  ],

  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
