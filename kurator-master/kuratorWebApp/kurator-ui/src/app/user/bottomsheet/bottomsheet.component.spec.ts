import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BottomsheetComponent } from './bottomsheet.component';
import { MatCardModule, MatBottomSheetModule, MatBottomSheetRef, MAT_BOTTOM_SHEET_DATA, MatSnackBarModule } from '@angular/material';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('BottomsheetComponent', () => {
  let component: BottomsheetComponent;
  let fixture: ComponentFixture<BottomsheetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BottomsheetComponent],
      imports: [MatCardModule, HttpClientTestingModule, MatBottomSheetModule, MatSnackBarModule],
      providers: [{ provide: MAT_BOTTOM_SHEET_DATA, useValue: {} }]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BottomsheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
