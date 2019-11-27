import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { UserprofileService } from '../userprofile/userprofile.service';

@Component({
  selector: 'app-playlist-document',
  templateUrl: './playlist-document.component.html',
  styleUrls: ['./playlist-document.component.css']
})
export class PlaylistDocumentComponent implements OnInit {

  userProfileInfo: any = {};
  userName: string;
  playlist: any = [];
  playlistName: string;
  createPlaylist: boolean;
  favStatus = false;
  docList: any = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private userProfleService: UserprofileService,
    private dialog: MatDialogRef<PlaylistDocumentComponent>
  ) {}

  ngOnInit() {
    this.openPlaylistDocument(this.data.playlist);
  }

  openPlaylistDocument(playlistName) {
    this.userProfleService.openUserPlaylist(playlistName).subscribe(response => {
            this.docList = response;
            console.log('reponse of open playlist' , response);
          });
  }

}
