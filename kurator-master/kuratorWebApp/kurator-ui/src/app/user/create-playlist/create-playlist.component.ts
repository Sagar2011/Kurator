import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { UserprofileService } from '../userprofile/userprofile.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-create-playlist',
  templateUrl: './create-playlist.component.html',
  styleUrls: ['./create-playlist.component.css']
})
export class CreatePlaylistComponent implements OnInit {
  playlistForm = this.formbuilder.group({
    playlistName: new FormControl(''),
    description: new FormControl('')
  });

  constructor(
    private userProfleService: UserprofileService,
    public dialogRef: MatDialogRef<CreatePlaylistComponent>,
    private formbuilder: FormBuilder,
    private snackBar: MatSnackBar
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {}

  onCreate() {
    this.userProfleService
      .createPlaylist(this.playlistForm.value)
      .subscribe(response => {
        this.snackBar.open('Playlist created successfully', null, {
          duration: 2000
        });
        this.onNoClick();
      });
  }

  // getUserPlayList() {
  //   this.userProfleService.userProfile().subscribe(resposne => {
  //     this.userProfileInfo = resposne;
  //     this.userName = this.userProfileInfo.userName;

  //     this.userProfleService.getPlaylist();
  //   });
  // }
}
