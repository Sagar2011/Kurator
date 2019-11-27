import { async, ComponentFixture, TestBed, inject } from '@angular/core/testing';
import { AddDocumentComponent } from './add-document.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  MatDialogModule, MatFormFieldModule, MatInputModule, MatIconModule, MatAutocompleteModule,
  MatSlideToggleModule, MatButtonModule, MatDialogRef, MatDialog, MatChipsModule
} from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OverlayContainer } from '@angular/cdk/overlay';

describe('AddDocumentComponent', () => {
  let component: AddDocumentComponent;
  let fixture: ComponentFixture<AddDocumentComponent>;
  let dialog: MatDialog;
  let overlayContainer: OverlayContainer;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddDocumentComponent],
      imports: [MatDialogModule,
        MatFormFieldModule,
        MatSlideToggleModule,
        MatButtonModule,
        HttpClientTestingModule,
        FormsModule, MatIconModule, MatAutocompleteModule,
        MatInputModule, MatChipsModule,
        BrowserAnimationsModule,
        ReactiveFormsModule],
      providers: [{ provide: MatDialogRef }]
    });
    TestBed.overrideModule(BrowserAnimationsModule, {
      set: {
        entryComponents: [AddDocumentComponent]
      }
    });
    TestBed.compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
  beforeEach(inject([MatDialog, OverlayContainer],
    (d: MatDialog, oc: OverlayContainer) => {
      dialog = d;
      overlayContainer = oc;
    })
  );
  afterEach(() => {
    overlayContainer.ngOnDestroy();
  });
  // it('should open a dialog ', () => {
  //   const dialogRef = dialog.open(AddDocumentComponent, {
  //     width: '300px', height: '200px'
  //   });
  //   expect(dialogRef.componentInstance instanceof AddDocumentComponent).toBe(true);
  // });
});
