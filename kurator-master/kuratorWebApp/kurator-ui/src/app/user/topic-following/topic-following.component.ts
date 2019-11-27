import { Component, OnInit, Input } from '@angular/core';
import { UserprofileService } from '../userprofile/userprofile.service';

@Component({
  selector: 'app-topic-following',
  templateUrl: './topic-following.component.html',
  styleUrls: ['./topic-following.component.css']
})
export class TopicFollowingComponent implements OnInit {
  @Input() concept: any;
  constructor(private userService: UserprofileService) { }

  ngOnInit() {
  }

  unfollowTopic(conceptName: any) {
    console.log('unfollow');
    this.userService.deleteUserFollowTopic(conceptName).subscribe();
  }

}
