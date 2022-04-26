import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Profile} from "../models/profile";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) {
  }

  public getProfile(username: string) {
    return this.http.get<Profile>(`${environment.serverUrl}/profiles/${username}`);
  }

}
