import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MessageService } from 'src/app/services/message.service';
import { UserService } from 'src/app/services/user.service';
import { WebsocketService } from 'src/app/services/websocket.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  liveData$ = this.wsService.messages$;

  private loggedIn : string[] = [];
  private registered : string[] = [];
  public displayed : Object[] = [];

  @Output() messageEvent = new EventEmitter<string>();
  public selected : string = null;

  constructor(private wsService : WebsocketService, private userService : UserService, private s : MessageService) { 
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
    this.wsService.connect();
    setTimeout(() => {this.userService.getLoggedIn(); this.userService.getRegistered();}, 500)
  }

  ngOnInit(): void {
    
  }

  handleMessage(message : string) {
    if(message.match('.+:.*')) {
      var type = message.split(':')[0];
      var content = message.split(':')[1];
      if(type == 'loggedInList') {
        this.handleLoggedInList(content);
      }
      else if(type == 'registeredList') {
        this.handleRegisteredList(content);
      }
    }
  }

  handleLoggedInList(message : string) {
    this.loggedIn = message.split(',');
    this.setUpDisplayedUsers();
  }

  handleRegisteredList(message : string) {
    this.registered = message.split(',');
    this.setUpDisplayedUsers();
  }

  setUpDisplayedUsers() {
    this.displayed = [];
    for(var user of this.registered) {
      if(this.loggedIn.includes(user))
        this.displayed.push({'user':user, 'active':true})
      else
        this.displayed.push({'user':user, 'active':false})
    }
    if(this.selected == null && this.displayed.length > 0) {
      this.select(this.displayed[0]['user']);
    }
  }

  sendMessage() {
    this.messageEvent.emit(this.selected)
  }

  select(user : string) {
    this.selected = user;
    this.sendMessage();
  }
}
