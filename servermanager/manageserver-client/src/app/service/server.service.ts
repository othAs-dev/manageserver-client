import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, tap, throwError} from "rxjs";
import {CustomResponse} from "../interface/custom-response";
import {Server} from "../interface/server";
import {Status} from "../enum/status.enum";

@Injectable({providedIn: 'root'})
export class ServerService {
  private readonly endpoint = 'http://localhost:8000/api/server';
  private http = inject(HttpClient);


  servers$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.endpoint}/list`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );
  save$ = (server: Server) => <Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.endpoint}/save`, server)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );
  ping$ = (ipAdress: String) => <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.endpoint}/ping/${ipAdress}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );
  filter$ = (status: Status, response: CustomResponse): Observable<CustomResponse> => {
    return new Observable<CustomResponse>(subscriber => {
      console.log(response);
      subscriber.next({
        ...(status === Status.ALL ? {...response, message: `Server filtered by ${status}`} :
          {
            ...response, message: response.data.servers?.filter(server => server.status === status).length > 0 ?
              `Server filtered by ${status === Status.SERVER_UP ? "Server up" : "Server down"}` : `No server with status ${status}`
          }),
        data: {
          servers: response.data.servers.filter(server => server.status === status)
        }
      });
      subscriber.complete();
    }).pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  };

  delete$ = (serverId: number) => <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(`${this.endpoint}/delete/${serverId}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );


  private handleError(handleError: HttpErrorResponse): Observable<never> {
    console.log(handleError)
    return throwError(`Error code ${handleError.status} : ${handleError.message}`);
  };
}
