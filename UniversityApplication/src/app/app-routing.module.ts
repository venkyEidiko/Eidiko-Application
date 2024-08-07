import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { ForgotpasswordComponent } from "./forgotpassword/forgotpassword.component";
import { ResetpasswordComponent } from "./resetpassword/resetpassword.component";
import { ResetPasswordConfirmationComponent } from "./reset-password-confirmation/reset-password-confirmation.component";
import { OtpComponent } from "./otp/otp.component";
import { RegistrationComponent } from "./registration/registration.component";
import { LoginComponent } from "./login/login.component";
import { HomeComponent } from "./home/home.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { WelcomeComponent } from "./welcome/welcome.component";
import { MeComponent } from "./me/me.component";
import { InboxComponent } from "./inbox/inbox.component";
import { MyteamComponent } from "./myteam/myteam.component";
import { OrgComponent } from "./org/org.component";
import { MyfinancesComponent } from "./myfinances/myfinances.component";
import { LayoutComponent } from "./layout/layout.component";
import { AttandanceComponent } from "./attandance/attandance.component";
import { LeavesComponent } from "./leaves/leaves.component";
import { ErrorComponent } from "./error/error.component";
import { ProfileComponent } from "./profile/profile.component";
import { ProfileAboutComponent } from "./profile-about/profile-about.component";
import { ProfileJobComponent } from "./profile-job/profile-job.component";
import { ProfilePrflComponent } from "./profile-prfl/profile-prfl.component";
import { ProfileDocComponent } from "./profile-doc/profile-doc.component";
import { AboutSummaryComponent } from "./about-summary/about-summary.component";
import { AboutTimelineComponent } from "./about-timeline/about-timeline.component";
import { AboutWallactivityComponent } from "./about-wallactivity/about-wallactivity.component";
import { CalenderComponent } from "./calender/calender.component";
import { SummaryComponent } from "./summary/summary.component";

const routes: Routes = [
  { path: "", component: LoginComponent},
  { path: "register", component: RegistrationComponent },

  { path: "forgotpassword", component: ForgotpasswordComponent },
  { path: "reset", component: ResetpasswordComponent },
  {
    path: "resetPwdConfirmation",
    component: ResetPasswordConfirmationComponent,
  },
  { path: "otp", component: OtpComponent },
  {
    path: "layout",
    component: LayoutComponent,
    children: [
      {
        path: "myprofile",
        component: ProfileComponent,
        children: [
          { path: "", redirectTo: "about/summary", pathMatch: "full" },
          {
            path: "about",
            component: ProfileAboutComponent,
            children: [
              { path: "", redirectTo: "summary", pathMatch: "full" },
              { path: "summary", component: AboutSummaryComponent },
              { path: "timeline", component: AboutTimelineComponent },
              { path: "wall-activity", component: AboutWallactivityComponent },
            ],
          },
          { path: "job", component: ProfileJobComponent },
          { path: "documents", component: ProfileDocComponent },
          { path: "profile", component: ProfilePrflComponent },
        ],
      },
      {
        path: "home",
        component: HomeComponent,
        children: [
          { path: "", redirectTo: "dashboard", pathMatch: "full" },
          { path: "dashboard", component: DashboardComponent },
          { path: "welcome", component: WelcomeComponent },
        ],
      },
      {
        path: "me",
        component: MeComponent,
        children: [
          { path: "", redirectTo: "leaves", pathMatch: "full" },

          { path: "attandance", component: AttandanceComponent },
          { path: "leaves", component: LeavesComponent },
        ],
      },
      { path: "inbox", component: InboxComponent },
      {
        path: "myteam",
        component: MyteamComponent,
        children: [
          { path: '', redirectTo: 'summary', pathMatch: 'full' },
          { path: "summary", component: SummaryComponent },
        ],
      },
      { path: "myfinance", component: MyfinancesComponent },
      { path: "org", component: OrgComponent },
    ],
  },
  { path: "**", component: ErrorComponent },
];

@NgModule({
  declarations: [],
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
