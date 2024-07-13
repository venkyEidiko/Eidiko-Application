import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class TableService {
  private baseUrl = 'http://localhost:8082';

  constructor(private http: HttpClient) {}

  fetchFilteredLeaveData(employeeId: number, leaveTypes: string[], statuses: string[], page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('employeeId', employeeId.toString())
      .set('page', page.toString())
      .set('size', size.toString());

    // Adding leaveTypes as query parameter
    if (leaveTypes && leaveTypes.length > 0) {
      params = params.set('leaveTypes', leaveTypes.join(','));
    }

   
    if (statuses && statuses.length > 0) {
      params = params.set('statuses', statuses.join(','));
    }

    return this.http.get(`${this.baseUrl}/leave/getSortLeaveType`, { params });
  }
}
