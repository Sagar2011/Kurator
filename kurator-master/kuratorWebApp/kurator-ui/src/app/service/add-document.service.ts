import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AddDocumentService {
  userProfileService: any;
  claps: any;
  constructor(private http: HttpClient) { }
  addStatus = '/index/indexingStatus';
  userUrl = '/docapi/documents';
  bulkUrl = '/docapi/bulkUpload';
  semanticUrl = '/search/documents';
  clapUrl = '/docapi/claps';
  valueObs: BehaviorSubject<string> = new BehaviorSubject<string>('');
  clapObs: BehaviorSubject<string> = new BehaviorSubject<string>(this.claps);
  // userEmail = '/userapi/userprofile';
  profile: any = {};

  public getValue(): Observable<string> {
    return this.valueObs;
  }

  public getClapsValue(): Observable<any> {
    return this.clapObs;
  }

  getClaps(docId: any) {
    return this.http.get(`${this.clapUrl}?docId=${docId}`);
  }

  addClaps(docId: any): Observable<any> {
    return this.http.patch(
      `${this.clapUrl}?docId=${docId}`,
      {});
  }
  public uploadDoc(data: any): Observable<any> {
    console.log('uploadValue: ', data);

    return this.http.post(this.userUrl, data, httpOptions)
      .pipe(
        tap((data1: any) => {
          console.log('doc upload ::', data1);
        })
      );
  }
  public bulkUpload(data: any): Observable<any> {
    console.log('bulkUploadValue: ', data);

    return this.http.post(this.bulkUrl, data, httpOptions)
      .pipe(
        tap((data1: any) => {
          console.log('doc upload ::', data1);
        })
      );
  }
  function(error: HttpErrorResponse) {
    return throwError(error);
  }
  userProfile() {
    this.userProfileService.userProfile().subscribe(res => {
      // console.log('user response', res);

      // console.log('userDetails', this.userDetails);
      this.profile = res;
      // console.log('profile', this.profile.email);
    });
  }

  public addStatusCall(data: any): Observable<any> {
    return this.http.get(`${this.addStatus}?documentId=${data}`, { responseType: 'text' });
  }

  public getDocument(data: any) {
    return this.http.get(`${this.semanticUrl}/${data}`);
  }
}

