import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AddIntentService {
  userUrl = '/search/intents/terms';

  constructor(private http: HttpClient) { }
  public addIntent(data: any): Observable<any> {
    console.log('AddedIntent: ', data);

    return this.http.patch(`${this.userUrl}`, data, httpOptions);
  }
  public getIntent(): Observable<any> {
    return this.http.get(`${this.userUrl}/admin`);
  }
}
