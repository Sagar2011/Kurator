import { environment } from './../../environments/environment.prod';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TrackUserActivityService {
  documentsAddedUrl = environment.addedDocuments;
  // documentsViewedUrl: string;

  getDocumentsAdded(datePicked: any): Observable<any> {
    return this.http.get(`${this.documentsAddedUrl}?date=${datePicked}`);
  }
  // getDocumentsViewed(datePicked: Date): Observable<any> {
  //   return this.http.get(`${this.documentsViewedUrl}?date=${datePicked}`);
  // }

  constructor(private http: HttpClient) { }
}
