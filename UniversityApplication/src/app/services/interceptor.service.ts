import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, switchMap, tap, throwError } from 'rxjs';
import { LoginService } from './login.service';
import { LoaderService } from './loader.service';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { OtpService } from './otp.service';
import { EmailCheckService } from './email-check.service';


@Injectable({
  providedIn: "root",
})
export class InterceptorService implements HttpInterceptor {
  constructor(
    private loginService: LoginService,
    private emailCheckService: EmailCheckService,
    private otpService: OtpService,
    private authService: AuthService, private loaderService: LoaderService, private router: Router
  ) { 
    this.initialize();
  }
  private initialize() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.loaderService.showLoading();
      } else if (event instanceof NavigationEnd) {
        this.loaderService.hideLoading();
      }
    });
  }
  

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (
      this.loginService.isAuthenticatedUser() ||
      this.emailCheckService.isAuthenticatedUser() ||
      this.authService.isAuthenticatedUser() ||
      this.otpService.isAuthenticatedUser()
    ) {
      console.log("calling free api");
      this.loginService.setUnAuthenticatedUser();
      this.authService.setUnAuthenticatedUser();
      this.otpService.setUnAuthenticatedUser();
      this.emailCheckService.setUnAuthenticatedUser();
      return next.handle(request);
    }

    return this.addToken(localStorage.getItem("jwt-token"), request, next).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error("Interceptor - Error:", error);
        if (error.status === 401) {
          return this.loginService.getTokenByRefreshToken().pipe(
            switchMap((response: any) => {
              const token = response.result[0];
              localStorage.setItem("jwt-token", token);
              return this.addToken(token, request, next);
            }),
            catchError((refreshError: any) => {
              console.error("Refresh token error:", refreshError);

              return throwError(refreshError);
            })
          );

        } else {
          return throwError(error);
        }
      })

    );
  }

  addToken(token: any, request: any, next: any): any {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      "Custom-Header": "value",
    });
    const modifiedRequest = request.clone({ headers });
    console.log("Interceptor - Request intercepted:", modifiedRequest);
    return next.handle(modifiedRequest);
  }
}
