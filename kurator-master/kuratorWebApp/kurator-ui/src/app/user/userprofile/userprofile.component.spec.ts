import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FollowingComponent } from '../following/following.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UserprofileService } from './userprofile.service';

import { UserprofileComponent } from './userprofile.component';
import { MatCardModule, MatDialogModule, MatDialogRef, MatDividerModule } from '@angular/material';
import { CardsComponent } from 'src/app/document/cards/cards.component';

describe('UserprofileComponent', () => {
  let component: UserprofileComponent;
  let fixture: ComponentFixture<UserprofileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UserprofileComponent, FollowingComponent, CardsComponent],
      imports: [MatCardModule, MatDialogModule, HttpClientTestingModule, MatDividerModule],
      providers: [{ provide: MatDialogRef }, UserprofileService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
