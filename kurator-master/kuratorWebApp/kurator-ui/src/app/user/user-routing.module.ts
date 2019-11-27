import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserprofileComponent } from './userprofile/userprofile.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { AppHomeComponent } from './app-home/app-home.component';

// import { AdminComponent } from '../admin/admin/admin.component';
import { PlaylistComponent } from './playlist/playlist.component';
import { ManageCurationComponent } from './manage-curation/manage-curation.component';
import { FollowingTopicComponent } from './following-topic/following-topic.component';

const routes: Routes = [
  { path: 'profile', component: UserprofileComponent },
  { path: 'dashboard', component: UserDashboardComponent },
  { path: '', component: AppHomeComponent, pathMatch: 'full' },
 //  { path: 'admin', component: AdminComponent },
  { path: 'playlist', component: PlaylistComponent },
  { path: 'followtopic', component: FollowingTopicComponent },
  { path: 'curation', component: ManageCurationComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
