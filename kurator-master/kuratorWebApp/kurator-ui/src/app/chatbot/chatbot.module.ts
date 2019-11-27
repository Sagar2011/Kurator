import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatDialogComponent } from './chat-dialog/chat-dialog.component';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../service/chat.service';
import { ChatButtonComponent } from './chat-button/chat-button.component';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule} from '@angular/forms';



@NgModule({
  declarations: [ChatDialogComponent, ChatButtonComponent],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  exports: [ChatDialogComponent, ChatButtonComponent],
  providers: [ChatService],
  entryComponents: [ChatDialogComponent]

})
export class ChatbotModule { }
