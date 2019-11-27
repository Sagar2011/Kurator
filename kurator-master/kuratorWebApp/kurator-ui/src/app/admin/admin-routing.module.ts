import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
// import { AdminComponent } from './admin/admin/admin.component';
// import { ManageIntentComponent } from './manage-intent/manage-intent.component';
import { IntentCardsComponent } from './intent-cards/intent-cards.component';
// import { EditIntentComponent } from './edit-intent/edit-intent.component';
import { AddIntentComponent } from './add-intent/add-intent.component';
// import { ManageConceptComponent } from './manage-concept/manage-concept.component';
import { ManageOntologyComponent } from './manage-ontology/manage-ontology.component';
import { TermCardsComponent } from './term-cards/term-cards.component';
import { TrackUserActivityComponent } from './track-user-activity/track-user-activity.component';
import { TrackDocumentComponent} from './track-document/track-document.component';
// import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';

const routes: Routes = [
  // { path: 'admin',
  //   children: [
  //     // { path: '', component: AdminComponent },
  //     {path: 'intent', component: ManageIntentComponent},
  //     { path: ':editintent', component: EditIntentComponent }
  //   ]
  // },
  // { path: 'editintent', component: EditIntentComponent },
  { path: 'addintent', component: AddIntentComponent },
 // { path: 'intent', component: ManageIntentComponent },
  { path: 'intentcard', component: IntentCardsComponent },
 // { path: 'concept', component: ManageConceptComponent },
  { path: 'ontology', component: ManageOntologyComponent },
  { path: 'termcard', component: TermCardsComponent },
  {path: 'trackUserDetails', component: TrackUserActivityComponent},
  { path: 'document', component: TrackDocumentComponent}
 // { path: 'admindashboard', component: AdminDashboardComponent },

  // { path: '', component: AdminComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
