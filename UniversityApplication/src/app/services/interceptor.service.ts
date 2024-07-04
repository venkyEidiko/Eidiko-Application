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
  providedIn: 'root'
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
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      this.loaderService.showLoading();
    if (this.loginService.isAuthenticatedUser()) {
      this.loginService.unAuthenticated();
      return next.handle(request);
    }


    // Customize headers here based on your needs
    const token = localStorage.getItem("jwt-token");
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Custom-Header': 'value'  // Add additional headers as needed
    });

    // Clone the request and attach headers
    const modifiedRequest = request.clone({ headers });
    console.log('Interceptor - Request intercepted:', modifiedRequest);

    // Pass the cloned request instead of the original request to the next handle
    return next.handle(modifiedRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          return this.handle401Error(request, next);
        } else {
          return throwError(error);
        }
      })
    );
  }

  private addToken(request: HttpRequest<any>, token: any) {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // let updatedRequest: HttpRequest<any>;

    return new Observable((observer) => {
      const token = this.loginService.generateTokenByRefreshtoken()

      // Update localStorage with the new token
      localStorage.setItem("jwt-token", token);
      this.loginService.unAuthenticated();
      // Clone the request and add the new token to headers
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Custom-Header': 'value'  // Add additional headers as needed
      });
      const updatedRequest = request.clone({ headers });

      // Log the updated request for debugging
      console.log('Interceptor - Updated Request:', updatedRequest);

      // Proceed with the updated request
      next.handle(updatedRequest).subscribe(
        (event: HttpEvent<any>) => {
          observer.next(event);
        },
        (error) => {
          observer.error(error);
        },
        () => {
          observer.complete();
        }
      );
    },

    );
  }
}