import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {HttpUtilities} from "../models/HttpUtilities";
import {Observable} from "rxjs";

@Injectable()
export class BloggerInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = this.authenticationService.getJwt();

    if (token && !HttpUtilities.JWT_HELPER.isTokenExpired(token)) {
      request = request.clone({
        setHeaders: {Authorization: `${HttpUtilities.JWT_PREFIX} ${token}`}
      });
    }
    return next.handle(request);
  }
}
