import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignupComponent} from "./components/signup/signup.component";
import {ErrorComponent} from "./components/error/error.component";
import {AuthGuardService as AuthGuard} from "./services/auth-guard.service";
import {ExploreComponent} from "./components/explore/explore.component";
import {SearchComponent} from "./components/search/search.component";
import {UploadComponent} from "./components/upload/upload.component";
import {ViewPostComponent} from "./components/view-post/view-post.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {ModerationsComponent} from './components/moderations/moderations.component';

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
    path: 'posts/:id',
    component: ViewPostComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'my-moderations',
    component: ModerationsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'upload',
    component: UploadComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'results/:query',
    component: SearchComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'profile/:username',
    component: ProfileComponent,
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
export class AppRoutingModule {
}
