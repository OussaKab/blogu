import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Comment} from "../models/comment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  getComments(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${environment.serverUrl}/comments/post/${id}`);
  }

  createComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(`${environment.serverUrl}/comments`, comment);
  }

  deleteComment(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${environment.serverUrl}/comments/${id}`);
  }

}
