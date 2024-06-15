import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AppComponent } from './app.component';

import { SidenavComponent } from './sidenav/sidenav.component';

import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';

import { MatIconModule } from '@angular/material/icon';


import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { ResetPasswordConfirmationComponent } from './reset-password-confirmation/reset-password-confirmation.component';
import { FormsModule } from '@angular/forms';
import { OtpComponent } from './otp/otp.component';
import { HttpClientModule } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTabsModule } from '@angular/material/tabs';
import { MatGridListModule } from '@angular/material/grid-list';
import { HomeComponent } from './home/home.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { RouterModule, Routes } from '@angular/router';

import {MatCardModule} from '@angular/material/card'


import { MeComponent } from './me/me.component';
import { InboxComponent } from './inbox/inbox.component';
import { MyteamComponent } from './myteam/myteam.component';
import { MyfinancesComponent } from './myfinances/myfinances.component';
import { OrgComponent } from './org/org.component';
import { ClockComponent } from './clock/clock.component';
import { DonutchartComponent } from './donutchart/donutchart.component';
import * as ApexCharts from 'apexcharts';
import { NgApexchartsModule } from 'ng-apexcharts';
import { LayoutComponent } from './layout/layout.component';
import { FooterComponent } from './footer/footer.component';

import { CommonModule } from '@angular/common';
import { AttandanceComponent } from './attandance/attandance.component';
import {MatProgressBar, MatProgressBarModule} from '@angular/material/progress-bar';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';

import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';

import { LeavesComponent } from './leaves/leaves.component';

import { NgxChartsModule } from '@swimlane/ngx-charts';


import { BarchartComponent } from './barchart/barchart.component';

import { NgChartsModule } from 'ng2-charts';




@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,





    SidenavComponent,
    NavbarComponent,
    HomeComponent,
    RegistrationComponent,
    ForgotpasswordComponent,
    ResetpasswordComponent,
    ResetPasswordConfirmationComponent,
    DashboardComponent,
    WelcomeComponent,
    MeComponent,
    InboxComponent,
    MyteamComponent,
   
    MyfinancesComponent,
    OrgComponent,
    ClockComponent,
    DonutchartComponent,
    OtpComponent,
    LayoutComponent,
    FooterComponent,

    LeavesComponent,
    
    BarchartComponent,
    
        
    

    AttandanceComponent,

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatGridListModule,
  MatProgressBarModule,
    MatMenuModule,
    RouterModule,
    NgChartsModule,
    NgApexchartsModule,
    AppRoutingModule,

    MatToolbarModule,
    FormsModule,
  
    HttpClientModule,
    MatIconModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    MatTableModule,
    MatCardModule,
     MatButtonModule

  ],

  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
