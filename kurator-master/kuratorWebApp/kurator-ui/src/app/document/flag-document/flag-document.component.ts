import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { MatDialogRef, MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatSnackBar } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { startWith, map } from 'rxjs/operators';
import { MatSelectModule } from '@angular/material/select';
import { config } from '../../config';

@Component({
  selector: 'app-flag-document',
  templateUrl: './flag-document.component.html',
  styleUrls: ['./flag-document.component.css']
})
export class FlagDocumentComponent implements OnInit {
  selectedValue: string;

  reasons = config.flaggingReason;

  formData: any;
  msg: string = null;
  clickEvent = false;
  user: any;
  constructor(
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<FlagDocumentComponent>,
    // private flagDocumentService: FlagDocumentService,
    private snackBar: MatSnackBar
  ) {
    console.log('reasons ::', this.reasons);
  }

  flagdoc = this.formBuilder.group({
    reason: [''],
    comment: ['']
  });

  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit() {
    // reasons: JSON.parse(config.flaggingReason.values)

  }

  onFlag() {
    console.log('flag form value', this.flagdoc.value);
    if (this.flagdoc.value.reason != null) {
      this.snackBar.open('Document Flagged Successfully', null, {
        duration: 2000,
      });
      this.dialogRef.close();
    }
    // try {
    //   this.addDocumentService.uploadDoc(this.uploaddoc.value).subscribe(
    //     res => {
    //       if (res === true) {
    //         this.msg = 'SUCCESS';
    //         this.snackBar.open('Document Added Successfully', null, {
    //           duration: 2000,
    //         });
    //         this.onNoClick();
    //       } else {
    //         this.msg = 'the url entered is either invalid or already existing,try again';
    //         this.snackBar.open('The url entered is either invalid or already existing,try again', null, {
    //           duration: 2000,
    //         });
    //         this.onNoClick();
    //       }
    //       this.uploaddoc.reset();
    //     },
    //     (error: HttpErrorResponse) => {
    //       if (error.status === 404) {
    //         this.msg = 'Page Not Found';
    //       } else {
    //         this.msg = 'Sorry Server is Down!!!!!!!';
    //       }
    //     });
    // } catch (error) {
    //   this.msg = 'Something Went Wrong try again after sometime............';
    // }
  }
}

// import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
// import { FormBuilder, FormControl } from '@angular/forms';
// import { MatDialogRef, MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatSnackBar } from '@angular/material';
// import { HttpErrorResponse } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { COMMA, ENTER } from '@angular/cdk/keycodes';
// import { startWith, map } from 'rxjs/operators';
// import { MatSelectModule } from '@angular/material/select';
// import { config } from '../../config';
// import { FlagDocumentService } from 'src/app/service/flag-document.service';
// import { UserprofileService } from 'src/app/user/userprofile/userprofile.service';

// @Component({
//   selector: 'app-flag-document',
//   templateUrl: './flag-document.component.html',
//   styleUrls: ['./flag-document.component.css']
// })
// export class FlagDocumentComponent implements OnInit {
//   selectedValue: string;

//   reasons = config.flaggingReason;

//   formData: any;
//   msg: string = null;
//   clickEvent = false;
//   user: any;
//   constructor(
//     private formBuilder: FormBuilder,
//     public dialogRef: MatDialogRef<FlagDocumentComponent>,
//     private flagDocumentService: FlagDocumentService,
//     private userService: UserprofileService,
//     private snackBar: MatSnackBar
//   ) {
//   }

//   flagdoc = this.formBuilder.group({
//     reason: [''],
//     comment: ['']
//   });

//   onNoClick(): void {
//     this.dialogRef.close();
//   }
//   ngOnInit() {
//     // reasons: JSON.parse(config.flaggingReason.values)

//   }

//   onFlag(string documentID) {
//     console.log('reasons ::', this.flagdoc.value.reason);
//     console.log('comments ::', this.flagdoc.value.comment);
//     console.log('flag form value', this.flagdoc.value);
//     if (this.flagdoc.value.reason != null) {
//       this.snackBar.open('Document Flagged Successfully', null, {
//         duration: 2000,
//       });
//       this.dialogRef.close();
//     }
//     try {
//       this.flagDocumentService.flagDocument(this.flagdoc.value.reason, this.flagdoc.value.comment).subscribe(
//         res => {
//           if (res === true) {
//             this.snackBar.open('Document Flagged Successfully', null, {
//               duration: 2000,
//             });
//             this.onNoClick();
//           } else {
//             this.snackBar.open('The could not be flagged,try again', null, {
//               duration: 2000,
//             });
//             this.onNoClick();
//           }
//           this.flagdoc.reset();
//         },
//         (error: HttpErrorResponse) => {
//           if (error.status === 404) {
//             this.msg = 'Page Not Found';
//           } else {
//             this.msg = 'Sorry Server is Down!!!!!!!';
//           }
//         });
//     } catch (error) {
//       this.msg = 'Something Went Wrong try again after sometime............';
//     }
//   }
// }

