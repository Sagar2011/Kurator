import { Component, OnInit } from '@angular/core';
import { UserprofileService } from '../userprofile/userprofile.service';
import { MatSnackBar } from '@angular/material';
import { duration } from 'moment';

@Component({
  selector: 'app-following',
  templateUrl: './following.component.html',
  styleUrls: ['./following.component.css']
})
export class FollowingComponent implements OnInit {

  userDetail: any = {};
  userName: string;
  followerData: any = [];
  following: any = [];
  imageUrl: string;
  userFollowing = [];
  constructor(private userService: UserprofileService, private snack: MatSnackBar) { }

  ngOnInit() {
    this.userService.getfollowingUser();
    // for following users
    this.userService.getvalueFollowUser().subscribe((data) => {
      this.userFollowing = data;
    });
  }

  onUnFollowUser(followerName: string) {
    // unfollowing a user
    this.userService.unFollowUser(followerName).subscribe(response => {
      this.snack.open('Unfollowed', 'dismiss', { duration: 3000 });
    });
  }



}
