import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentNotAllowedComponent } from './content-not-allowed.component';

describe('ContentNotAllowedComponent', () => {
  let component: ContentNotAllowedComponent;
  let fixture: ComponentFixture<ContentNotAllowedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContentNotAllowedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentNotAllowedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
