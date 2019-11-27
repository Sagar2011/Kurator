import { Component, OnInit } from '@angular/core';
import { UserprofileService } from '../userprofile/userprofile.service';
import { MatSnackBar, MatDialog, MatDialogRef } from '@angular/material';
import { TopicFollowingComponent } from '../topic-following/topic-following.component';
import { TrendingService } from 'src/app/service/trending.service';

@Component({
  selector: 'app-following-topic',
  templateUrl: './following-topic.component.html',
  styleUrls: ['./following-topic.component.css']
})
export class FollowingTopicComponent implements OnInit {

  constructor(
    private userService: UserprofileService,
    private snack: MatSnackBar,
    private dialog: MatDialogRef<TopicFollowingComponent>,
    private trending: TrendingService
  ) { }
  concepts = [];
  choosenList = [];
  status = true;
  userChoice = [];
  array = [];
  empty = false;
  closeStatus = true;
  ngOnInit() {
    this.userService.getAllTopics().subscribe((data) => {
      this.concepts = data;
    });
    this.userService.getUserFollowTopics();
    this.userService.getValueTopics().subscribe((data) => {
      this.userChoice = data;
      console.log(this.userChoice + 'users');
      if (this.userChoice.length > 0) {
        console.log('if condition');
        this.closeStatus = false;
      }
    });
  }

  follow(concept: any, i: any) {
    if (this.choosenList.indexOf(concept) === -1) {
      this.choosenList.push(concept);
      this.array.push(i);
    } else {
      this.choosenList = this.choosenList.filter(item => item !== concept);
      this.array = this.array.filter(index => index !== i);
    }
    if (this.choosenList.length >= 3) {
      this.status = false;
    } else {
      this.status = true;

    }
    console.log('da', this.choosenList);
  }

  submit() {
    this.userService.setAllTopics(this.choosenList).subscribe((res) => {
      this.snack.open('Started following', 'dismiss', {
        duration: 1500
      });
      this.trending.getTrending();
    });
    this.dialog.close();
  }
  getColors(i) {
    if (this.array.indexOf(i) === -1) {
      return 'grey';
    } else {
      return 'accent';
    }
  }

  close() {
    if (this.userChoice.length > 0) {
      this.dialog.close();
      this.trending.getTrending();
    }
  }
}
