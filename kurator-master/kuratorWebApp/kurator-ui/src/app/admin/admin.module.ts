import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { AdminComponent } from './admin/admin.component';
// import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminRoutingModule } from './admin-routing.module';
import { MaterialModule } from '../material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import {  WavesModule, MDBBootstrapModule } from 'angular-bootstrap-md';
import { HeatMapAllModule } from '@syncfusion/ej2-angular-heatmap';
import { IntentCardsComponent } from './intent-cards/intent-cards.component';
// import { EditIntentComponent } from './edit-intent/edit-intent.component';
import { AddIntentComponent } from './add-intent/add-intent.component';
import { AddConceptComponent } from './add-concept/add-concept.component';
import { ManageOntologyComponent } from './manage-ontology/manage-ontology.component';
import { AlertDialogComponent } from './alert-dialog/alert-dialog.component';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { TermCardsComponent } from './term-cards/term-cards.component';
import { TrackUserActivityComponent } from './track-user-activity/track-user-activity.component';
import { ChartsModule } from 'ng2-charts';

import { TrackDocumentComponent } from './track-document/track-document.component';
import { BulkUploadComponent } from './bulk-upload/bulk-upload.component';
// import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';

@NgModule({
  declarations: [IntentCardsComponent,
    AddIntentComponent, AddConceptComponent, ManageOntologyComponent,
    AlertDialogComponent, ConfirmationDialogComponent, TermCardsComponent,
    TrackDocumentComponent, TrackUserActivityComponent, BulkUploadComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FlexLayoutModule,
    WavesModule,
    HeatMapAllModule,
    MDBBootstrapModule.forRoot(),
ChartsModule,
  ],
  entryComponents: [AddConceptComponent, BulkUploadComponent, ConfirmationDialogComponent, AlertDialogComponent],
  exports: [
    AlertDialogComponent, IntentCardsComponent,
    ManageOntologyComponent, ConfirmationDialogComponent, TermCardsComponent
  ]
})
export class AdminModule { }
