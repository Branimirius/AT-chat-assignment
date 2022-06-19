import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgentCentreComponent } from './agent-centre/agent-centre.component';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import { LoggedInComponent } from './components/logged-in/logged-in.component';
import { RouteGuardService } from './guard/route-guard.service';

const routes: Routes = [
  {path:'chat', component: LoggedInComponent, canActivate: [RouteGuardService], data: { onlyLoggedIn: true }},
  {path:'', component: AgentCentreComponent, canActivate: [RouteGuardService], data: { onlyLoggedIn: false }},
  {path:'login', component: AuthenticationComponent, canActivate: [RouteGuardService], data: { onlyLoggedIn: false }}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
