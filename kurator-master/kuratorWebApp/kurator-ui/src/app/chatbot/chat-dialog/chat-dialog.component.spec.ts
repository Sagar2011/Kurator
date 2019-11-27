import { async, ComponentFixture, TestBed, inject } from '@angular/core/testing';
import { OverlayContainer } from '@angular/cdk/overlay';
import {
  MatIconModule, MatInputModule, MatDialogModule, MatFormFieldModule, MatDialogRef,
  MatDialog
} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChatDialogComponent } from './chat-dialog.component';
import { ChatButtonComponent } from '../chat-button/chat-button.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('ChatDialogComponent', () => {
  let component: ChatDialogComponent;
  let fixture: ComponentFixture<ChatDialogComponent>;
  let dialog: MatDialog;
  let overlayContainer: OverlayContainer;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ChatDialogComponent, ChatButtonComponent],
      imports: [MatIconModule, BrowserAnimationsModule,
        MatInputModule, MatFormFieldModule, FormsModule, MatDialogModule, ReactiveFormsModule],
      providers: [{ provide: MatDialogRef }]
    });
    TestBed.overrideModule(BrowserAnimationsModule, {
      set: {
        entryComponents: [ChatDialogComponent]
      }
    });
    TestBed.compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatDialogComponent);
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
    const dialogRef = dialog.open(ChatDialogComponent, {
      width: '300px', height: '200px'
    });
    expect(dialogRef.componentInstance instanceof ChatDialogComponent).toBe(true);
  });
});
