import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: "root",
})
export class EmailCheckService {
  constructor(private http: HttpClient) {}
  private isAuthenticated: boolean = false;

  email = "";
  checkEmail(email: string) {
    this.isAuthenticated = true;
    this.email = email;
    return this.http.get<any>(`http://localhost:8082/api/getByEmail/${email}`);
  }
  getEmail() {
    this.isAuthenticated = true;
    return this.email;
  }

  setUnAuthenticatedUser() {
    this.isAuthenticated = false;
  }
  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }
}
