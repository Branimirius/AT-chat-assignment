import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AgentType } from '../../model/agent-type';
import { AID } from '../../model/aid';


@Injectable({
  providedIn: 'root'
})
export class AgentService {

  baseUrl : string = 'http://localhost:8180/chat-war/rest/agents/'

  constructor(private http : HttpClient) { }


  getRunningAgents() : Observable<AID[]> {
    return this.http.get<AID[]>(this.baseUrl + 'running');
  }
  getTypes() : Observable<AgentType[]> {
    return this.http.get<AgentType[]>(this.baseUrl + 'classes');
  }

  


  stopAgent(aid: AID) : Observable<any> {
    return this.http.put(this.baseUrl +'running', aid);
  }
  startAgent(type: AgentType, name: string) : Observable<any> {
    return this.http.put(this.baseUrl + 'running/' + name, type);
  }

  
 }
