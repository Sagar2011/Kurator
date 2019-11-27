import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material';
import { GuestDashboardComponent } from '../guest-dashboard/guest-dashboard.component';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { DocumentModel } from 'src/app/data-model/documentModel';
import { SearchService } from 'src/app/service/search.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-app-home',
  templateUrl: './app-home.component.html',
  styleUrls: ['./app-home.component.css'],
  encapsulation: ViewEncapsulation.ShadowDom
})
export class AppHomeComponent implements OnInit {
  // tslint:disable-next-line: max-line-length
  cookieMessage = 'We use cookies and other tracking technologies to improve your browsing experience on our application, to show you personalized content and experience, to analyze our website traffic, and to understand where our visitors are coming from. By browsing our website, you consent to our use of cookies and other tracking technologies.';
  cookieDismiss = 'Accept';
  constructor(
    private dialog: MatDialog,
    private service: SearchService) {
    this.searchForm = new FormGroup({
      inputQuery: new FormControl(''),
    });
  }
  inputQuery: string;
  searchForm: FormGroup;
  totalTimeTaken: any = 0;
  notFoundStatus = false;
  notFoundMessage: string;
  listDocuments: DocumentModel[];
  noOfDocs: any = 0;
  // title = 'kurator-ui';
  // show = true;
  // tslint:disable-next-line: use-lifecycle-interface
  ngOnInit() {
    const cc = window as any;
    cc.cookieconsent.initialise({
      palette: {
        popup: {
          background: '#000000'
        },
        button: {
          background: '#DCDCDC',
          text: '#000000'
        }
      },
      theme: 'classic',
      content: {
        message: this.cookieMessage,
        dismiss: this.cookieDismiss
      }
    });
  }
  guestSession() {
    this.dialog.open(GuestDashboardComponent, { width: '3500px' });
  }
  getDocument() {
    try {
      const startTime = performance.now();
      this.service.getGuestData(this.searchForm.value).subscribe((data) => {
        // fetched the results from the particular query
        if (data.statusCode === 200) {
          this.service.setValue(data.result);
          this.service.setExceptionMessage('');
          this.service.setMessage(false);
          this.totalTimeTaken = performance.now() - startTime;
          this.totalTimeTaken = (this.totalTimeTaken / 1000).toFixed(2);
          this.service.changeMessage(this.totalTimeTaken);
        } else
          if (data.statusCode === 404) {
            this.service.setMessage(true);
          } else
            if (data.statusCode === 406) { // for naviagting to the page of language not allowed
              console.log('inside abusive');
              // this.route.navigate(['dashboard']);
              // this.route.navigate(['../abusive']);
            } else {
              this.notFoundMessage = data.message;
              this.service.setExceptionMessage(this.notFoundMessage);
              this.service.setValue([]);

            }
        // this.route.navigate(['search']);

      }, (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.notFoundMessage = 'Page Not Found';
          this.service.setExceptionMessage(this.notFoundMessage);
          this.service.setValue([]);

        } else {
          this.notFoundMessage = 'Sorry Server is Down!!!!!!!';
          this.service.setExceptionMessage(this.notFoundMessage);
          this.service.setValue([]);
        }
      });
      this.searchForm.reset();
      this.dialog.open(GuestDashboardComponent, { width: '3500px' });
    } catch (error) {
      this.notFoundMessage = 'Something Went Wrong try again after sometime............';
      this.notFoundStatus = true;

    }



  }

  // agree() {
  // this.show = false;
  // }

}


