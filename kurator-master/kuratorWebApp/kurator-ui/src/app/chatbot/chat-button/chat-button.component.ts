import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ChatDialogComponent } from '../chat-dialog/chat-dialog.component';

@Component({
  selector: 'app-chat-button',
  templateUrl: './chat-button.component.html',
  styleUrls: ['./chat-button.component.css']
})
export class ChatButtonComponent implements OnInit {

  constructor(public dialog: MatDialog) { }
  openDialog(): void {
    const dialogRef = this.dialog.open(ChatDialogComponent, {
    });

  }

  ngOnInit() {
  }

}
