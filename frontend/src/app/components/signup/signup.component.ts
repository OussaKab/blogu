import {Component, EventEmitter, Injectable, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import Swal from 'sweetalert2';
import {HttpUtilities} from "../../models/HttpUtilities";
import {RoleAssign} from "../../models/role-assign";
import {SignupRequest} from "../../models/SignupRequest";
import {Credentials} from "../../models/credentials";


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class SignupComponent implements OnInit {

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter<boolean>();
  possibleRoles: RoleAssign[] = Object.values(RoleAssign);

  signupForm: FormGroup;
  loginForm: FormGroup;

  signupFormSubmitted = false;
  loginFormSubmitted = false;

  errMessageLogin: string = '';
  errMessageSignup: string = '';
  alreadyLoggedIn = false;
  private subs: Subscription[] = [];

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) {
    if (this.authService.isLoggedIn())
      this.alreadyLoggedIn = true;

    this.signupForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', HttpUtilities.PASSWORD_VALIDATORS),
      email: new FormControl('', [Validators.required, Validators.email]),
      role: new FormControl('', [Validators.required])
    });

    this.loginForm = new FormGroup({
      usernameLogin: new FormControl('', [Validators.required]),
      passwordLogin: new FormControl('', [Validators.required])
    });
  }

  ngOnDestroy(): void {
    this.subs.forEach(s => s.unsubscribe());
  }

  ngOnInit(): void {
  }

  signup(): void {
    this.signupFormSubmitted = true;

    if (this.signupForm.valid) {
      const email = this.signupForm.get('email')?.value as string;
      const username = this.signupForm.get('username')?.value as string;
      const password = this.signupForm.get('password')?.value as string;

      const credentials: Credentials = {username, password};

      const role: RoleAssign = this.signupForm.get('role')?.value as RoleAssign;

      const s: SignupRequest = {credentials, email, role};

      this.subs.push(this.authService.register(s).subscribe({
          next: jwt => {
            localStorage.setItem('token', jwt.token);
            Swal.fire({
              title: 'Signup successful!',
              text: `${username} was created!`,
              icon: 'success'
            });
            this.loggedIn.emit(true);
          },
          error: err => {
            Swal.fire({
              title: `signup error`,
              text: err.error ? err.error.message : 'Server may be down... Contact us at help@blogu.com with a screenshot for troubleshooting.',
              icon: 'error'
            });
            this.errMessageSignup = err.error ? err.error.message : 'Server may be down... Contact us at help@blogu.com with a screenshot for troubleshooting.';
          },
          complete: () => {
            location.reload();
            this.router.navigateByUrl(HttpUtilities.REDIRECT_CREDENTIALS);
          }
        }
      ));
    }
  }

  login(): void {
    this.loginFormSubmitted = true;
    if (this.loginForm.valid) {
      const username = this.loginForm.get('usernameLogin')?.value as string;
      const password = this.loginForm.get('passwordLogin')?.value as string;
      this.subs.push(this.authService.login({username, password}).subscribe({
        next: jwt => {
          localStorage.setItem('token', jwt.token);
          Swal.fire({
            title: 'login successful!',
            text: `redirected to ${HttpUtilities.REDIRECT_CREDENTIALS}`,
            icon: 'success'
          });
          this.loggedIn.emit(true);
        },
        error: err => {
          Swal.fire({
            title: 'login error',
            text: err.error.message,
            icon: 'error'
          });
          this.loggedIn.emit(false);
          this.errMessageLogin = err.error.message;
        },
        complete: () => {
          location.reload();
          this.router.navigateByUrl(HttpUtilities.REDIRECT_CREDENTIALS)
        }
      }));
    }
  }
}
