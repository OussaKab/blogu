import {Injectable} from '@angular/core';
import {Observable, retry, shareReplay} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpUtilities} from "../models/HttpUtilities";
import {HttpClient} from "@angular/common/http";
import {Post} from "../models/post";
import {PreviewPost} from "../models/preview-post";

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

  getFile(path: string): Observable<Blob> {
    return this.http.get(`${environment.serverUrl}/files/${path}`, {responseType: 'blob'}).pipe(
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

  moderate(id: number) {
    return this.http.post<boolean>(`${environment.serverUrl}/posts/moderate/${id}`, HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );
  }

  getAllModerateForUser() {
    return this.http.get<PreviewPost[]>(`${environment.serverUrl}/posts/moderated`, HttpUtilities.JSON_HTTP_OPTIONS).pipe(
      retry(2),
      shareReplay(1)
    );

  }
}
