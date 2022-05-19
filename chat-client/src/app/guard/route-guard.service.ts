import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService implements CanActivate {

  constructor(public router: Router, public userService : UserService) { }
  
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const onlyLoggedIn : boolean = route.data.onlyLoggedIn;    
    if (onlyLoggedIn && !this.userService.isLoggedIn()) {
      this.router.navigate(['']);
      return false;
    }
    return true;
  }
}
