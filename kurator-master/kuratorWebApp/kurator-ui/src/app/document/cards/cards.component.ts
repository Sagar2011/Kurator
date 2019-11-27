import { Component, OnInit, Input, OnDestroy, DoCheck, AfterViewInit, EventEmitter, Output } from '@angular/core';
import { MatDialog, MatSnackBar, MatBottomSheet } from '@angular/material';
import { DocumentViewComponent } from '../document-view/document-view.component';
import { UserprofileService } from 'src/app/user/userprofile/userprofile.service';
import { FavouriteServiceService } from 'src/app/service/favourite-service.service';
import { RecentViewService } from 'src/app/service/recent-view.service';
import * as moment from 'moment';
import { BottomsheetComponent } from 'src/app/user/bottomsheet/bottomsheet.component';
import { AddDocumentService } from 'src/app/service/add-document.service';
import { AddPlaylistComponent } from '../add-playlist/add-playlist.component';
import { HttpErrorResponse } from '@angular/common/http';
import { timer, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { FlagDocumentComponent } from '../flag-document/flag-document.component';


@Component({
  selector: 'app-cards',
  templateUrl: './cards.component.html',
  styleUrls: ['./cards.component.css']
})
export class CardsComponent implements OnInit, AfterViewInit {
  showClap = false;
  @Input() dataDocs: any;
  @Input() favStatus: boolean;
  // date: Date;
  constructor(
    private service: AddDocumentService,
    private dialog: MatDialog,
    private userService: UserprofileService,
    private fav: FavouriteServiceService,
    private snack: MatSnackBar,
    private recent: RecentViewService,
    private bottomSheet: MatBottomSheet
  ) { }
  intentrating: number;
  title: string;
  user: any;
  time: any;
  statusPipeline = true;
  id: any;
  array = 'firewall';
  timer: any;
  subscription: Subscription;
  clap: any;
  follow = [];
  @Output() messageEvent = new EventEmitter<string>();
  ngOnInit() {
    this.service.getClaps(this.dataDocs.documentId).subscribe((claps) => {
      this.clap = claps;
    });
    this.time = moment(this.dataDocs.addedOn).fromNow();
    if (this.dataDocs.confidencerating !== undefined) {
      this.service.getClaps(this.dataDocs.documentId).subscribe((claps) => {
        this.clap = claps;
      });
      this.confidence();
    } else {
      this.subscription = timer(0, 2000).pipe(switchMap(() =>
        this.service.addStatusCall(this.dataDocs.documentId))).subscribe(data => {
          if (data === 'INDEXED' || data === null || data === undefined) {
            this.statusPipeline = false;
            this.array = data;
            this.service.getDocument(this.dataDocs.documentId).subscribe((response) => {
              this.dataDocs = response;
              this.confidence();
              this.service.getClaps(this.dataDocs.documentId).subscribe((claps) => {
                this.clap = claps;
              });
              this.sendMessage('running');
              this.subscription.unsubscribe();
            });
          } else {
            this.statusPipeline = true;
            this.array = data;
            this.sendMessage('completed');
          }
        }, (error: HttpErrorResponse) => {
          console.log('exception' + error.message);
        });
    }
    this.dataDocs.description = this.dataDocs.description.substring(0, 175);
  }

  documentView(
    docId: string,
    name: string,
    url: string,
    adddedBy: string,
    confidenceRating: any[]
  ) {
    this.addRecentView(docId);
    this.dialog.open(DocumentViewComponent, {
      data: {
        documentId: docId,
        title: name,
        pageurl: url,
        addname: adddedBy,
        rate: confidenceRating
      },
      width: '2000px'
    }).afterClosed().subscribe((claps) => {
      if (claps && claps.action === 1) {
        this.clap = claps.data;
      }
    });
  }

  playlist(docId: any) {
    this.dialog.open(AddPlaylistComponent, {

      data: {
        documentId: docId,
      },
      width: '600px',
      height: '500px'
    });
    console.log('docId', docId);
  }

  addFavourite(documentId: string) {
    console.log(documentId + ': documentId');
    if (!this.favStatus) {
      this.fav.addFav(documentId).subscribe(response => {
        this.snack.open('Added to your favourites', 'dismiss');
        this.favStatus = !this.favStatus;
        this.sendMessage('favourite');

      });
    } else {
      this.fav
        .deleteFav(documentId)
        .subscribe(response => {
          this.snack.open('removed from your favourites', 'dismiss');
          this.favStatus = !this.favStatus;
          this.sendMessage('unfavourites');
        });
    }
  }

  addRecentView(documentId: string) {
    this.recent
      .addRecent(documentId)
      .subscribe(response => {
        this.sendMessage('added');
      });
  }

  followTopic(topic: any) {
    this.follow.push(topic);
    this.userService.setAllTopics(this.follow).subscribe((res) => {
      this.snack.open('Started following', 'dismiss', {
        duration: 1500
      });
    });
  }
  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy() {
    if (this.id) {
      clearInterval(this.id);
    }
  }
  ngAfterViewInit() { }
  openBottomSheet(addedBy: any) {
    this.bottomSheet.open(BottomsheetComponent, {
      data: { username: addedBy }
    });
  }

  confidence() {
    this.dataDocs.confidencerating.forEach(element => {
      this.intentrating = element.rating * 20;
      this.title = element.intent;
    });
    this.statusPipeline = false;
  }
  sendMessage(data: any) {
    this.messageEvent.emit(data);
  }

  flagDocument() {
    const dialogRef = this.dialog.open(FlagDocumentComponent, {
      width: '500px'
    });
  }
}
