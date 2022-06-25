import { Component, OnInit } from '@angular/core';
import { AID } from 'src/app/model/aid';
import { HomeSocketService } from 'src/app/services/socket/home-socket.service';
import { AgentService } from 'src/app/services/agents/agent.service';

@Component({
  selector: 'app-running-agents',
  templateUrl: './running.component.html',
  styleUrls: ['./running.component.css']
})
export class RunningComponent implements OnInit {

  liveData$ = this.agentSocket.messages$;

  constructor(private agentService : AgentService, private agentSocket : HomeSocketService) {
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  running : AID[] = []
  
  ngOnInit(): void {
    this.agentSocket.connect();
    this.agentService.getRunningAgents().subscribe(
      data => this.running = data
    )
  }

  public stop(agent : AID) {
    this.agentService.stopAgent(agent).subscribe()
  }

  handleMessage(msg : string) {
    this.running = JSON.parse(msg)
  }
}
