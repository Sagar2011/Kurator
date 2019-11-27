import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { UserprofileService } from 'src/app/user/userprofile/userprofile.service';

@Component({
  selector: 'app-add-playlist',
  templateUrl: './add-playlist.component.html',
  styleUrls: ['./add-playlist.component.css']
})
export class AddPlaylistComponent implements OnInit {
  userProfileInfo: any = {};
  userName: string;
  playlist: any = [];
  playlistName: string;
  createPlaylist: boolean;
  addPlaylist = false;
  playlist1: any = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private userProfleService: UserprofileService,
    private dialogRef: MatDialogRef<AddPlaylistComponent>,
    private snackBar: MatSnackBar
  ) {} // private formbuilder: FormBuilder,
  // private dialog: MatDialogRef<PlaylistComponent>,

  ngOnInit() {
    this.userProfleService.getPlaylist();
    // this.createPlaylist = false;

    // this.playlistForm = this.formbuilder.group({
    //   playlistName: [''],
    //   // description: ['']
    // });

    this.userProfleService.getPlay().subscribe(res => {
      console.log('getting playlis response', res);

      this.playlist = res;
    });
  }

  addToPlaylist() {
    this.addPlaylist = !this.addPlaylist;
    console.log(this.addPlaylist);
    this.userProfleService
      .addDocumentToPlaylist(this.playlist1, this.data.documentId)
      .subscribe(response => {
        console.log('success');
        this.snackBar.open(
          'Document Added To The Playlists Successfully',
          null,
          {
            duration: 2000
          }
        );
        this.onNoClick();
      });
  }

  // onCancel() {
  //   this.createPlaylist = false;
  // }

  onNoClick(): void {
    this.dialogRef.close();
  }

  playlistArray(playlistName: any) {
    this.playlist1.push(playlistName);
  }
}
