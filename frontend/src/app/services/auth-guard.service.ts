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
      if (this.authService.loginIsExpired()) {
        Swal.fire({
          title: 'Session Expired',
          text: 'Your session has expired. Please login again.',
          icon: 'warning',
          showCancelButton: false,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'OK'
        }).then((result) => {
          if (result.value) {
            localStorage.removeItem('token');
            this.router.navigateByUrl('/credentials');
          }
        });
      } else {
        Swal.fire({
          title: 'Login Required',
          text: 'Please login to continue.',
          icon: 'warning',
          showCancelButton: false,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'OK'
        }).then((result) => {
          if (result.value) {
            localStorage.removeItem('token');
            this.router.navigateByUrl('/credentials');
          }
        });
      }
    }
    return isAuthenticated;
  }

}
