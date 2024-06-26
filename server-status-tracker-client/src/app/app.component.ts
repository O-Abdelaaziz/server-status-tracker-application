import { Status } from './model/status';
import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ServerService } from './service/server.service';
import {
  BehaviorSubject,
  Observable,
  catchError,
  map,
  of,
  startWith,
} from 'rxjs';
import { AppState } from './model/app-state';
import { CustomResponse } from './model/custom-response';
import { DataState } from './model/data-state';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  public appState$!: Observable<AppState<CustomResponse>>;
  public readonly DataState = DataState;
  public readonly Status = Status;
  private filterSubject = new BehaviorSubject<String>('');
  filterStatus$ = this.filterSubject.asObservable();
  
  public serverService = inject(ServerService);

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$
    .pipe(
      map((response) => {
        return { dataState: DataState.LOADED_STATE, appData: { ...response, data: { servers: response.data.servers?.reverse() } } }

      }),
      startWith({ dataState: DataState.LOADING_STATE }),
      catchError((error: string) =>
        of({ dataState: DataState.ERROR_STATE, error: error })
      )
    );
  }
}
