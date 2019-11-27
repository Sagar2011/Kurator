import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

export const routes: Routes = [
  {
    path: 'search',
    loadChildren: () =>
      import('./document/document.module').then(
        document => document.DocumentModule
      )
  },
  {
    path: 'admin',
    loadChildren: () =>
      import('./admin/admin.module').then(
        admin => admin.AdminModule
      )
  },
  {
    path: '',
    loadChildren: () =>
      import('./user/user.module').then(userModule => userModule.UserModule),
  },
  { path: '**', component: PageNotFoundComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule { }
