import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConsoleComponent } from './components/console-component/console.component';
import { StartComponent } from './components/start-component/start.component';
import { TypesComponent } from './components/types-component/types.component';
import { RunningComponent } from './components/running-agents/running.component';
import { MessageComponent } from './components/message-component/message.component';

import {MatListModule} from '@angular/material/list'; 
import {MatSelectModule} from '@angular/material/select'; 
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button'
import { FormsModule } from '@angular/forms';
import { AgentsComponent } from './components/agents-component/agents.component';

@NgModule({
  declarations: [
    AppComponent,
    ConsoleComponent,
    StartComponent,
    TypesComponent,
    RunningComponent,
    MessageComponent,
    AgentsComponent
    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CommonModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatListModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
