import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestDashboardComponent } from './guest-dashboard.component';
import {
  MatIconModule, MatToolbarModule, MatFormFieldModule, MatCardModule,
  MatChipsModule, MatMenuModule, MatDialogModule, MatDialogRef, MatDividerModule, MatProgressSpinnerModule, MatSnackBarModule
} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CardsComponent } from 'src/app/document/cards/cards.component';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('GuestDashboardComponent', () => {
  let component: GuestDashboardComponent;
  let fixture: ComponentFixture<GuestDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GuestDashboardComponent, CardsComponent],
      imports: [MatIconModule, MatToolbarModule, FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule, NgCircleProgressModule.forRoot(), MatCardModule, MatChipsModule,
        MatMenuModule, MatDialogModule, HttpClientTestingModule, RouterTestingModule,
        MatDividerModule, MatProgressSpinnerModule, MatSnackBarModule],
      providers: [{ provide: MatDialogRef }]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
