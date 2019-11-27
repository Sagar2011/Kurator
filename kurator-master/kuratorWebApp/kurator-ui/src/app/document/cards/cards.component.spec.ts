import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CardsComponent } from './cards.component';
import {
  MatCardModule, MatProgressSpinnerModule,
  MatIconModule, MatDialogRef, MatMenuModule, MatChipsModule, MatDialogModule, MatSnackBarModule, MatDividerModule, MatBottomSheetModule
} from '@angular/material';
import { NgCircleProgressModule } from 'ng-circle-progress';

describe('CardsComponent', () => {
  let component: CardsComponent;
  let fixture: ComponentFixture<CardsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CardsComponent],
      imports: [HttpClientTestingModule, MatCardModule,
        MatDialogModule, MatProgressSpinnerModule,
        MatChipsModule, MatIconModule, MatMenuModule, NgCircleProgressModule.forRoot(),
        MatSnackBarModule, MatDividerModule, MatBottomSheetModule],
      providers: [{ provide: MatDialogRef }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardsComponent);
    component = fixture.componentInstance;
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
