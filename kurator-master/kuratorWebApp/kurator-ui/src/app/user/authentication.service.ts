import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private static readonly LOGOUT_URL = 'users/auth/userlogout';
  public currentUser;
  private redirectUrl = '/';
  private isAuthCorrect: BehaviorSubject<boolean> = new BehaviorSubject(false);
  constructor(private http: HttpClient) {
    // this.isAuthenticated();
  }

  getUserProfile() {
    return this.http.get('/users/userprofile');
  }

  getIsAuthCorrect(): Observable<any> {
    return this.isAuthCorrect.asObservable();
  }
  getRedirectUrl(): string {
    return this.redirectUrl;
  }
  setRedirectUrl(url: string): void {
    this.redirectUrl = url;
  }
  isAuthenticated() {
    if (!this.currentUser) {
      this.getUserProfile().subscribe(userProfile => {
        // console.log('in isAuthenticated: ', userProfile);
        if (userProfile) {
          this.currentUser = userProfile;
          this.isAuthCorrect.next(true);
          // return true;
          console.log(userProfile);
        } else {
          this.isAuthCorrect.next(false);
          // console.log('No UserProfile found');
        }
      });
    } else {
      this.isAuthCorrect.next(false);
    }
  }

  logout() {
    return this.http.get(AuthenticationService.LOGOUT_URL);
  }

}
