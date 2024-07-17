import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private employeeData: any = null;
  private jwtToken: string | null = null;
  private isAuthenticated: boolean = false;
  url = "http://localhost:8082/";

  constructor(private http: HttpClient) { }

  register(data: any): Observable<any> {
    this.isAuthenticated = true
    return this.http.post<any>(this.url + "api/save", data);
  }

  login(data: any): Observable<any> {
    this.isAuthenticated = true
    return this.http.post<any>(this.url + "login1", data);
  }

  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }

  setAuthanticationForRefresh(){
    this.isAuthenticated = true;
  }

  setUnAuthenticatedUser() {
    this.isAuthenticated = false;
  }


  setEmployeeData(employeeId: number) {
    //this.employeeData = data;
    this.http.get(`${this.url}api/getBybyEmployeeID/${employeeId}`).subscribe(res=>{
      this.employeeData=res;
      this.employeeData=this.employeeData.result[0];
      localStorage.setItem('employee-data', JSON.stringify(this.employeeData));
    })

  }
  searchEmployee(search: any): Observable<any[]> {
    return this.http.get<any[]>(this.url + "api/searchByKeyword/" + search)
  };

  getEmployeeData(): any {
    if (!this.employeeData) {
      const storedData = localStorage.getItem('employee-data');
      if (storedData) {
        try {
          this.employeeData = JSON.parse(storedData);
        } catch (error) {
          console.error('Error parsing employee data from localStorage:', error);

        }
      }
    }
    return this.employeeData;
  }

  setJwtToken(token: string) {
    this.jwtToken = token;
    localStorage.setItem('jwt-token', this.jwtToken);
  }

  getJwtToken(): string | null {
    if (!this.jwtToken) {
      this.jwtToken = localStorage.getItem('jwt-token');
    }
    return this.jwtToken;
  }

  getAuthHeaders(): HttpHeaders {
    const token = this.getJwtToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getTokenByRefreshToken(): Observable<any> {
    this.isAuthenticated = true
    const url = this.url + "refresh/" + localStorage.getItem("refresh-token");
    console.log(url);
    return this.http.get<any>(url);
  }
}