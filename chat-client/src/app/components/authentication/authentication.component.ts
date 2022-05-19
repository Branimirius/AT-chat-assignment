import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { WebsocketService } from 'src/app/services/websocket.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {

  public username : string;
  public password : string;

  liveData$ = this.wsService.messages$;

  constructor(private userService : UserService, private wsService : WebsocketService, private snackBar : MatSnackBar, private router : Router) {
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  ngOnInit(): void {
    this.wsService.connect();
  }

  login() {
    this.userService.login(this.username, this.password);
  }

  signup() {
    this.userService.signup(this.username, this.password);
  }

  handleMessage(message : string) {
    if(message.match('.+:.*')) {
      var type = message.split(':')[0];
      var content = message.split(':')[1];
      if(type == 'login')
        this.handleLogin(content);
      else if(type == 'register')
        this.handleRegistration(content);
    }
  }

  handleLogin(message : string) {
    if(message.startsWith('OK ')) {
      localStorage.setItem('identifier', message.substring(3));
      this.router.navigate(['chat']);
    }
    else {
      this.openSnackBar(message);
    }
  }

  handleRegistration(message : string) {
    this.openSnackBar(message);
  }

  openSnackBar(message : string) {
    this.snackBar.open(message, 'Okay', {duration : 5000});
  }
}
