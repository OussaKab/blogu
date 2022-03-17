import {AuthService} from 'src/app/services/auth.service';
import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from "rxjs";
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const isAuthenticated: boolean = this.authService.isLoggedIn();
    localStorage.setItem('route', this.router.url);
    if (!isAuthenticated) {
      Swal.fire({
        title: 'You are not logged in',
        text: 'Please login to continue...',
        icon: 'warning',
        confirmButtonText: 'Login'
      }).then(() => this.authService.logout());
    }
    return isAuthenticated;
  }

}
