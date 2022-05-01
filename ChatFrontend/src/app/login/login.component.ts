import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username : string;
  public password : string;

  constructor(private userService: UserService) { 
    this.username = "";
    this.password = "";
  }

  ngOnInit(): void {
  }

  login(){
    this.userService.login(this.username, this.password);
  }
  
  register(){
    this.userService.register(this.username, this.password);
  }

}
