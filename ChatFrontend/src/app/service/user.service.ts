import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

    constructor(private http : HttpClient) { }
  
    login(username: string, password: string) {
      const body = {
          username: username,
          password: password
      }
      let headers = new HttpHeaders({
        'Content-Type': 'application/json' });
      let options = { headers: headers };
      this.http.post('http://localhost:8180/ChatWAR/rest/users/login/' + localStorage.getItem('sessionId'), body, options).subscribe();
    }
  
    register(username: string, password: string) {
        const body = {
            username: username,
            password: password
        }
        let headers = new HttpHeaders({
          'Content-Type': 'application/json' });
        let options = { headers: headers };
      this.http.post('http://localhost:8180/ChatWAR/rest/users/register/' + localStorage.getItem('sessionId'), body, options).subscribe();
    }   
}