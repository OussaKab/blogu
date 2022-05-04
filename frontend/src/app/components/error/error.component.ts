import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  url: string = '';
  takeawayUrl: string = '';
  takeawayText: string = '';
  constructor(router: Router, auth: AuthService) {
    router.events.forEach((event) => {
      if (event instanceof NavigationEnd) {
        const url = event.url;
        this.url = url === '/error' ? 'error page' : url;
      }
    });
    this.takeawayUrl = !auth.isLoggedIn() ? '/credentials' : '/explore';
    this.takeawayText = !auth.isLoggedIn() ? 'Login' : 'See more content';
  }

  ngOnInit(): void {
  }

}
