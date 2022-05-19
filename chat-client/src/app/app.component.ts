import { Component, OnInit } from '@angular/core';
import { WebsocketService } from './services/websocket.service';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'chat-client';

  liveData$ = this.wsService.messages$;

  constructor(private wsService : WebsocketService, public userService : UserService) {
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }
  
  ngOnInit(): void {
    this.wsService.connect();
  }

  handleMessage(message : string) {
    if(message.match('.+:.*')) {
      var type = message.split(':')[0];
      var content = message.split(':')[1];
      if(type == 'sessionId')
        localStorage.setItem('sessionId', content);
    }
  }
}
