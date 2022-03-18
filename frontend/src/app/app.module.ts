import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SignupComponent} from './components/signup/signup.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {SignedPageComponent} from './components/signed-page/signed-page.component';
import {HomeComponent} from './components/home/home.component';
import {ErrorComponent} from './components/error/error.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MyprofileComponent} from './components/myprofile/myprofile.component';
import {ExploreComponent} from './components/explore/explore.component';
import {SearchComponent} from './components/search/search.component';
import {BloggerInterceptor} from "./services/blogger.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    NavbarComponent,
    SignedPageComponent,
    HomeComponent,
    ErrorComponent,
    MyprofileComponent,
    ExploreComponent,
    SearchComponent
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
