import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { catchError, tap, switchAll, share } from 'rxjs/operators';
import { EMPTY, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HomeSocketService {

  private WS_ENDPOINT : string = 'ws://localhost:8180/chat-war/ws/agent';

  private socket$: WebSocketSubject<any>;
  private messagesSubject$ = new Subject();
  public messages$ = this.messagesSubject$.pipe(switchAll(), share(), catchError(e => { throw e }));
  
  private getWS() {
    return webSocket({url: this.WS_ENDPOINT, deserializer: msg => msg.data});
  }


  public connect(): void {
    if (!this.socket$ || this.socket$.closed) {
      this.socket$ = this.getWS();
      const messages = this.socket$.pipe(
        tap({
          error: error => console.log(error),
        }), catchError(_ => EMPTY));

      this.messagesSubject$.next(messages);
      this.socket$.subscribe({
        complete: () => { console.log('connection with agent socket closed') }
      })
    }
  }
  
  

  

  close() {
    this.socket$.complete();
  }

  sendMessage(msg: any) {
    this.socket$.next(msg);
  }
}
