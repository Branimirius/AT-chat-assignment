import { Component, OnInit } from '@angular/core';
import { ACLMessage } from 'src/app/model/acl-message';
import { AID } from 'src/app/model/aid';
import { HomeSocketService } from 'src/app/services/socket/home-socket.service';
import { AgentService } from 'src/app/services/agents/agent.service';
import { MessageService } from 'src/app/services/agents/message.service';

@Component({
  selector: 'app-send-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  constructor(private agentService : AgentService, private messageService : MessageService, private agentSocket : HomeSocketService) { 
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  agents : AID[] = []
  performatives : string[] = []

  sender : AID;
  receiver : AID;
  replyTo : AID;
  performative : string;
  username : string = '';
  password : string = '';
  senderUsername : string = '';
  receiverUsername : string = '';
  subject : string = '';
  content : string = '';
  team1 : string = '';
  team2 : string = '';

  liveData$ = this.agentSocket.messages$;

  ngOnInit(): void {
    this.agentSocket.connect();
    this.agentService.getRunningAgents().subscribe(
      data => { this.agents = data;
                this.messageService.getPerformatives().subscribe(
                  data => this.performatives = data
                )
      }
    )
  }

  //this method is called from html code
  public sendMessage() {
    if(this.agents.includes(this.sender) && this.agents.includes(this.receiver) && this.performatives.includes(this.performative)) {
      const userArgs = this.parsingUserArgs();
      const content = this.getMessageContent();
      const message : ACLMessage = new ACLMessage(this.performative, this.sender, [this.receiver], this.getReplyTo(), content, null, userArgs, '', '', '', '', '', '', '', 0);
      this.messageService.sendMessage(message).subscribe()
    }
  }

  //method for acknowledging performatives
  parsingUserArgs() {
    var result : Object = {}
    if(this.performative === 'LOG_IN' || this.performative === 'REGISTER') {
      result['username'] = this.username
      result['password'] = this.password
      return result;
    }
    if(['ADD_MESSAGE', 'ADD_REGISTERED', 'ADD_LOGGED_IN', 'REMOVE_LOGGED_IN', 'PERFORMED'].includes(this.performative)) {
      return result;
    }
    if(this.performative === 'COLLECT') {
      result['team1'] = this.team1
      result['team2'] = this.team2
      return result;
    }
    
     if(this.performative === 'SEND_MESSAGE_USER') {
      result['sender'] = this.senderUsername
      result['receiver'] = this.receiverUsername
      result['subject'] = this.subject
      result['content'] = this.content
      return result
     }

     if(this.performative === 'SEND_MESSAGE_ALL') {
      result['sender'] = this.senderUsername
      result['subject'] = this.subject
      result['content'] = this.content
      return result
     }

     if(['DISPLAY', 'PREDICT'].includes(this.performative)) {
       return result;
     }
     
     else {
      result['username'] = this.username
      return result;
     }
  }

  //collect and predict performatives
  getReplyTo() {
    if(['COLLECT', 'PREDICT'].includes(this.performative)) 
      return this.replyTo
    else
      return null
  }

  //parsing message content according to perforamtive
  getMessageContent() {
    if(this.performative === 'DISPLAY')
      return this.content
    if(this.performative === 'PREDICT')
      return '[]'
    var result : Object = {}

    if(['ADD_REGISTERED', 'ADD_LOGGED_IN', 'REMOVE_LOGGED_IN'].includes(this.performative)) {
      result['username'] = this.username
      result['password'] = this.password
    }

    else if(['ADD_MESSAGE'].includes(this.performative)) {
      var sender = {}
      var receiver = {}
      sender['username'] = this.senderUsername
      sender['password'] = ''
      receiver['username'] = this.receiverUsername
      receiver['password'] = ''
      result['sender'] = sender
      result['receiver'] = receiver
      result['created'] = new Date()
      result['subject'] = this.subject
      result['content'] = this.content
    }

    return JSON.stringify(result);
  }

  
  handleMessage(msg : string) {
    this.agents = JSON.parse(msg)
  }
}
