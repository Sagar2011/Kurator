import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProfileComponent } from './edit-profile.component';
import { MatCardModule, MatInputModule, MatFormFieldModule, MatDialogModule, MatDialogRef } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserprofileService } from '../../user/userprofile/userprofile.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('EditProfileComponent', () => {
  let component: EditProfileComponent;
  let fixture: ComponentFixture<EditProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditProfileComponent],
      imports: [MatCardModule, MatInputModule, BrowserAnimationsModule,
        FormsModule, ReactiveFormsModule, MatFormFieldModule, MatDialogModule, HttpClientTestingModule],
      providers: [{ provide: MatDialogRef }, UserprofileService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
