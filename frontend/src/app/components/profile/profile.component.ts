import {Component, Input, OnInit} from '@angular/core';
import {Profile} from "../../models/profile";
import {ProfileService} from "../../services/profile.service";
import {ActivatedRoute} from "@angular/router";
import Swal from "sweetalert2";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  @Input() editable: boolean | undefined;
  profile: Profile | undefined;
  username: string;
  role: string | undefined;

  constructor(
    private profileService: ProfileService,
    private authService: AuthService,
    route: ActivatedRoute
  ) {
    this.username = route.snapshot.params['username'];
  }

  ngOnInit(): void {
    this.role = this.authService.getRole();
    this.profileService.getProfile(this.username).subscribe({
      next: profile => this.profile = profile,
      error: err => Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: err.error.message,
      })
    });
  }

}
