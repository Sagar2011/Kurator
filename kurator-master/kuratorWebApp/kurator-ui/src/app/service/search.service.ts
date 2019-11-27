import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment.prod';
import { Observable, BehaviorSubject } from 'rxjs';
import { DocumentModel } from '../data-model/documentModel';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { AuthenticationService } from '../user/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  constructor(
    private http: HttpClient,
    private route: Router, private snackBar: MatSnackBar,
    private authenticationService: AuthenticationService,
  ) {
    this.httpOptions = {
      headers: new HttpHeaders(
        {
          'Content-Type': 'application/json',
        })
    };
  }

  // for calling the semantic service for the query results
  url = environment.searchUrl;
  guestUrI = environment.guestUrl;
  wikiApi = environment.wikiApi;
  queryUrl: string;
  guestQueryUrl: string;
  wikiUrl: string;
  private httpOptions;
  searchResults = [];
  query: string;
  private valueObs: BehaviorSubject<any> = new BehaviorSubject(this.searchResults);
  private queryObs: BehaviorSubject<any> = new BehaviorSubject(this.query);
  // for sharing data between the search and message in the result
  private messageObs: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  // for exception handling in the client and server side
  private exceptionMsgObs: BehaviorSubject<string> = new BehaviorSubject<string>(null);

  // for sharing response time between search and result page
  private responseTime: BehaviorSubject<any> = new BehaviorSubject<any>(0);
  currentResponseTime = this.responseTime.asObservable();

  changeMessage(message: any) {
    this.responseTime.next(message);
  }

  public setExceptionMessage(value: string): void {
    this.exceptionMsgObs.next(value);
  }

  public getExceptionMessage(): Observable<string> {
    return this.exceptionMsgObs;
  }


  public setQuery(value: string): void {
    this.queryObs.next(Object.values(value));
  }

  public getQuery(): Observable<string> {
    return this.queryObs;
  }



  public setMessage(value: boolean): void {
    this.messageObs.next(value);
  }

  public getMessage(): Observable<boolean> {
    return this.messageObs;
  }


  public setValue(value: Array<DocumentModel>): void {
    this.valueObs.next(value);
  }

  public getValue(): Observable<any> {
    return this.valueObs;
  }


  getData(data: any, email: any) {
    this.queryUrl = this.url + '?' + 'query=' + Object.values(data) + '&email=' + email;
    this.http.get(this.queryUrl).subscribe((searchResult: any) => {
      if (searchResult.statusCode === 200) {
        this.searchResults = searchResult.result;
        this.setMessage(false);
      } else
        if (data.statusCode === 404) {
          this.setMessage(true);
          this.searchResults = searchResult.result;

        } else
          if (data.statusCode === 406) { // for naviagting to the page of language not allowed
            console.log('inside abusive');
            this.setExceptionMessage('abusive');
            this.searchResults = searchResult.result;
            // this.route.navigate(['abusive']);
          } else
            if (data.statusCode === 423) {  // for logging out the user and blocking it
              this.setExceptionMessage('blocked');
              this.searchResults = searchResult.result;
              // this.snackBar.open('Sorry, you are blocked', 'dismiss');
              // this.onLogout();
            }
      this.valueObs.next(this.searchResults);
    });
  }

  getGuestData(data: any): Observable<any> {
    this.guestQueryUrl = this.guestUrI + '?' + 'query=' + Object.values(data);
    console.log(this.guestQueryUrl);
    return this.http.get(this.guestQueryUrl, this.httpOptions);
  }

  getDataFromWiki(data: any): Observable<any> {
    this.wikiUrl = this.wikiApi + '?action=opensearch&search=' + Object.values(data) + '&limit=3&format=json';
    return this.http.get(this.wikiUrl);
  }

  onLogout() {
    this.authenticationService.logout()
      .subscribe(res => {
        console.log(' logout response', res);
      });
    this.route.navigate(['']);
  }


  getDefination(search: any) {
    return this.http.get(`${this.wikiApi}${search}`);
  }
}
