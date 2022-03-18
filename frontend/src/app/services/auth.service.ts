import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable, retry, shareReplay} from "rxjs";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Credentials} from "../models/credentials";
import {JwtToken} from "../models/JwtToken";
import {HttpUtilities} from "../models/HttpUtilities";
import {SignupRequest} from "../models/SignupRequest";
import {AbstractControl} from "@angular/forms";
import Swal from "sweetalert2";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router) {
  }

  login(credentials: Credentials): Observable<JwtToken> {
    return this.http.post<JwtToken>(`${environment.authUrl}/login`, JSON.stringify(credentials), HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  logout(): void {
    this.http.post<boolean>(`${environment.authUrl}/logoff`, null).subscribe({
      next: () => {
        localStorage.removeItem('token');
        this.router.navigateByUrl('/credentials');
      },
      error: err => Swal.fire('Error', err.error.message, 'error'),
      complete: () => {
        location.reload();
      }
    });
  }

  register(signupRequest: SignupRequest): Observable<JwtToken> {
    return this.http.post<JwtToken>(`${environment.authUrl}/register`, JSON.stringify(signupRequest), HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  public isLoggedIn(): boolean {
    const token = this.getJwt();
    return !!(token && !HttpUtilities.JWT_HELPER.isTokenExpired(token));
  }


  public getJwt(): string | null {
    return localStorage.getItem('token');
  }

  public getUsername(): string | undefined {
    const token = this.getJwt();
    return !!token ? HttpUtilities.JWT_HELPER.decodeToken(token).sub : undefined;
  }

  public getRole(): string | undefined {
    const token = this.getJwt();
    return !!token ? HttpUtilities.JWT_HELPER.decodeToken(token).role : undefined;
  }

  checkUsername(control: AbstractControl) {
    return this.http.get<boolean>(`${environment.authUrl}/existsByUsername/${control.value}`).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  checkEmail(control: AbstractControl) {
    return this.http.get<boolean>(`${environment.authUrl}/existsByEmail/${control.value}`).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  loginIsExpired() {
    const token = this.getJwt();

    if (!!token)
      return HttpUtilities.JWT_HELPER.isTokenExpired(token);
    return false;
  }
}
