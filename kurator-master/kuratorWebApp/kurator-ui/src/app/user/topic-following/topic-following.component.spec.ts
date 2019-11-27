import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicFollowingComponent } from './topic-following.component';
import { MatCardModule } from '@angular/material';
import { AvatarModule } from 'ngx-avatar';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('TopicFollowingComponent', () => {
  let component: TopicFollowingComponent;
  let fixture: ComponentFixture<TopicFollowingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TopicFollowingComponent],
      imports: [MatCardModule, AvatarModule, HttpClientTestingModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopicFollowingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
