import { Component, OnInit } from '@angular/core';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialog, MatDialogRef } from '@angular/material';
import { BulkUploadComponent } from '../bulk-upload/bulk-upload.component';
import {TrackDocumentService} from '../../service/track-document.service';
import * as moment from 'moment';
import * as async from 'async';
import { single } from 'rxjs/operators';
export interface Section {
  documentId: string;
  title: string;
  intent: string;
  // addedOn: Date;
  addedOn: string;
  addedBy: string;
  // indexedOn: Date;
  indexedOn: string;
  confidenceRating: number;
}
// import { TrackDocumentService } from '../../service/track-document.service';


// export interface Section {
//   documentId: string;
//   title: string;
//   intent: string;
//   // addedOn: Date;
//   addedOn: string;
//   addedBy: string;
//   // indexedOn: Date;
//   indexedOn: string;
//   rating: number;
// }


@Component({
  selector: 'app-track-document',
  templateUrl: './track-document.component.html',
  styleUrls: ['./track-document.component.css']
})
export class TrackDocumentComponent implements OnInit {
  listOfDocument: any = [];
  documentFromSemantic: any = [];
  // folders: Section[] = [
    // {
    //   title: 'Document Title',
    //   addedOn: '1 day Ago',
    //   intent: 'introduction',
    //   indexedOn: '3 hr ago',
    //   confidenceRating: 85,
    //   addedBy: 'Ankit Kumar'
    // },
    // {
    //   title: 'Java',
    //   addedOn: '2 days ago',
    //   intent: 'advanced details',
    //   indexedOn: '1 day ago',
    //   confidenceRating: 90,
    //   addedBy: 'Manoj '
    // },
    // {
    //   title: 'Spring',
    //   addedOn: '4 days ago',
    //   intent: 'introduction',
    //   indexedOn: '2 days ago',
    //   confidenceRating: 60,
    //   addedBy: 'Soumik Paul'
    // }
  // ];
  constructor( private dialog: MatDialog, private trackDocument: TrackDocumentService) { }
  public folders = {
    documentId: String,
    title: String,
    intent: String,
    // addedOn: Date;
    addedOn: undefined,
    addedBy: String,    // indexedOn: Date;
    indexedOn: undefined,
    rating: Number
  };
  public countvar = 0;
  public allData = [];
  public confidenceData = [];

  public count() {
    console.log('Called ', this.countvar);
    this.countvar++;
    return this.countvar;
  }
  // constructor(private trackDocument: TrackDocumentService) { }


  ngOnInit() {
    this.ngGetDocument();
  }

  ngGetDocument() {
    this.trackDocument.getDocument().subscribe((data: any) => {
      async.map(data.resultList, this.myfunction.bind(this), (err, res) => {
        // console.log('resutl:: ', res);
        this.allData = res;
        // this.confidenceUpdate();

      });

    });
  }
  // console.log('document response', data);
  // this.listOfDocument = data.resultList;
  // // this.listOfDocument.addedOn = data.resultList.addedOn;
  // let i = 0;
  // console.log(' The length of the array ', this.listOfDocument.length);
  // for (i = 0; i < this.listOfDocument.length; i++) {
  //   this.trackDocument.getIndexedOn(this.listOfDocument[i].documentId).subscribe((mydata) => {
  //     this.folders.indexedOn = moment(mydata).fromNow();
  //     this.folders.addedOn = moment(this.listOfDocument[i].addedOn).fromNow();
  //     this.folders.documentId = this.listOfDocument[i].documentId;
  //     this.folders.addedBy = this.listOfDocument[i].addedBy;
  //     this.folders.title = this.listOfDocument[i].title;
  //     console.log("insdie subs: ", this.folders);
  //     this.allData.push(this.folders);
  //     if(i === this.listOfDocument.length-1){
  //       console.log('Printing the folder', this.allData);
  //     }
  //   });
  //   // this.folders[i].indexedOn = moment(this.trackDocument.getIndexedOn(this.listOfDocument[i].documentId)).fromNow;

  // }
  // console.log('Printing All data', this.allData);
  // console.log(this.folders);
  // this.trackDocument.getDocument().subscribe((data: any) => {
  //   async.map(data.resultList, this.semanticFunction.bind(this), (err, res) => {
  //     console.log('resutl:: ', res);
  //     this.allData = res;
  //   } );
  // });


  myfunction(singleValue, cb) {
    // console.log('docutmentId ', singleValue);
    this.trackDocument.getIndexedOn(singleValue.documentId).subscribe((mydata) => {
      console.log('Valuable data ', mydata[0]);
      this.folders.indexedOn = moment(mydata[0]).fromNow();
      this.folders.addedOn = moment(singleValue.addedOn).fromNow();
      this.folders.documentId = singleValue.documentId;
      this.folders.addedBy = singleValue.addedBy;
      this.folders.title = singleValue.title;
      this.folders.intent = mydata[1];
      this.folders.rating = mydata[2];
      // cosnsole.log("insdie subs: ", this.folders);
      const obj = JSON.parse(JSON.stringify(this.folders));
      // this.allData.push(this.folders);
      cb(null, obj);
    });

    // this.trackDocument.getIntentOf(singleValue.documentId).subscribe((data: any) => {
    //   console.log('Got the data from semantic service', data);
    //   // let i = this.count();
    //   // let i = this.countvar;
    //   // console.log(i);
    //   // let i = 0;

    //   // console.log("Printing single value", singleValue);
    //   // console.log("Hello");
    //   // this.allData[i].rating = data[i].rating;
    //   // this.allData[i].intent = data[i].intent;
    //   // console.log(this.allData[i]);
    //   // console.log("Value of i is", i);
    //   // this.countvar++;
    //   // }
    //   data.forEach((element,index) => {
    //     console.log('in the function');
    //     this.allData[index].rating = element.rating;
    //     this.allData[index].intent = element.intent;
    //   });
    //   console.log(this.allData);
    // });
  }
  // semanticFunction(singleValue, cb) {
  //   console.log('documentId', singleValue.documentId);
  //   this.trackDocument.getIntentOf(singleValue.documentId).subscribe((mydata) => {
  //     this.folders.rating = mydata[1];
  //     this.folders.intent = mydata[0];
  //     const obj = JSON.parse(JSON.stringify(this.folders));
  //     cb(null, obj);
  //   });
  // }



  bulkUpload(): void {
    const dialogRef = this.dialog.open(BulkUploadComponent, {
      width: '500px'
    });
  }

}

