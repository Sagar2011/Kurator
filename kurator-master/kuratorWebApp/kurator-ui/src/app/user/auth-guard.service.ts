import { Injectable } from '@angular/core';

import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  currentUser;
  returnValue;
  stateurl;
  constructor(private router: Router, private authService: AuthenticationService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    this.authService.getIsAuthCorrect().subscribe((data) => {
      this.currentUser = data;
      // console.log(this.currentUser);
    });
    if (this.currentUser) {
      // logged in so return true
      return true;
    }

    this.router.navigate([''], { queryParams: { returnUrl: state.url } });
    this.stateurl = state.url;
    return false;
  }
  // canLoad(route: Route, ) {
  //   const url: string = route.path;
  //   console.log('Url:' + url);
  //   this.authService.getIsAuthCorrect().subscribe((data) => {
  //     this.currentUser = data;
  //     // console.log(this.currentUser);
  //   });
  //   if (this.currentUser) {
  //     // logged in so return true
  //     return true;
  //   }

  //   this.authService.setRedirectUrl(url);
  //   this.router.navigate([''], { queryParams: { returnUrl: this.stateurl } });
  //   return false;
  // }


}
