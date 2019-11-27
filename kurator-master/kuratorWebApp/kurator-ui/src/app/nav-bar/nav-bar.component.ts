import { OnInit, HostListener, Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthenticationService } from '../user/authentication.service';
import { MatDialogRef } from '@angular/material';
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../user/login/login.component';
import { UserprofileService } from '../user/userprofile/userprofile.service';
import { filter } from 'rxjs/internal/operators/filter';
import { FlagDocumentComponent } from '../document/flag-document/flag-document.component';
​
@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {
  public currentWindowWidth: number;
  loginRef: MatDialogRef<LoginComponent>;
  forSearch: boolean;
  user: any = {};
  userImage: string;
  userRole: any;
  userRoleStatus: boolean;
  title = 'kurator-ui';
  show = true;
​
  constructor(
    public router: Router,
    private authenticationService: AuthenticationService,
    private dialog: MatDialog,
    private userprofileService: UserprofileService
  ) { }
​
  openDialog() {
    this.loginRef = this.dialog.open(LoginComponent, {
      width: '400px',
      height: '400px'
    });
  }
​
  ngOnInit() {
    this.currentWindowWidth = window.innerWidth;
    this.forSearch = false;
    this.router.events
      .pipe(
        filter(e => e instanceof NavigationEnd)
      )
      .subscribe((navEnd: NavigationEnd) => {
        if (navEnd.urlAfterRedirects !== '/') {
          this.userProfile();
        }
      });
  }
  onClickKurator() {
    this.router.navigate(['/dashboard']);
  }
​
  onDashboard() {
    this.router.navigate(['/dashboard']);
  }
  trackingUserDetail() {
    this.router.navigate(['/admin/trackUserDetails']);
  }
  // for routing to Manage intent view
  onManageIntent() {
    this.router.navigate(['/admin/intent']);
​
  }
​
  onManageConcept() {
    this.router.navigate(['admin/concept']);
  }
  onManageOntology() {
    this.router.navigate(['admin/ontology']);
  }
  onManageDocument() {
    this.router.navigate(['/admin/document']);
  }
​
  onMyCuration() {
    this.router.navigate(['/curation']);
  }
  onSearch() {
    this.forSearch = !this.forSearch;
  }
  // for adjusting screen size in mobile and desktop view
  @HostListener(' window:resize')
  onResize() {
    this.currentWindowWidth = window.innerWidth;
    this.forSearch = false;
  }
​
  onLogout() {
    this.authenticationService.logout()
      .subscribe(res => {
        // console.log(' logout response', res);
      });
    this.router.navigate(['']);
  }
​
  profile() {
    this.router.navigate(['/profile']);
  }
​
  onPlaylist() {
    this.router.navigate(['/playlist']);
  }
​
  userProfile() {
    console.log('calling user profile in nav bar');
    this.userprofileService.userProfile().subscribe(res => {
      this.user = res;
      this.userImage = 'url(' + this.user.avatarURL + ')';
      this.userRole = this.user.userRole;
      if (this.userRole === 'ADMIN') {
        this.userRoleStatus = true;
      } else {
        this.userRoleStatus = false;
      }
      console.log('userImage', this.userImage);
    });
  }
​
  flagDocument() {
    const dialogRef = this.dialog.open(FlagDocumentComponent, {
      width: '500px'
    });
  }
​
​
  agree() {
    this.show = false;
  }
}



