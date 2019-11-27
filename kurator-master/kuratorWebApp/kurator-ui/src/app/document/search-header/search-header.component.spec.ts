import { async, ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { SearchHeaderComponent } from './search-header.component';
import { FormsModule, ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgCircleProgressModule } from 'ng-circle-progress';

import {
  MatFormFieldModule, MatIconModule, MatCardModule, MatInputModule,
  MatChipsModule, MatMenuModule, MatProgressSpinnerModule, MatSnackBarModule, MatDividerModule
} from '@angular/material';
import { CardsComponent } from '../cards/cards.component';
describe('SearchHeaderComponent', () => {
  let component: SearchHeaderComponent;
  let fixture: ComponentFixture<SearchHeaderComponent>;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SearchHeaderComponent, CardsComponent],
      imports: [FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatCardModule,
        HttpClientTestingModule,
        RouterTestingModule,
        MatIconModule,
        MatChipsModule,
        MatInputModule,
        MatMenuModule,
        MatProgressSpinnerModule,
        NgCircleProgressModule,
        BrowserAnimationsModule,
        MatSnackBarModule,
        MatDividerModule]
    })
      .compileComponents();
  }));



  beforeEach(() => {
    fixture = TestBed.createComponent(SearchHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create a FormGroup comprised of FormControls', () => {
    expect(component.searchForm instanceof FormGroup).toBe(true);
  });

  it('should return true if the form control is valid', () => {
    const formControl = new FormControl('test');

    component.searchForm.controls['inputQuery '] = formControl;
    expect(formControl.valid).toBe(true);
  });
});
