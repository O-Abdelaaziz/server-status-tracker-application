import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CustomResponse } from '../model/custom-response';
import { Observable, throwError} from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { ServerResponse } from '../model/server-response';
import { Status } from '../model/status';

@Injectable({
  providedIn: 'root',
})
export class ServerService {
  private readonly API_URL = 'http://localhost:8080';
  private _httpClient: HttpClient = Inject(HttpClient);

  public ping$ = (ipAddress: string) =>
    this._httpClient
      .get<CustomResponse>(`${this.API_URL}/servers/ping/${ipAddress}`)
      .pipe(tap(console.log), catchError(this.handleError));

  public filter$ = (status: Status, response: CustomResponse) =>
    new Observable<CustomResponse>(
      subscriber => {
        console.log(subscriber);
        subscriber.next(
          status=== Status.ALL ?{...response,message:`Servers filtered by ${status} status`}:
          {
            ...response,
            message: (response.data?.servers ?? []).filter(server => server.status === status).length >0 ? `Servers filtered by 
            ${status === Status.SERVER_UP ? 'SERVER UP' : 'SERVER DOWN'} status`
            :`No servers of ${status} found`,
            data:{servers:response.data.servers?.filter(server => server.status === status)}
          }
        );
        subscriber.complete();
      }
    )
      .pipe(tap(console.log), catchError(this.handleError));

  public servers$ =<Observable<CustomResponse>>
      this._httpClient
      .get<CustomResponse>(`${this.API_URL}/servers/limit/`+30)
      .pipe(tap(console.log), catchError(this.handleError));

  public server$ = (uid: number) =>
    this._httpClient
      .get<CustomResponse>(`${this.API_URL}/servers/server/${uid}`)
      .pipe(tap(console.log), catchError(this.handleError));

  public save$ = (server: ServerResponse) =>
    this._httpClient
      .post<CustomResponse>(`${this.API_URL}/servers`, server)
      .pipe(tap(console.log), catchError(this.handleError));

  public update$ = (server: ServerResponse, uid: string) =>
    this._httpClient
      .put<CustomResponse>(`${this.API_URL}/servers/update/${uid}`, server)
      .pipe(tap(console.log), catchError(this.handleError));

  public delete$ = (uid: string) =>
    this._httpClient
      .delete<CustomResponse>(`${this.API_URL}/servers/delete/${uid}`)
      .pipe(tap(console.log), catchError(this.handleError));

  private handleError(handleError: HttpErrorResponse): Observable<never> {
    console.log(handleError);

    throw new Error(`An error occurred - Error code: ${handleError.status}`);
  }
}
