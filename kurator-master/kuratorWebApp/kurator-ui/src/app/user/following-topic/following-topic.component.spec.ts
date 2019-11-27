import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FollowingTopicComponent } from './following-topic.component';
import { MatCardModule, MatSnackBarModule, MatDialogModule, MatDialogRef, MatChipsModule, MatIconModule } from '@angular/material';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AvatarModule } from 'ngx-avatar';

describe('FollowingTopicComponent', () => {
  let component: FollowingTopicComponent;
  let fixture: ComponentFixture<FollowingTopicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [FollowingTopicComponent],
      imports: [MatCardModule, HttpClientTestingModule, MatSnackBarModule, MatDialogModule,
        MatChipsModule, MatIconModule, AvatarModule],
      providers: [{ provide: MatDialogRef }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FollowingTopicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
