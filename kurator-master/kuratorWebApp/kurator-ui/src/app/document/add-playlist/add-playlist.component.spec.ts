import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPlaylistComponent } from './add-playlist.component';

import {MatToolbarModule, MatIconModule, MatListItem, MatListModule} from '@angular/material';

describe('AddPlaylistComponent', () => {
  let component: AddPlaylistComponent;
  let fixture: ComponentFixture<AddPlaylistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddPlaylistComponent ],
      imports: [ MatToolbarModule, MatIconModule, MatListModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPlaylistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
