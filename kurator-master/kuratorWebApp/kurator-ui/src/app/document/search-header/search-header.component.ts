import { Component, OnInit, HostListener, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormBuilder, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { SearchService } from 'src/app/service/search.service';
import { UserprofileService } from 'src/app/user/userprofile/userprofile.service';
import { AuthenticationService } from 'src/app/user/authentication.service';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-search-header',
  templateUrl: './search-header.component.html',
  styleUrls: ['./search-header.component.css']
})
export class SearchHeaderComponent implements OnInit {

  inputQuery: string;
  searchForm: FormGroup;
  listDocuments: any;
  notFoundStatus: boolean;
  notFoundMessage: string;
  totalTimeTaken: any;
  userDetails: any = [];

  // for enabling message if status not found
  status: boolean;
  constructor(
    private service: SearchService,
    formBuilder: FormBuilder, private route: Router,
    private userProfileService: UserprofileService,
    private router: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private snackBar: MatSnackBar
  ) {
    this.searchForm = new FormGroup({
      inputQuery: new FormControl(''),
    });

  }
  public currentWindowWidth: number;
  forSearch: boolean;
  ngOnInit() {
    this.notFoundStatus = false;
    this.currentWindowWidth = window.innerWidth;
    this.forSearch = false;
  }
  onSearch() {
    this.forSearch = true;
  }
  onCancel() {
    this.forSearch = false;
  }

  @HostListener('window:resize')
  onResize() {
    this.currentWindowWidth = window.innerWidth;
  }

  getDocument() {
    try {
      const startTime = performance.now();
      this.userProfileService.userProfile().subscribe((res) => {
        this.userDetails = res;
        this.service.setQuery(this.searchForm.value);
        this.service.getData(this.searchForm.value, this.userDetails.email);
        this.route.navigate(['/search']);
        this.searchForm.reset();
      });
    } catch (error) {
      this.notFoundMessage = 'Something Went Wrong try again after sometime............';
      this.service.setExceptionMessage(this.notFoundMessage);
      this.service.setValue([]);
    }
  }

  onClick() {
    alert('clicked');
  }

  routeToPage() {
    this.route.navigate(['abusive']).then(
      x => console.log(x + 'by routung')
    );
  }
  onLogout() {
    this.authenticationService.logout()
      .subscribe(res => {
        console.log(' logout response', res);
      });
    this.route.navigate(['']);
  }
}
