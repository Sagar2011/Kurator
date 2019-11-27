import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule, MatSnackBarModule } from '@angular/material';

import { FollowingComponent } from './following.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UserprofileService } from '../../user/userprofile/userprofile.service';

describe('FollowingComponent', () => {
  let component: FollowingComponent;
  let fixture: ComponentFixture<FollowingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [FollowingComponent],
      imports: [MatCardModule, HttpClientTestingModule, MatSnackBarModule],
      providers: [UserprofileService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FollowingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
