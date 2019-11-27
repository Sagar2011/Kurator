import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { EditProfileComponent } from '../edit-profile/edit-profile.component';
import { UserprofileService } from './userprofile.service';
import { TrendingService } from 'src/app/service/trending.service';

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent implements OnInit {
  following: boolean;
  topics: boolean;
  email: string;
  useravatarURL: string;
  userName: string;
  firstName: string;
  lastName: string;
  aboutUser: string;
  userDetails: any = [];
  profile: any = {};
  userImage: string;
  trendingDocList: any;
  userChoice = [];
  userFollowing = [];
  status: boolean;
  follower: boolean;
  constructor(
    private dialog: MatDialog,
    private userProfileService: UserprofileService,
    private trendingService: TrendingService,
    private userService: UserprofileService
  ) {}

  ngOnInit() {
    this.following = false;
    this.topics = true;
    this.follower = false;
    this.userProfile();
    this.trendingService.getValue().subscribe(data => {
      this.trendingDocList = data.resultList;
    });
    this.userService.getUserFollowTopics();
    // get all topics followed by user
    this.userService.getValueTopics().subscribe(data => {
      console.log('topic data ::', data);
      this.userChoice = data;
      if (this.userChoice.length > 0) {
        this.status = true;
      }
    });
  }

  onFollowing() {
    this.following = !this.following;
    this.topics = false;
    this.follower = false;
  }

  onTopicList() {
    this.topics = !this.topics;
    this.following = false;
    this.follower = false;
    // this.userService.getUserFollowTopics();
  }
  onFollower() {
    this.follower = !this.follower;
    this.topics = false;
    this.following = false;
  }

  onEdit(data) {
    const openEdit = this.dialog.open(EditProfileComponent, {
      width: '400px',
      height: '450px'
    });
    console.log('edit value', data);

    openEdit.afterClosed().subscribe(res => {
      this.userProfile();
    });
  }

  userProfile() {
    this.userProfileService.userProfile().subscribe(res => {
      console.log('user response', res);
      this.profile = res;
      console.log('profile', this.profile);
      this.userImage = this.profile.avatarURL;
      console.log(this.profile.avatarURL);
    });
  }
}
