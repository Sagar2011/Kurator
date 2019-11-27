import { Component, OnInit } from '@angular/core';
import { UserprofileService } from '../userprofile/userprofile.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PlaylistDocumentComponent } from '../playlist-document/playlist-document.component';
import { MatDialog } from '@angular/material/dialog';
import { CreatePlaylistComponent } from '../create-playlist/create-playlist.component';
import * as moment from 'moment';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {
  userProfileInfo: any = {};
  userName: string;
  playlist = [];
  createPlaylist: boolean;
  playlistForm: FormGroup;
  numberOfPlaylist: number;
  status: boolean;
  constructor(
    private userProfleService: UserprofileService,
    private formbuilder: FormBuilder,
    private dialog: MatDialog, // private dialog: MatDialogRef<PlaylistComponent>,
    private snackBar: MatSnackBar
  ) {
    this.userProfleService.getPlaylist();
  }

  ngOnInit() {
    this.userProfleService.getPlay().subscribe(res => {
      // this.playlist = res;
      // this.playlist = this.playlist.result;
      // this.status = !this.status;
      this.numberOfPlaylist = res.length;
      if (this.numberOfPlaylist > 0) {
        this.status = true;
      }
      console.log('res :::', res);
      const modifiedPlaylist = res.map(element => {
        return {
          playlistName: element.playlistName,
          description: element.description.substring(0, 7),
          createdOn: moment(element.createdOn).fromNow()
        };
      });

      this.playlist = modifiedPlaylist;
      // this.userProfleService.getPlaylist();
      // this.playlist.forEach(
      //   element => {
      //     console.log('created' + element.createdOn);
      //     console.log(
      //       'moment' + moment(element.createdOn).format('MM/DD/YYYY')
      //     );
      //     element.description = element.description.substring(0, 10);
      //     element.playlistName = element.playlistName.substring(0, 7);
      //     element.createdOn = moment(element.createdOn).fromNow();
      //   },
      //   () => {
      //     console.log('playlist after modification ::', this.playlist);
      //   }
      // );
    });
    // this.userProfleService.getPlaylist();
  }

  // getUserPlayList() {
  //   this.userProfleService.getPlaylist();
  //   console.log(this.getUserPlayList);
  // }
  openDialogCreate() {
    const dialogRef = this.dialog.open(CreatePlaylistComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
  // onCancel() {
  //   this.createPlaylist = false;
  // }

  removePlaylist(playlistName: string) {
    this.userProfleService.removePlaylist(playlistName).subscribe(response => {
      this.snackBar.open('Playlist deleted successfully', null, {
        duration: 2000
      });
      console.log('response delete ::', playlistName);
      this.userProfleService.getPlaylist();
      this.userProfleService.getPlay().subscribe(res => {
        this.numberOfPlaylist = res.length;
        console.log(this.numberOfPlaylist);
        if (this.numberOfPlaylist > 0) {
          this.status = true;
        } else {
          this.status = false;
        }
      });
    });
  }

  openPlaylist(playlistName: string) {
    this.dialog.open(PlaylistDocumentComponent, {
      data: {
        playlist: playlistName
      },
      width: '1500px',
      height: '1500px'
    });
  }
}
