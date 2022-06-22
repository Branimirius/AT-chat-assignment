import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AgentType } from "../dto/agent-type";

@Injectable({
    providedIn: 'root'
  })
export class AgentService{

    constructor(private http : HttpClient) { }

    GetAgentTypes(): Observable<AgentType>{
        return this.http.get<any>('http://localhost:8180/chat-war/rest/managers/agents/classes')
    }

    GetAgentsRunning(){}

    RunAgent(){}

}