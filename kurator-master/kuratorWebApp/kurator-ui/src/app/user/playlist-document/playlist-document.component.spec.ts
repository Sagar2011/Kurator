import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaylistDocumentComponent } from './playlist-document.component';

import {CardsComponent} from '../../document/cards/cards.component';

describe('PlaylistDocumentComponent', () => {
  let component: PlaylistDocumentComponent;
  let fixture: ComponentFixture<PlaylistDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaylistDocumentComponent , CardsComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaylistDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
