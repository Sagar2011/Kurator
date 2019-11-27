import { NgModule } from '@angular/core';
import { CommonModule, LocationStrategy, PathLocationStrategy, TitleCasePipe } from '@angular/common';
import { MaterialModule } from '../material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DocumentModule } from '../document/document.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { AvatarModule } from 'ngx-avatar';

import { UserRoutingModule } from './user-routing.module';
import { UserprofileComponent } from './userprofile/userprofile.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { AppHomeComponent } from './app-home/app-home.component';
import { HttpClientModule } from '@angular/common/http';
import { FollowingComponent } from './following/following.component';
import { ChatbotModule } from '../chatbot/chatbot.module';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ChartsModule, WavesModule } from 'angular-bootstrap-md';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { AuthenticationService } from './authentication.service';
import { GuestDashboardComponent } from './guest-dashboard/guest-dashboard.component';


// import { MatVideoModule } from 'mat-video';
import { HeatMapAllModule } from '@syncfusion/ej2-angular-heatmap';
import { PlaylistComponent } from './playlist/playlist.component';
import { TopicFollowingComponent } from './topic-following/topic-following.component';
import { ManageCurationComponent } from './manage-curation/manage-curation.component';
import { BottomsheetComponent } from './bottomsheet/bottomsheet.component';
import { PlaylistDocumentComponent } from './playlist-document/playlist-document.component';
import { FollowingTopicComponent } from './following-topic/following-topic.component';
import { CreatePlaylistComponent } from './create-playlist/create-playlist.component';

@NgModule({
  declarations: [
    UserprofileComponent,
    UserDashboardComponent,
    AppHomeComponent,
    FollowingComponent,
    EditProfileComponent,
    GuestDashboardComponent,
    PlaylistComponent,
    TopicFollowingComponent,
    ManageCurationComponent,
    PlaylistDocumentComponent,
    FollowingTopicComponent,
    CreatePlaylistComponent],

  imports: [
    AvatarModule,
    CommonModule,
    UserRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FlexLayoutModule,
    ChatbotModule,
    DocumentModule,
    ChartsModule,
    WavesModule,
    HeatMapAllModule,
    MDBBootstrapModule.forRoot()
    // MatVideoModule
  ],
  entryComponents: [EditProfileComponent, GuestDashboardComponent,
    PlaylistDocumentComponent, FollowingTopicComponent, CreatePlaylistComponent],
  exports: [FollowingComponent, AppHomeComponent, PlaylistComponent
  ],

  providers: [AuthenticationService]
})
export class UserModule { }
