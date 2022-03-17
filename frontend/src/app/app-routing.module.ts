import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignupComponent} from "./components/signup/signup.component";
import {ErrorComponent} from "./components/error/error.component";
import {MyprofileComponent} from "./components/myprofile/myprofile.component";
import {AuthGuardService as AuthGuard} from "./services/auth-guard.service";
import {ExploreComponent} from "./components/explore/explore.component";
import {SearchComponent} from "./components/search/search.component";

const routes: Routes = [
  {
    path: 'credentials',
    component: SignupComponent
  },
  {
    path: 'explore',
    component: ExploreComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'search/:query',
    component: SearchComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'me',
    component: MyprofileComponent,
    canActivate: [AuthGuard]

  },
  {
    path: 'error',
    component: ErrorComponent
  },
  {
    path: '',
    redirectTo: '/credentials',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '/error',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
