import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SignupComponent} from './components/signup/signup.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {HomeComponent} from './components/home/home.component';
import {ErrorComponent} from './components/error/error.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ExploreComponent} from './components/explore/explore.component';
import {SearchComponent} from './components/search/search.component';
import {BloggerInterceptor} from "./services/blogger.interceptor";
import {UploadComponent} from './components/upload/upload.component';
import {ViewPostComponent} from './components/view-post/view-post.component';
import {ProfileComponent} from './components/profile/profile.component';
import {PostPreviewComponent} from './components/post-preview/post-preview.component';
import {ModerationsComponent} from './components/moderations/moderations.component';
import {DragAndDropDirective} from './directives/drag-and-drop.directive';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    NavbarComponent,
    HomeComponent,
    ErrorComponent,
    ExploreComponent,
    SearchComponent,
    UploadComponent,
    ViewPostComponent,
    ProfileComponent,
    PostPreviewComponent,
    ModerationsComponent,
    DragAndDropDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: BloggerInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
