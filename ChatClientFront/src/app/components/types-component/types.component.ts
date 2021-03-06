import { Component, OnInit } from '@angular/core';
import { AgentType } from 'src/app/model/agent-type';
import { AgentService } from 'src/app/services/agents/agent.service';
import { TypeSocketService } from 'src/app/services/socket/type-socket.service';

@Component({
  selector: 'app-agent-types',
  templateUrl: './types.component.html',
  styleUrls: ['./types.component.css']
})
export class TypesComponent implements OnInit {

  liveData$ = this.typeSocket.messages$;

  constructor(private agentService : AgentService, private typeSocket : TypeSocketService) {
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  types : AgentType[] = []

  handleMessage(msg : string) {
    this.types = JSON.parse(msg)
  }


  ngOnInit(): void {
    this.typeSocket.connect();
    this.agentService.getTypes().subscribe(
      data => this.types = data
    )
  }



  
}
