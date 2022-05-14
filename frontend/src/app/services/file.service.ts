import {Injectable} from '@angular/core';
import {Observable, retry, shareReplay} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpUtilities} from "../models/HttpUtilities";
import {HttpClient} from "@angular/common/http";
import {Post} from "../models/post";
import {PreviewPost} from "../models/preview-post";
import {ModerationDTO} from "../models/moderation-d-t-o";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) {
  }

  upload(uploadPost: FormData) {
    return this.http.post<number>(`${environment.serverUrl}/posts`, uploadPost, {
      observe: 'events',
      reportProgress: true
    }).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  getOne(id: number) {
    return this.http.get<Post>(`${environment.serverUrl}/posts/${id}`, HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  getFile(data: any): Observable<Blob> {
    return this.http.post(`${environment.serverUrl}/files/single?`, data, {responseType: 'blob'}).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  getAll() {
    return this.http.get<PreviewPost[]>(`${environment.serverUrl}/posts`).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  edit(upl: any) {
    return this.http.put(`${environment.serverUrl}/posts/${upl.id}`, JSON.stringify(upl), HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  search(search: string) {
    return this.http.post<PreviewPost[]>(`${environment.serverUrl}/posts/search/${search}`, HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  moderate(moderationDTO: ModerationDTO) {
    return this.http.post<boolean>(`${environment.serverUrl}/posts/moderate`, JSON.stringify(moderationDTO), HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  unmoderate(postId: number) {
    return this.http.post<boolean>(`${environment.serverUrl}/posts/unmoderate/${postId}`, HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  getAllModerateForUser(username: string) {
    return this.http.post<PreviewPost[]>(`${environment.serverUrl}/posts/moderations-for-user/${username}`, HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );

  }
}
