import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {
  MatSidenavModule, MatIconModule, MatDialogModule, MatCardModule,
  MatChipsModule, MatMenuModule, MatCheckboxModule, MatToolbarModule, MatDividerModule, MatProgressSpinnerModule
} from '@angular/material';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ChatButtonComponent } from '../../chatbot/chat-button/chat-button.component';
import { UserDashboardComponent } from './user-dashboard.component';
import { CardsComponent } from 'src/app/document/cards/cards.component';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('UserDashboardComponent', () => {
  let component: UserDashboardComponent;
  let fixture: ComponentFixture<UserDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UserDashboardComponent, ChatButtonComponent, CardsComponent],
      imports: [MatSidenavModule, MatIconModule, MatDialogModule, HttpClientTestingModule,
        NgCircleProgressModule.forRoot(), MatCardModule, MatChipsModule, MatMenuModule,
        BrowserAnimationsModule, FormsModule, ReactiveFormsModule,
        MatCheckboxModule, MatToolbarModule, MatDividerModule, MatProgressSpinnerModule],
    });
    TestBed.overrideModule(BrowserAnimationsModule, {
      set: {
        entryComponents: []
      }
    });
    TestBed.compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
