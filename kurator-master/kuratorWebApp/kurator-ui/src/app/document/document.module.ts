import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DocumentRoutingModule } from './document-routing.module';
import { SearchComponent } from './search/search.component';
import { CardsComponent } from './cards/cards.component';
import { SearchHeaderComponent } from './search-header/search-header.component';
import { AddDocumentComponent } from './add-document/add-document.component';
import { HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DocumentViewComponent } from './document-view/document-view.component';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { LoginComponent } from '../user/login/login.component';
import { ContentNotAllowedComponent } from './content-not-allowed/content-not-allowed.component';
import { AuthenticationService } from '../user/authentication.service';
import { FlagDocumentComponent } from './flag-document/flag-document.component';
import { AddPlaylistComponent } from './add-playlist/add-playlist.component';
import { BottomsheetComponent } from '../user/bottomsheet/bottomsheet.component';



@NgModule({

  declarations: [SearchComponent, CardsComponent, SearchHeaderComponent,
     LoginComponent, AddDocumentComponent, DocumentViewComponent,
      ContentNotAllowedComponent, FlagDocumentComponent, AddPlaylistComponent, BottomsheetComponent],

  imports: [
    CommonModule,
    DocumentRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FlexLayoutModule,
    NgCircleProgressModule.forRoot(),
  ],
  entryComponents: [AddDocumentComponent, FlagDocumentComponent, DocumentViewComponent, LoginComponent, SearchHeaderComponent,
     AddPlaylistComponent, BottomsheetComponent],
  exports: [SearchHeaderComponent, CardsComponent],
  providers: [AuthenticationService]
})
export class DocumentModule { }
