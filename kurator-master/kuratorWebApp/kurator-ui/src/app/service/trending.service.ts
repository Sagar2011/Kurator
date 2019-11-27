import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment.prod';
import { Observable, BehaviorSubject } from 'rxjs';
import { TrendingDocumentModel } from '../data-model/trendingDocumentModel';

@Injectable({
  providedIn: 'root'
})
export class TrendingService {
  addList = [];
  trendList = [];
  trendUrl = '/users/topics/documents';
  constructor(private http: HttpClient) {
    this.httpOptions = {
      headers: new HttpHeaders(
        {
          '/topics/documentsContent-Type': 'application/json',
        })
    };
  }
  valueObs: BehaviorSubject<any> = new BehaviorSubject(this.trendList);
  url = environment.trendingdocsUrl;
  addValueObs: BehaviorSubject<any> = new BehaviorSubject(this.addList);

  private httpOptions;

  public getValue(): Observable<any> {
    return this.valueObs;
  }

  public getValueAdd(): Observable<any> {
    return this.addValueObs;
  }

  public getTrending() {
    this.http.get(this.trendUrl).subscribe((fav: any) => {
      this.trendList = fav;
      this.valueObs.next(this.trendList);
    });
  }

  public getAdded() {
    this.http.get(this.url).subscribe((fav: any) => {
      this.addList = fav;
      this.addValueObs.next(this.addList);
    });
  }

}
