import { Component, OnInit } from '@angular/core';
import { AgentType } from 'src/app/model/agent-type';
import { AgentService } from 'src/app/services/agents/agent.service';
import { TypeSocketService } from 'src/app/services/socket/type-socket.service';

@Component({
  selector: 'app-start-agent',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

  liveData$ = this.typeSocket.messages$;
  
  constructor(private agentService : AgentService, private typeSocket : TypeSocketService) {
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  types : AgentType[] = []
  type : AgentType
  agentName : string = ''

  ngOnInit(): void {
    this.typeSocket.connect();
    this.agentService.getTypes().subscribe(
      data => this.types = data
    )
  }

  public start() {
    if(this.types.includes(this.type) && this.agentName.length > 0)
      this.agentService.startAgent(this.type, this.agentName).subscribe()
  }

  handleMessage(msg : string) {
    this.types = JSON.parse(msg)
  }
}
