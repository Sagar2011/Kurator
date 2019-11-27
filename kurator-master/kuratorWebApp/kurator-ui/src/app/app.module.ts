import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DocumentModule } from './document/document.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChatbotModule } from './chatbot/chatbot.module';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { AuthInterceptorService } from './user/auth-interceptor.service';
import { ChartsModule } from 'ng2-charts';
// import { CookieService } from 'ngx-cookie-service';
// import { AuthenticationService } from './user/authentication.service';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AdminModule } from './admin/admin.module';
// import { N } from '@angular/cdk/keycodes';


@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    PageNotFoundComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ChatbotModule,
    FormsModule,
    ChartsModule,
    ReactiveFormsModule,
    DocumentModule,
    AdminModule,
    FlexLayoutModule,
    ChartsModule,
    // DataFilterPipe

  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true },
    MaterialModule
    // CookieService,
    // AuthenticationService
  ],
  bootstrap: [AppComponent],
  exports: []
})
export class AppModule { }
