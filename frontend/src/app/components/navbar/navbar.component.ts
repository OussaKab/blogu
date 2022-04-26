import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  username: string | undefined;
  searchForm: FormGroup;
  role: string | undefined;

  constructor(private authService: AuthService) {
    this.searchForm = new FormGroup({
      search: new FormControl('', [Validators.required])
    });
  }

  ngOnInit(): void {
    this.username = this.authService.getUsername();
    this.role = this.authService.getRole();
  }

  logoff() {
    this.authService.logout();
  }

  search() {
    let search = this.searchForm?.get('search')?.value as string;
    if (search) {
      location.href = '/results/' + search;
    }
  }
}
