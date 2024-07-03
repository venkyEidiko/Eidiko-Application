import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})

export class EmailCheckService {

  constructor(private http: HttpClient) { }
   email='';
 checkEmail(email: string) {
  this.email=email;
    return this.http.get<any>(`http://10.0.0.38:8082/api/getByEmail/${email}`);

}
getEmail(){
  return this.email;
}
}
