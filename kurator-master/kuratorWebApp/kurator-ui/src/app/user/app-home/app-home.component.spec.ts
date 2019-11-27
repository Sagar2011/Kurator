import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChatButtonComponent } from '../../chatbot/chat-button/chat-button.component';
import { AppHomeComponent } from './app-home.component';
import { MatButtonModule, MatCardModule, MatToolbarModule, MatIconModule,
  MatDialogModule, MatIcon, MatDividerModule, MatInputModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('AppHomeComponent', () => {
  let component: AppHomeComponent;
  let fixture: ComponentFixture<AppHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppHomeComponent, ChatButtonComponent],
      imports: [MatButtonModule, MatCardModule, MatToolbarModule, MatIconModule, MatDialogModule,
      MatDividerModule, ReactiveFormsModule, MatInputModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
