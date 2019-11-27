import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CardsComponent } from '../cards/cards.component';
import {
  MatCardModule, MatProgressSpinnerModule,
  MatIconModule, MatMenuModule, MatChipsModule, MatDividerModule, MatSnackBarModule
} from '@angular/material';
import { SearchComponent } from './search.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { RouterTestingModule } from '@angular/router/testing';

describe('SearchComponent', () => {
  let component: SearchComponent;
  let fixture: ComponentFixture<SearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SearchComponent, CardsComponent],
      imports: [MatCardModule, MatProgressSpinnerModule,
        MatIconModule, HttpClientTestingModule, MatMenuModule,
        MatChipsModule, NgCircleProgressModule, MatDividerModule, RouterTestingModule, MatSnackBarModule],

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchComponent);
    component = fixture.componentInstance;
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
