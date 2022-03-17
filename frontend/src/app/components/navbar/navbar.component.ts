import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  username: string | undefined;
  searchForm: FormGroup;

  constructor(private authService: AuthService, private router: Router) {
    this.searchForm = new FormGroup({
      search: new FormControl('', [Validators.required])
    });
  }

  ngOnInit(): void {
    this.username = this.authService.getUsername();
  }

  logoff() {
    this.authService.logout();
  }

  search() {
    let search = this.searchForm?.get('search')?.value as string;
    if (search) {
      this.router.navigateByUrl('/search/' + search);
    }
  }
}
