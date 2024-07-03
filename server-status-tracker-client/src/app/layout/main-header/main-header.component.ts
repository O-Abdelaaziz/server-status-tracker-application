import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CreateServerComponent } from '../../components/create-server/create-server.component';
@Component({
  selector: 'app-main-header',
  standalone: true,
  imports: [ButtonModule],
  templateUrl: './main-header.component.html',
  styleUrl: './main-header.component.css',
  providers: [DialogService],
})
export class MainHeaderComponent implements OnInit {
  ref: DynamicDialogRef | undefined;
  @Output() openModel: EventEmitter<boolean> = new EventEmitter<boolean>();

  private dialogService = inject(DialogService);

  ngOnInit(): void {}

  showDialog() {
    this.openModel.emit(true);
  }

  showCreateServerModel() {
    this.ref = this.dialogService.open(CreateServerComponent, {
      header: 'Create New Server',
      width: '50vw',
      contentStyle: { overflow: 'auto' },
      modal: true,
      breakpoints: {
        '960px': '75vw',
        '640px': '90vw',
      },
    });
  }
}
