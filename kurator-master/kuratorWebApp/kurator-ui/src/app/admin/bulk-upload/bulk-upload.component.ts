import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MatSnackBar } from '@angular/material';
import { AddDocumentComponent } from 'src/app/document/add-document/add-document.component';
import { AddDocumentService } from 'src/app/service/add-document.service';
import { UserprofileService } from 'src/app/user/userprofile/userprofile.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material';
// import { Papa } from 'ngx-papaparse';
import { toArray } from 'rxjs/operators';


@Component({
  selector: 'app-bulk-upload',
  templateUrl: './bulk-upload.component.html',
  styleUrls: ['./bulk-upload.component.css']
})
export class BulkUploadComponent implements OnInit {

  msg: string = null;
  fileContent: any;
  fileReaded: any;
  validity = false;
  spinnerStatus = false;
  constructor(
    public dialogRef: MatDialogRef<BulkUploadComponent>,
    private addDocumentService: AddDocumentService,
    private snackBar: MatSnackBar,
    private userService: UserprofileService
    // private papa: Papa
  ) { }

  ngOnInit() {
  }

  public onChange(fileList: FileList): void {
    const file = fileList[0];
    const fileReader: FileReader = new FileReader();
    const self = this;
    // const arr: urlList=[];
    fileReader.onloadend = (x) => {
      self.fileContent = fileReader.result;
      console.log('file content: ', self.fileContent);
      console.log('type', typeof (self.fileContent));
      // console.log('second element', self.fileContent[1][1]);
    };
    fileReader.readAsText(file);

    // file format check..........

    // getFileDetails (event) {
    // tslint:disable-next-line: prefer-for-of
    for (let p = 0; p < fileList.length; p++) {
      const name = fileList[p].name;
      const type = fileList[p].type;
      const size = fileList[p].size;
      // const modifiedDate = event.target.files[i].lastModifiedDate;

      console.log('Name: ' + name + '\n"' +
        'Type: ' + type + '\n' +
        'Last-Modified-Date: ' + 'none' + '\n' +
        'Size: ' + Math.round(size / 1024) + ' KB');

      if (type === 'text/csv') {
        console.log('csv file');

        // csv header check.............

        // csv2Array(fileInput: any){
        const fileInput = fileList;
        // read file from input
        this.fileReaded = fileList[0];

        const reader: FileReader = new FileReader();
        reader.readAsText(this.fileReaded);

        reader.onload = (e) => {
          // const csv: string = reader.result;
          const csv: string = this.fileContent;
          const allTextLines = csv.split(/\r|\n|\r/);
          const headers = allTextLines[0].split(',');
          const lines = [];

          // tslint:disable-next-line: prefer-for-of
          for (let i = 0; i < allTextLines.length; i++) {
            // split content based on comma
            const data = allTextLines[i].split(',');
            if (data.length === headers.length) {
              const tarr = [];
              for (let j = 0; j < headers.length; j++) {
                tarr.push(data[j]);
              }

              // log each row to see output
              console.log(tarr);
              lines.push(tarr);
              if (tarr[0] === 'url') {
                console.log('its url in the header....');
                this.validity = true;
                // for (let k = 1; k < tarr.length; k++) {
                //   angular.module('urlExample', [])
                //     .controller('ExampleController', ['$scope', function ($scope) {
                //       $scope.url = {
                //         text: tarr[k]
                //       };
                //     }]);
                //   if (urlExists(tarr[k])) {
                //     console.log('yaaaaaaayyyyyy');
                //   }
                //   else {
                //     console.log('nayyyyyyyyyyyyy');
                //   }
                // }
              }
              // else {
              //   console.log('invalid header....');
              // }
            }
          }
          // all rows in the csv file
          console.log('lines', lines);
        };
        // this.snackBar.open('Successfull', null, {
        //   duration: 2000,
        // });
        // this.onNoClick();
      } else {
        console.log('not csv');
        // this.snackBar.open('Please enter a valid csv file', null, {
        //   duration: 2000,
        // });
        // this.onNoClick();
        this.validity = false;
      }
    }


    //   this.papa.parse(self.fileContent, {
    //     complete: (result) => {
    //       console.log('Parsed: ', result);
    //     }
    //   });
    //   var allKeyPresent = false; // Flag

    //   this.papa.parse(file, {
    //     header: true,
    //     skipEmptyLines: true,
    //     step: function (row, parser) {
    //       if (!allKeyPresent) { //Only chek if flag is not set, i.e, for the first time
    //         parser.pause(); // pause the parser
    //         var first_row_data = row.data[0];
    //         console.log('this row data: ', first_row_data);
    //         // Now check object keys, if it match
    //         if (('Extension' in first_row_data) && ('url' in first_row_data)) {
    //           //every required key is present
    //           allKeyPresent = true;
    //           console.log('valid header..: ', allKeyPresent);

    //           // Do your data processing here

    //           parser.resume();
    //         } else {
    //           //some key is missing, abort parsing
    //           parser.abort();
    //         }

    //       } else { // we already match the header, all required key is present

    //         // Do the Data processing here
    //         console.log('we are in data processing');

    //       }

    //     }
    //   });
  }

  uploadurls() {
    console.log('upload form value', this.fileContent);
    try {
      this.spinnerStatus = true;
      if (this.validity === true) {
        this.msg = 'SUCCESS';
        this.spinnerStatus = false;
        this.snackBar.open('File Upload Successfull', null, {
          duration: 2000,
        });
        this.onNoClick();
        this.addDocumentService.bulkUpload(this.fileContent).subscribe(
          res => {
            if (res === true) {
              // this.msg = 'SUCCESS';
              // this.spinnerStatus = false;
              // this.snackBar.open('File Upload Successfull', null, {
              //   duration: 2000,
              // });
              // this.onNoClick();
            } else {
              this.msg = 'There was some error, please try again';
              this.snackBar.open('There was some error, please try again', null, {
                duration: 2000,
              });
              this.onNoClick();
            }
            // this.bulkupload.reset();
          },
          (error: HttpErrorResponse) => {
            if (error.status === 404) {
              this.msg = 'Page Not Found';
            } else {
              this.msg = 'Sorry Server is Down!!!!!!!';
            }
          });
      } else {
        this.snackBar.open('Please enter a valid csv file', null, {
          duration: 2000,
        });
        this.onNoClick();
      }
    } catch (error) {
      this.msg = 'Something Went Wrong try again after sometime............';
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  // urlExists(url) {
  //   return fetch(url, { mode: "no-cors" })
  //     .then(res => true)
  //     .catch(err => false)
  // }

}
