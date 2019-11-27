import { Component, OnInit, Inject } from '@angular/core';
import { MAT_BOTTOM_SHEET_DATA, MatSnackBarModule, MatSnackBar, MatBottomSheet } from '@angular/material';
import { UserprofileService } from '../userprofile/userprofile.service';

@Component({
  selector: 'app-bottomsheet',
  templateUrl: './bottomsheet.component.html',
  styleUrls: ['./bottomsheet.component.css']
})
export class BottomsheetComponent implements OnInit {


  userDetail: any = {};
  userName: string;
  followerData: any = [];
  following: any = [];
  imageUrl: string;
  constructor(
    private userService: UserprofileService,
    @Inject(MAT_BOTTOM_SHEET_DATA) public data: any,
    private snack: MatSnackBar,
    private sheet: MatBottomSheet) { }

  ngOnInit() {
  }

  getFollowingUser() {
    // fetching username
      this.userService.followingUser(this.data.username).subscribe(response => {
        this.sheet.dismiss();
        this.snack.open('Started following', 'dismiss');
      });
  }



  onUnFollowUser(followerName: string) {
    // unfollowing a user
    this.userService.unFollowUser(followerName).subscribe(response => {
    });

    this.getFollowingUser();
  }



}
