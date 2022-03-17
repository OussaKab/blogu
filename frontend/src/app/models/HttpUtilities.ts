// tslint:disable-next-line:no-namespace
import {HttpHeaders} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt";
import {Validators} from "@angular/forms";
import {UrlTree} from "@angular/router";


export namespace HttpUtilities {

  export const REDIRECT_CREDENTIALS: string | UrlTree = !!localStorage.getItem('route') && localStorage.getItem('route') != '/' && localStorage.getItem('route') != '/credentials' ? localStorage.getItem('route') || '/me' : '/me';

  export const JWT_HELPER = new JwtHelperService();

  export const JSON_HTTP_OPTIONS = {headers: new HttpHeaders({'content-type': 'application/json'})};

  export const JWT_PREFIX = 'Bearer';

  export const PASSWORD_VALIDATORS = [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30)
  ];
}
