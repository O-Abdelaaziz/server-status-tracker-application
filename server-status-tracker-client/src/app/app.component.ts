import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ServerService } from './service/server.service';
import { CommonModule } from '@angular/common';
import { TopHeaderComponent } from './layout/top-header/top-header.component';
import { MainHeaderComponent } from './layout/main-header/main-header.component';
import { MessagesModule } from 'primeng/messages';
import { ConfirmationService, MessageService, Message } from 'primeng/api';
import { TabViewModule } from 'primeng/tabview';
import { TableModule } from 'primeng/table';
import { ServerResponse } from './model/server-response';
import { BadgeModule } from 'primeng/badge';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TooltipModule } from 'primeng/tooltip';
import { StatsComponent } from './components/stats/stats.component';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
} from '@angular/forms';
import { FormBuilder, Validators } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { FormsModule, NgModel } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { RippleModule } from 'primeng/ripple';
import { Status } from './model/status';
import { ServerRequest } from './model/server-request';
@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  providers: [ConfirmationService, MessageService],
  imports: [
    RouterOutlet,
    CommonModule,
    TopHeaderComponent,
    MainHeaderComponent,
    MessagesModule,
    TabViewModule,
    TableModule,
    BadgeModule,
    ButtonModule,
    ConfirmDialogModule,
    ToastModule,
    TooltipModule,
    StatsComponent,
    ReactiveFormsModule,
    DialogModule,
    FormsModule,
    InputTextModule,
    RippleModule,
  ],
})
export class AppComponent implements OnInit {
  public server: ServerRequest = new ServerRequest();
  public servers: ServerResponse[] = [];
  public serverFormGroup: FormGroup;
  public isFromSubmitted = false;

  public allServers: number = 0;
  public allServersUp: number = 0;
  public allServersDown: number = 0;

  public messages: Message[] | undefined;
  public isDialogVisible: boolean = false;
  public isLoading: boolean = false;

  private serverService = inject(ServerService);
  private fromBuilder = inject(FormBuilder);
  private confirmationService = inject(ConfirmationService);
  private messageService = inject(MessageService);

  constructor() {
    this.serverFormGroup = this.fromBuilder.group({
      name: new FormControl('', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(20),
      ]),
      owner: new FormControl('', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(20),
      ]),
      ipAddress: new FormControl('', [
        Validators.required,
        Validators.pattern(
          '(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)'
        ),
      ]),
      memory: new FormControl('', [Validators.required]),
      type: new FormControl('', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(20),
      ]),
      icon: new FormControl(''),
    });
  }

  ngOnInit(): void {
    this.retrieveServers();
    this.messages = [
      {
        severity: 'info',
        detail:
          'The servers displayed below are subject to occasional downtime, so its important to continuously fetch and verify their status. We understand the importance of reliable server performance, and we strive to keep them up and running as much as possible. However, please be aware that intermittent disruptions may occur. Rest assured, our team is actively monitoring and maintaining these servers to provide you with the best possible experience. Thank you for your understanding and patience.',
      },
    ];
  }

  public retrieveServers(): void {
    this.serverService.servers().subscribe({
      next: (response) => {
        this.servers = response;
        this.allServers = this.servers.length;
        this.allServersUp = this.servers.filter(
          (server) => server.status === 'SERVER_UP'
        ).length;
        this.allServersDown = this.servers.filter(
          (server) => server.status === 'SERVER_DOWN'
        ).length;
        console.log(response);
      },
      error: (e) => console.error(e),
    });
  }

  get form(): { [key: string]: AbstractControl } {
    return this.serverFormGroup.controls;
  }
  public onSaveServer() {
    this.isFromSubmitted = true;
    this.isLoading = true;

    if (this.serverFormGroup.invalid) {
      return;
    }

    this.serverService.createServer(this.serverFormGroup.value).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.retrieveServers();
        this.closeDialog();
        console.log(response);
        this.messageService.add({
          severity: 'info',
          summary: 'Confirmed',
          detail: 'Record deleted successfully',
        });
        console.log(response);
      },
    });
  }

  public onDelete(uid: string) {
    this.confirmationService.confirm({
      message: 'Do you want to delete this record?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass: 'p-button-danger p-button-text',
      rejectButtonStyleClass: 'p-button-text p-button-text',
      acceptIcon: 'none',
      rejectIcon: 'none',

      accept: () => {
        this.serverService.deleteServe(uid).subscribe({
          next: (response) => {
            this.servers = this.servers.filter((server) => server.uid !== uid);
            this.messageService.add({
              severity: 'info',
              summary: 'Confirmed',
              detail: 'Record deleted successfully',
            });
            console.log(response);
          },
          error: (e) => console.error(e),
        });
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Rejected',
          detail: 'You have rejected',
        });
      },
    });
  }

  showDialog() {
    this.isDialogVisible = true;
  }

  closeDialog() {
    this.isDialogVisible = false;
  }
}
