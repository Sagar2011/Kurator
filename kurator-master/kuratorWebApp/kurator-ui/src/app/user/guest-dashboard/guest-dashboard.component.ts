import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { SearchService } from 'src/app/service/search.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { DocumentModel } from 'src/app/data-model/documentModel';

@Component({
  selector: 'app-guest-dashboard',
  templateUrl: './guest-dashboard.component.html',
  styleUrls: ['./guest-dashboard.component.css']
})
export class GuestDashboardComponent implements OnInit, OnDestroy {
  exceptionMessage: string;
  docList: any = [];
  subscription: any;
  preUrl: any;
  status;
  topics: any = [];
  message = 'No Document Found';
  totalDocuments: number;
  searchTime: any;
  favStatus = false;
  constructor(
    private dialog: MatDialogRef<GuestDashboardComponent>,
    private service: SearchService,
  ) {

  }

  ngOnInit() {
    // for handling the exception in the modules
    this.service.getExceptionMessage().subscribe(message => {
      this.exceptionMessage = message;
    });
    this.service.getMessage().subscribe(msg => {
      this.status = msg;
    });
    this.subscription = this.service.getValue().subscribe(data => {
      this.docList = data;
      this.totalDocuments = this.docList.length;
    });
  }
  close() {
    this.dialog.close();
  }
  ngOnDestroy(): void {
    // this.service.setValue([]);
    // this.service.setMessage(false);
  }
}
