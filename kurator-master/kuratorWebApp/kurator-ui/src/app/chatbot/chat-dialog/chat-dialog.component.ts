import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ChatService, Message } from 'src/app/service/chat.service';
import { MatDialogRef } from '@angular/material';
import { scan } from 'rxjs/operators';

@Component({
  selector: 'app-chat-dialog',
  templateUrl: './chat-dialog.component.html',
  styleUrls: ['./chat-dialog.component.css']
})
export class ChatDialogComponent implements OnInit {

  messages: Observable<Message[]>;
  formValue: string;
  container: HTMLElement;
  constructor(private chat: ChatService, private dialogRef: MatDialogRef<ChatDialogComponent>) {
  }

  ngOnInit() {
    // appends to array after each new message is added to feedSource
    this.messages = this.chat.conversation.asObservable().pipe(
      scan((acc, val) => acc.concat(val)));

    // this.dialogRef.updatePosition({ left: '50px' , top: '70px'});
  }
  sendMessage() {
    this.chat.converse(this.formValue);
    this.formValue = '';
  }
  close() {
    this.dialogRef.close();

  }

}
