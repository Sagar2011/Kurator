import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { UserprofileService } from '../userprofile/userprofile.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  editProfile =  this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: [''],
    aboutUser: ['']
  });
  userDetails: any = {};

  constructor(
    private dialogRef: MatDialogRef<EditProfileComponent>,
    // @Inject(MAT_DIALOG_DATA)public data,
    private formBuilder: FormBuilder,
    private userprofileService: UserprofileService
  ) { }

  ngOnInit() {
    // console.log('data',this.data);
    this.userprofileService.userProfile().subscribe(res => {
      this.userDetails = res;
      // console.log('this.userDetails', this.userDetails.firstName);
      // console.log('this.userDetails', this.userDetails.lastName);
      this.editProfile = this.formBuilder.group({
        firstName: [this.userDetails.firstName, Validators.required],
        lastName: [this.userDetails.lastName, Validators.required],
        email: [this.userDetails.email],
        aboutUser: [this.userDetails.aboutUser]
      });
    });
  }

  onCancel() {
    this.dialogRef.close();
  }

  onSubmit() {
    // console.log('edit profile value', this.editProfile.value);
    this.userprofileService.updateProfile(this.editProfile.value).subscribe(res => {
      // console.log('edit prpfile value', res);
    });

    this.userprofileService.userProfile();
    this.dialogRef.close();
  }
}
// updateProfileUrl:'/users/updateprofile',
