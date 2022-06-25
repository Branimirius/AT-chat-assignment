import { Component } from '@angular/core';
import { AgentType } from './model/agent-type';
import { AID } from './model/aid';
import { AgentService } from './services/agents/agent.service';
import { MessageService } from './services/agents/message.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ChatClientFront';

  showStartAgent = false;
  showSendMessage = false;

  constructor() {
  }
}
