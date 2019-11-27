import { async, ComponentFixture, TestBed, inject } from '@angular/core/testing';

import { DocumentViewComponent } from './document-view.component';
import { MatIconModule, MatToolbarModule, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { MaterialModule } from 'src/app/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OverlayContainer } from '@angular/cdk/overlay';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('DocumentViewComponent', () => {
  let component: DocumentViewComponent;
  let fixture: ComponentFixture<DocumentViewComponent>;
  let dialog: MatDialog;
  let overlayContainer: OverlayContainer;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DocumentViewComponent],
      imports: [MatIconModule, MatToolbarModule, MatDialogModule,
        HttpClientTestingModule,
        MaterialModule, BrowserAnimationsModule],
      providers: [{ provide: MatDialogRef }, { provide: MAT_DIALOG_DATA, useValue: {} }]
    });
    TestBed.overrideModule(BrowserAnimationsModule, {
      set: {
        entryComponents: [DocumentViewComponent]
      }
    });
    TestBed.compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  beforeEach(inject([MatDialog, OverlayContainer],
    (d: MatDialog, oc: OverlayContainer) => {
      dialog = d;
      overlayContainer = oc;
    })
  );
  afterEach(() => {
    overlayContainer.ngOnDestroy();
  });
  it('should open a dialog ', () => {
    const dialogRef = dialog.open(DocumentViewComponent, {
      width: '300px', height: '200px'
    });
    expect(dialogRef.componentInstance instanceof DocumentViewComponent).toBe(true);
  });
});
