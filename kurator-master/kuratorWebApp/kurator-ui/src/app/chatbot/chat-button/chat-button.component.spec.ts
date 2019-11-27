import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatIconModule, MatButtonModule, MatDialogModule, MatDialogRef } from '@angular/material';
import { ChatButtonComponent } from './chat-button.component';

describe('ChatButtonComponent', () => {
  let component: ChatButtonComponent;
  let fixture: ComponentFixture<ChatButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ChatButtonComponent],
      imports: [MatIconModule, MatButtonModule, MatDialogModule],
      providers: [{ provide: MatDialogRef }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
