import { CommonModule } from '@angular/common';
import { ServerResponse } from './../../model/server-response';
import { Component } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { Status } from '../../model/status';
import { DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-create-server',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    InputTextModule,
    ButtonModule,
    RippleModule,
  ],
  templateUrl: './create-server.component.html',
  styleUrl: './create-server.component.css',
})
export class CreateServerComponent {

  ref: DynamicDialogRef | undefined;
  serverResponse: ServerResponse = {
    id: null,
    uid: '',
    name: '',
    owner: '',
    ipAddress: '',
    memory: '',
    type: '',
    icon: '',
    status: Status.SERVER_UP,
    lastCheck: new Date(),
    lastCheckTimeAgo: '',
    createdAt: new Date(),
    createdAtTimeAgo: '',
    updatedAt: new Date(),
    updatedAtTimeAgo: '',
  };

  onSubmit(){
    
  }
}
