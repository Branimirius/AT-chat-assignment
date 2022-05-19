import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http : HttpClient) { }

  login(username, password) {
    var data = new Object();
    data['username'] = username;
    data['password'] = password;
    this.http.post('http://localhost:8180/chat-war/rest/users/login/' + localStorage.getItem('sessionId'), data).subscribe();
  }

  logout() {
    this.http.delete('http://localhost:8180/chat-war/rest/users/loggedIn/' + localStorage.getItem('sessionId') + "/" + localStorage.getItem('identifier')).subscribe();
    localStorage.removeItem('identifier');
  }

  signup(username, password) {
    var data = new Object();
    data['username'] = username;
    data['password'] = password;
    this.http.post('http://localhost:8180/chat-war/rest/users/register/' + localStorage.getItem('sessionId'), data, {headers : new HttpHeaders({ 'Content-Type': 'application/json' })}).subscribe();
  }

  getRegistered() {
    this.http.get('http://localhost:8180/chat-war/rest/users/registered/' + localStorage.getItem('sessionId')).subscribe();
  }

  getLoggedIn() {
    this.http.get('http://localhost:8180/chat-war/rest/users/loggedIn/' + localStorage.getItem('sessionId')).subscribe();
  }

  isLoggedIn() {
    return localStorage.getItem('identifier') != null;
  }
}
