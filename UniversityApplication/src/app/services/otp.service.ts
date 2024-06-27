import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OtpService {
  private apiUrl = 'http://10.0.0.73:8082/api/sendMail';

  constructor(private http: HttpClient) { }
  sendOtpEmail(toEmail: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?toMail=${toEmail}`);
  }
}
