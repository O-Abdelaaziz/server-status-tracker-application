import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ServerService } from './service/server.service';
import { Observable, catchError, map, of, startWith } from 'rxjs';
import { AppState } from './model/app-state';
import { CustomResponse } from './model/custom-response';
import { DataState } from './model/data-state';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  public appState$!: Observable<AppState<CustomResponse>>;
  public serverService = inject(ServerService);

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$.pipe(
      map((response) => {
        return { dataState: DataState.LOADED_STATE, appData: response };
      }),
      startWith({ dataState: DataState.LOADING_STATE }),
      catchError((error: string) =>
        of({ dataState: DataState.ERROR_STATE, error: error })
      )
    );
  }
}
