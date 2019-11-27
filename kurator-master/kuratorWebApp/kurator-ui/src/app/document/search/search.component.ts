import { Component, OnInit, OnChanges } from '@angular/core';
import { SearchService } from 'src/app/service/search.service';
import { Router } from '@angular/router';
import { MatSnackBarModule, MatSnackBar } from '@angular/material';
import { AuthenticationService } from 'src/app/user/authentication.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  exceptionMessage: string;
  constructor(
    private service: SearchService,
    private route: Router,
    private snack: MatSnackBar,
    private authenticationService: AuthenticationService) { }
  docList: any = [];
  subscription: any;
  preUrl: any;
  status = false;
  topics: any = [];
  message;
  totalDocuments: number;
  searchTime: any;
  favStatus = false;
  isWaiting = true;
  query: string;
  subscribe: any;


  ngOnInit() {
    // for query
    this.service.getQuery().subscribe((res) => {
      this.query = res;
      this.service.getDefination(this.query).subscribe((data) => {
        this.message = data;
      });

    });
    this.service.getExceptionMessage().subscribe(message => {
      this.exceptionMessage = message;
      if (this.exceptionMessage === 'abusive') {
        this.route.navigate(['abusive']);
      } else if (this.exceptionMessage === 'blocked') {
        this.snack.open('Sorry, you are blocked', 'dismiss');
        this.onLogout();
      } else {
        this.service.setExceptionMessage('');
      }
    });
    // for handling the exception in the modules
    this.service.getMessage().subscribe(msg => {
      // this.status = msg;
      this.isWaiting = false;
    });
    this.subscription = this.service.getValue().subscribe(data => {
      // this.isWaiting = false;
      this.docList = data;
      this.totalDocuments = this.docList.length;
    });
  }

  onLogout() {
    this.authenticationService.logout()
      .subscribe(res => {
        console.log(' logout response', res);
      });
    this.route.navigate(['']);
  }
  // tslint:disable-next-line: use-lifecycle-interface
  ngOnDestroy(): void {
    // this.service.setMessage(false);
    this.service.setValue([]);
  }
}
