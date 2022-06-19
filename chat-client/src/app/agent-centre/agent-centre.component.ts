import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Message } from '../dto/message';
import { MessageService } from '../services/message.service';
import { WebsocketService } from '../services/websocket.service';

@Component({
  selector: 'app-agent-centre',
  templateUrl: './agent-centre.component.html',
  styleUrls: ['./agent-centre.component.css']
})
export class AgentCentreComponent implements OnInit {
  public selectedAgent : string = null;
  constructor(private messageService : MessageService, private wsService : WebsocketService) { }
  liveData$ = this.wsService.messages$;

  @Input() selected : string = null;

  public allMessages : Message[] = [];
  public displayedMessages : Message[] = [];

  public subject : string = "";
  public content : string = "";

  displayedColumns: string[] = ['date', 'message'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  dataSource = new MatTableDataSource<Message>(this.displayedMessages);



  ngOnInit(): void {
  }
  receiveMessage($event) {
    this.selected = $event
  }
  setUpDisplayedMessages() {
    this.displayedMessages = [];
    this.sortMessagesByDateTimeCreated();
    this.filterMessagesBySelectedUser();
    this.dataSource.data = this.displayedMessages;
    this.dataSource.paginator = this.paginator;
  }

  sortMessagesByDateTimeCreated() {
    for(var message of this.allMessages) {
      var dateTimeBase = message.dateTime.toString().split('.')[0]
      var dateBase = dateTimeBase.split('T')[0]
      var timeBase : string = dateTimeBase.split('T')[1]
      var hour = Number(timeBase.split(':')[0]) != NaN ? Number(timeBase.split(':')[0]) : 0
      var minute = Number(timeBase.split(':')[1]) != NaN ? Number(timeBase.split(':')[1]) : 0
      var second = Number(timeBase.split(':')[2]) != NaN ? Number(timeBase.split(':')[2]) : 0
      if(timeBase.split(':').length < 3) {
        second = 0
      }
      console.log(message.dateTime)
      console.log(hour)
      console.log(minute)
      console.log(second)
      message['date'] = new Date(Number(dateBase.split('-')[0]), Number(dateBase.split('-')[1]), Number(dateBase.split('-')[2]), hour, minute, second)
    }
    this.allMessages.sort((m1, m2) => m1['date'].getTime() - m2['date'].getTime())
  }

  filterMessagesBySelectedUser() {
    for(var message of this.allMessages) {
      if(message.otherUsername == this.selected) {
        this.displayedMessages.push(message);
      }
    }
  }

  handleMessage(message : string) {
    if(message.match('.+:.*')) {
      var type = message.split(':')[0];
      if(type == 'messageList') {
        this.handleMessageList(message);
      }
      else if(type == 'message') {
        this.handleNewMessage(message);
      }
    }
  }

  handleMessageList(message : string) {
    message = message.substring(12, message.length)
    this.allMessages = JSON.parse(message);
    this.setUpDisplayedMessages();
  }

  handleNewMessage(message : string) {
    message = message.substring(8, message.length)
    this.allMessages.push(JSON.parse(message));
    this.setUpDisplayedMessages();
  }

  send() {
    this.messageService.send(this.selected, this.subject, this.content)
    this.subject = ""
    this.content = ""
  }

  sendToAll() {
    this.messageService.sendToAll(this.subject, this.content)
    this.subject = ""
    this.content = ""
  }
}
