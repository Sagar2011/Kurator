import {
  Component,
  OnInit,
  HostListener,
  OnChanges,
  DoCheck,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog, MatDialogRef } from '@angular/material';
import { AddDocumentComponent } from '../../document/add-document/add-document.component';
import { SearchService } from 'src/app/service/search.service';
import { TrendingService } from '../../service/trending.service';
import { FavouriteServiceService } from 'src/app/service/favourite-service.service';
import { UserprofileService } from '../userprofile/userprofile.service';
import { RecentViewService } from 'src/app/service/recent-view.service';
import { FollowingTopicComponent } from '../following-topic/following-topic.component';
import { AddDocumentService } from 'src/app/service/add-document.service';
import * as async from 'async';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  messageToCard: string;
  trendingStatus: boolean;
  exceptionMessage: string;
  docList: any = [];
  trendingDocList: any = [];
  subscription: any;
  favList: any = [];
  viewList: any = [];
  addedList = [];
  preUrl: any;
  status;
  topics: any = [];
  message = 'No Document Found';
  totalDocuments: number;
  searchTime: any;
  recentlyAdded: boolean;
  favorite: boolean;
  recentlyViewed: boolean;
  trending = true;
  user: any;
  favStatus = false;
  loaded = true;
  recentlyViewedStatus: boolean;
  viewedData: any;
  recentlyAddedStatus: boolean;
  favoriteStatus: boolean;

  constructor(
    private dialog: MatDialog,
    private service: SearchService,
    private trendingService: TrendingService,
    private fabService: FavouriteServiceService,
    private userService: UserprofileService,
    private recent: RecentViewService,
    private addService: AddDocumentService
  ) { }
  public currentWindowWidth: number;

  ngOnInit() {
    this.recentlyAdded = false;
    this.favorite = false;
    this.recentlyViewed = false;
    this.trending = true;
    this.userService.entryPointBooleanValue().subscribe(dt => {
      // console.log('Data', dt);
      if (dt) {
        this.userService.entryPoint();
        const topicAvailable = this.dialog.open(FollowingTopicComponent, {
          disableClose: true,
          width: '900px'
          // height: '300px'
        });
      }
    });
    // this.trending = true;
    this.currentWindowWidth = window.innerWidth;
    // for handling the exception in the modules
    this.service.getExceptionMessage().subscribe(message => {
      this.exceptionMessage = message;
      this.loaded = false;
    });
    this.service.getMessage().subscribe(msg => {
      this.status = msg;
    });
    this.subscription = this.service.getValue().subscribe(data => {
      // console.log(data);
      this.docList = data;
      this.totalDocuments = this.docList.length;
      this.loaded = false;
    });
    // for fav
    this.fabService.getFavouriteList().subscribe(data => {
      this.favList = data;
      if (this.favList.length > 0) {
        this.favoriteStatus = true;
      }
    });
    // trending
    this.trendingService.getValue().subscribe(data => {
      this.loaded = false;
      this.trendingDocList = data;
      if (this.trendingDocList.length > 0) {
        this.trendingStatus = true;
      }
    });
    // added
    console.log('recently added 1111111111111111');

    // this.trendingService.getValueAdd().subscribe(data => {
    //   this.addedList = data.resultList;
    //   console.log('recently added' + this.addedList);
    //   if (this.addedList.length > 0) {
    //     this.recentlyAddedStatus = true;
    //   }
    // });
  }
  openDialogUpload(): void {
    const dialogRef = this.dialog.open(AddDocumentComponent, {
      width: '500px'
    });
  }
  @HostListener('window:resize')
  onResize() {
    this.currentWindowWidth = window.innerWidth;
  }
  // tslint:disable-next-line: use-lifecycle-interface
  ngOnDestroy(): void {
    this.service.setValue([]);
    this.service.setMessage(false);
  }
  onTrending() {
    this.trending = !this.trending;
    this.recentlyAdded = false;
    this.recentlyViewed = false;
    this.favorite = false;
    this.favStatus = false;
    this.trendingService.getTrending();
  }

  async onRecentlyAdded() {
    this.recentlyAdded = !this.recentlyAdded;
    this.trending = false;
    this.recentlyViewed = false;
    this.favorite = false;
    this.favStatus = false;
    await this.trendingService.getAdded();

    await this.trendingService.getValueAdd().subscribe(data => {
      this.addedList = data.resultList;
      this.recentlyAdded = true;
      // console.log('recently added' + this.addedList.length, "----------", this.recentlyAdded);
      if (this.addedList.length > 0) {
        this.recentlyAddedStatus = true;
      }
    });
    // console.log("inside onRecentlyAdded");
  }
  onRecentlyViewed() {
    this.recentlyViewed = !this.recentlyViewed;
    this.recentlyAdded = false;
    this.trending = false;
    this.favorite = false;
    this.favStatus = false;
    this.recent.getRecent().subscribe(data => {
      if (data != null) {
        this.viewList = data;

        console.log('recent' + this.viewList);
      } else {
        console.log('do favourite list');
      }
      if (this.viewList.length > 0) {
        this.recentlyViewedStatus = true;
      }
    });
  }
  onfavorite() {
    this.recentlyViewed = false;
    this.recentlyAdded = false;
    this.trending = false;
    this.favorite = !this.favorite;
    this.favStatus = true;
    this.fabService.getFav();
  }
  receiveMessage($event: any) {
    this.messageToCard = $event;
    console.log(this.messageToCard + 'child');
  }
}
