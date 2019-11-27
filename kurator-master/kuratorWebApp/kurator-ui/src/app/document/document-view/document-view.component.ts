import { Component, OnInit, Inject, PipeTransform, Pipe } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar, MatDialog } from '@angular/material';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { UserprofileService } from 'src/app/user/userprofile/userprofile.service';
import { FavouriteServiceService } from 'src/app/service/favourite-service.service';
import { FlagDocumentComponent } from '../flag-document/flag-document.component';
import { AddDocumentService } from 'src/app/service/add-document.service';


@Component({
  selector: 'app-document-view',
  templateUrl: './document-view.component.html',
  styleUrls: ['./document-view.component.css']
})
export class DocumentViewComponent implements OnInit {
  template: SafeResourceUrl;
  showClap = false;
  user: any;
  claps: any;
  favStatus: boolean;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public sanitizer: DomSanitizer,
    private dialog: MatDialogRef<DocumentViewComponent>,
    private dialog1: MatDialog,
    private fav: FavouriteServiceService,
    private snack: MatSnackBar,
    private service: AddDocumentService) {
  }

  ngOnInit() {
    this.template = this.sanitizer.bypassSecurityTrustResourceUrl(this.data.pageurl);
    console.log(this.data.rate);
  }

  close() {
    this.service.getClaps(this.data.documentId).subscribe((clap) => {
      this.dialog.close({ action: 1, data: clap });
    });
  }
  liked() {
    console.log('liked');
    this.service.addClaps(this.data.documentId).subscribe((response) => {
      this.showClap = true;
      this.snack.open('+1 Clapped', 'dismiss', { duration: 1500 });
    });
  }
  addFavourite(documentId: string) {
    console.log(documentId + ': documentId');
    if (!this.favStatus) {
      this.fav.addFav(documentId).subscribe(response => {
        this.snack.open('Added to your favourites', 'dismiss');
        this.favStatus = !this.favStatus;

      });
    } else {
      this.fav
        .deleteFav(documentId)
        .subscribe(response => {
          this.snack.open('removed from your favourites', 'dismiss');
          this.favStatus = !this.favStatus;
        });
    }
  }

  flagDocument() {
    const dialogRef = this.dialog1.open(FlagDocumentComponent, {
      width: '500px'
    });
  }
}
