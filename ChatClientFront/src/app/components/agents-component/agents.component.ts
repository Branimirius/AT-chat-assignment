import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './agents.component.html',
  styleUrls: ['./agents.component.css']
})
export class AgentsComponent implements OnInit {

  showStartForm = false;
  showMessageForm = false;

  constructor() { }

  ngOnInit(): void {
  }

  toggleStart() {
    this.showStartForm = !this.showStartForm
    if(this.showStartForm)
      this.showMessageForm = false
  }

  toggleMessage() {
    this.showMessageForm = !this.showMessageForm
    if(this.showMessageForm)
      this.showStartForm = false
  }
}
