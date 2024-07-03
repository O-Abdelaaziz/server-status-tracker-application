import { CustomResponse } from './../model/custom-response';
import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError} from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { ServerResponse } from '../model/server-response';
import { Status } from '../model/status';
import { ServerRequest } from '../model/server-request';

@Injectable({
  providedIn: 'root',
})
export class ServerService {
  private readonly API_URL = 'http://localhost:8080/api';
   _httpClient = inject(HttpClient);

   public servers():Observable<ServerResponse[]>{
      return this._httpClient.get<ServerResponse[]>(`${this.API_URL}/servers`)
   }

   public createServer(serverRequest:ServerRequest):Observable<ServerRequest>{
      return this._httpClient.post<ServerRequest>(`${this.API_URL}/servers`,serverRequest);
   }

   public deleteServe (uid:string):Observable<boolean>{
      return this._httpClient.delete<boolean>(`${this.API_URL}/servers/${uid}`)
   }
}
