import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchComponent } from './search/search.component';
import { DocumentViewComponent } from './document-view/document-view.component';
import { ContentNotAllowedComponent } from './content-not-allowed/content-not-allowed.component';


const routes: Routes = [
  { path: '', component: SearchComponent , pathMatch: 'full'},
  { path: 'view', component: DocumentViewComponent },
  { path: 'abusive', component: ContentNotAllowedComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentRoutingModule { }
