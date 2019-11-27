import { Injectable } from '@angular/core';
// import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, throwError, BehaviorSubject } from 'rxjs';
// import { tap } from 'rxjs/operators';
// import { MatDialogRef, MatSnackBar } from '@angular/material';
// import { UserprofileService } from '../user/userprofile/userprofile.service';
// import { FormBuilder } from '@angular/forms';
// import { FlagDocumentComponent } from '../document/flag-document/flag-document.component';

// const httpOptions = {
//   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
// };

@Injectable({
  providedIn: 'root'
})
export class FlagDocumentService {
  // userProfileService: any;

  constructor() { }

  // constructor(private http: HttpClient) { }
  // flagUrl = '/docapi/documents/flag';
  // profile: any = {};

  // public flagDocument(data: any, data1: any, data2: any): Observable<any> {
  //   console.log('flag document: ', data);

  //   return this.http.post(this.flagUrl, data, data1, data2, httpOptions)
  //     .pipe(
  //       tap((data1: any) => {
  //         console.log('doc upload ::', data1);
  //       })
  //     );
  // }
  // function(error: HttpErrorResponse) {
  //   return throwError(error);
  // }
  // userProfile() {
  //   this.userProfileService.userProfile().subscribe(res => {
  //     // console.log('user response', res);

  //     // console.log('userDetails', this.userDetails);
  //     this.profile = res;
  //     // console.log('profile', this.profile.email);
  //   });
  // }
}
