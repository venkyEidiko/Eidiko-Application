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
import { OtpComponent } from './otp/otp.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTabsModule } from '@angular/material/tabs';
import { MatGridListModule } from '@angular/material/grid-list';
import { HomeComponent } from './home/home.component';
import { ReactiveFormsModule } from '@angular/forms';
import { WelcomeComponent } from './welcome/welcome.component';
import { RouterModule, Routes } from '@angular/router';
import { MatCardModule } from '@angular/material/card'
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
import { CommonModule, DatePipe } from '@angular/common';
import { AttandanceComponent } from './attandance/attandance.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressBar, MatProgressBarModule } from '@angular/material/progress-bar';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { ProfileComponent } from './profile/profile.component';
import { ProfileAboutComponent } from './profile-about/profile-about.component';
import { ProfileJobComponent } from './profile-job/profile-job.component';
import { ProfilePrflComponent } from './profile-prfl/profile-prfl.component';
import { ProfileDocComponent } from './profile-doc/profile-doc.component';
import { AboutSummaryComponent } from './about-summary/about-summary.component';
import { AboutTimelineComponent } from './about-timeline/about-timeline.component';
import { AboutWallactivityComponent } from './about-wallactivity/about-wallactivity.component';
import { TableComponent } from './table/table.component';
import { Donut1Component } from './donut1/donut1.component';
import { DialogComponent } from './dialog/dialog.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { NgChartsModule } from 'ng2-charts';
import { Dialog1Component } from './dialog1/dialog1.component';
import { CompDialogComponent } from './comp-dialog/comp-dialog.component';
import { FormsModule } from '@angular/forms';
import { MatExpansionModule } from '@angular/material/expansion';
import { DropdowntableComponent } from './dropdowntable/dropdowntable.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { LeavesComponent } from './leaves/leaves.component';
import { BarchartComponent } from './barchart/barchart.component';
import { CalenderComponent } from './calender/calender.component';
import { ShiftRequestFormComponent } from './shift-request-form/shift-request-form.component';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { HolidayDialogComponent } from './holiday-dialog/holiday-dialog.component'; // Import CarouselModule
import { CheckboxdropdownComponent } from './checkboxdropdown/checkboxdropdown.component';
import { PickerComponent, PickerModule } from '@ctrl/ngx-emoji-mart';
import { TruncatePipe } from 'src/pipes/turncate.pipe';
import { DashboardComponent } from './dashboard/dashboard.component';
import { Edit1Component } from './edit1/edit1.component';
import { Edit2Component } from './edit2/edit2.component';
import { Edit3Component } from './edit3/edit3.component';
import { Edit4Component } from './edit4/edit4.component';
import { SummaryComponent } from './summary/summary.component';
import { CapatlizePipe } from '../pipes/capatlize.pipe';
import { FirstletterPipe } from '../pipes/firstletter.pipe';
import { InterceptorService } from './services/interceptor.service';
import { NgxUiLoaderModule, NgxUiLoaderHttpModule } from 'ngx-ui-loader';
import { WorkFromHomeDialogeComponent } from './work-from-home-dialoge/work-from-home-dialoge.component';
import {  MatSnackBarModule } from '@angular/material/snack-bar';
import { Truncate1Pipe } from 'src/pipes/truncate1.pipe';
import { MatTooltipModule } from '@angular/material/tooltip';


@NgModule({
  declarations: [
    AppComponent,
    TruncatePipe,
    LoginComponent,
    CalenderComponent,
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
    Edit1Component,
    ClockComponent,
    DonutchartComponent,
    OtpComponent,
    LayoutComponent,
    FooterComponent,
    LeavesComponent,
    BarchartComponent,
    AttandanceComponent,
    ProfileComponent,
    ProfileAboutComponent,
    ProfileJobComponent,
    ProfilePrflComponent,
    ProfileDocComponent,
    AboutSummaryComponent,
    AboutTimelineComponent,
    AboutWallactivityComponent,
    TableComponent,
    Donut1Component,
    DialogComponent,
    Dialog1Component,
    CompDialogComponent,
    DropdowntableComponent,
    ShiftRequestFormComponent,
    HolidayDialogComponent,
    CheckboxdropdownComponent,
    Edit1Component,
    Edit2Component,
    Edit3Component,
    Edit4Component,
    SummaryComponent,
    CapatlizePipe,
    FirstletterPipe,
    WorkFromHomeDialogeComponent,
    Truncate1Pipe

  ],
  imports: [
    PickerModule,
    FormsModule,
    MatDatepickerModule,
    BrowserModule,
    MatButtonModule,
    MatDialogModule,
    BrowserAnimationsModule,
    MatAutocompleteModule,
    MatTabsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatSelectModule,
    MatInputModule,
    MatDatepickerModule,
    MatGridListModule,
    MatProgressBarModule,
    MatMenuModule,
    RouterModule,
    MatSnackBarModule,
    NgApexchartsModule,
    NgChartsModule,
    AppRoutingModule,
    MatToolbarModule,
    FormsModule,
    HttpClientModule,
    MatIconModule,
    MatCheckboxModule,
    MatExpansionModule,
    ReactiveFormsModule,
    CommonModule,
    MatProgressBarModule,
    MatButtonModule,
    MatMenuModule,
    MatPaginatorModule,
    MatTableModule,
    CommonModule,
    FormsModule,
    MatSelectModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatCardModule,
    NgxUiLoaderModule,
    MatTooltipModule,
    NgxUiLoaderHttpModule.forRoot({
      showForeground: true,
      exclude: ["http://10.0.0.60:8080/api/searchByKeyword/"]
    }),

  ],


  providers: [{ provide: HTTP_INTERCEPTORS, useClass: InterceptorService, multi: true }, // Register interceptor
    DatePipe,
    LeavesComponent
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
