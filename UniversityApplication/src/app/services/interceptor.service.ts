import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { LoginService } from "./login.service";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class InterceptorService implements HttpInterceptor {
  constructor(private loginService: LoginService, private router: Router) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (this.loginService.isAuthenticatedUser()) {
      this.loginService.setUnAuthenticatedUser();
      return next.handle(request);

    }

    // Customize headers here based on your needs
    const token = localStorage.getItem("jwt-token");
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      "Custom-Header": "value", // Add additional headers as needed
    });

    // Clone the request and attach headers
    const modifiedRequest = request.clone({ headers });
    console.log("Interceptor - Request intercepted:", modifiedRequest);

    // Pass the cloned request instead of the original request to the next handle
    return next.handle(modifiedRequest).pipe(
      tap(
        (event) => console.log("Interceptor - Response received:", event),
        (error) => {
          console.error("Interceptor - Error:", error);
          if (error.status == 401) {
            this.router.navigate(["/login"]);
          }
        }
      )
    );
  }
}
