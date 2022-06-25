import { Component, OnInit } from '@angular/core';
import { ConsoleSocketService } from 'src/app/services/socket/console-socket.service';

@Component({
  selector: 'app-console',
  templateUrl: './console.component.html',
  styleUrls: ['./console.component.css']
})
export class ConsoleComponent implements OnInit {

  liveData$ = this.loggerSocket.messages$;

  constructor(private loggerSocket : ConsoleSocketService) {
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  logs : string[] = []

  ngOnInit(): void {
    this.loggerSocket.connect()
  }

  

  clearConsole() {
    this.logs = []
  }

  handleMessage(msg : string) {
    this.logs.push(msg)
  }
}
