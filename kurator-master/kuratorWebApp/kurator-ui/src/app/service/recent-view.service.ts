import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecentViewService {
  addRecentUrl = environment.recentUrl;
  getRecentUrl = environment.getRecent;
  constructor(private http: HttpClient) { }


  addRecent(data: string): Observable<any> {
    return this.http.patch(`${this.addRecentUrl}`, data);
  }

  getRecent(): Observable<any> {
    return this.http.get(`${this.getRecentUrl}`);
  }
}
