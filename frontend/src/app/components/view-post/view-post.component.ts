import {Component, OnDestroy, OnInit} from '@angular/core';
import {FileService} from "../../services/file.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import Swal from "sweetalert2";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {CommentService} from "../../services/comment.service";
import {Comment} from "../../models/comment";

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit, OnDestroy {
  post: any;
  id: number | undefined;
  role: string = '';
  editForm: FormGroup;
  sub: Subscription | undefined;
  commentForm: FormGroup;
  comments: Comment[] = [];

  constructor(
    private fileService: FileService,
    private authService: AuthService,
    private commentService: CommentService,
    route: ActivatedRoute,
    private router: Router
  ) {
    const num = route.snapshot.params['id'];
    if (!isNaN(num) && Number.isInteger(Number(num)))
      this.id = +num;
    else {
      Swal.fire({
        title: 'Post not found',
        text: 'Invalid post id',
        icon: 'error'
      }).finally(() => this.router.navigateByUrl('/explore'))
    }
    this.editForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required])
    });
    this.commentForm = new FormGroup({
      comment: new FormControl('', [Validators.required])
    });
  }

  ngOnInit(): void {
    this.role = this.authService.getRole() || '';
    if (this.id) {
      this.fileService.getOne(this.id).subscribe({
        next: data => this.post = data,
        error: err =>
          Swal.fire({
            title: 'Post retrieval failed',
            text: err.error.message,
            icon: 'error'
          }).finally(() => this.router.navigateByUrl('/explore'))
      });
      this.commentService.getComments(this.id).subscribe({
        next: data => this.comments = data,
        error: err =>
          Swal.fire({
            title: 'Comments retrieval failed',
            text: err.error.message,
            icon: 'error'
          })
      });
    }
  }

  edit() {
    const title = this.editForm.get('title')?.value as string;
    const description = this.editForm.get('description')?.value as string;

    this.post.title = title;
    this.post.description = description;

    this.sub = this.fileService.edit(this.post).subscribe({
      next: data => {
        Swal.fire({
          title: 'Success',
          text: 'Post edited successfully',
          icon: 'success',
          confirmButtonText: 'Ok'
        }).finally(() => this.post = data);
      },
      error: err => Swal.fire({
        title: 'Error',
        text: 'Error editing post',
        icon: 'error',
        confirmButtonText: 'Ok'
      }).finally(() => console.log(err)),
      complete: () => console.log('edit complete')
    });
  }

  ngOnDestroy(): void {
    if (this.sub)
      this.sub.unsubscribe();
  }

  createComment(): void {
    const text = this.commentForm.get('comment')?.value as string || '';

    if (text.length === 0) {
      Swal.fire({
        title: 'Error',
        text: 'Comment cannot be empty',
        icon: 'error',
        confirmButtonText: 'Ok'
      });
      return;
    }

    const username = this.authService.getUsername();

    if (username) {
      const createdAt = new Date();
      const postId = this.post.id;

      const commentObj: Comment = {
        text,
        createdAt,
        postId,
        username
      };

      this.sub = this.commentService.createComment(commentObj).subscribe({
        next: data => {
          this.comments.push(data);
          this.commentForm.reset();
        },
        error: err => Swal.fire({
          title: 'Error',
          text: 'Error creating comment',
          icon: 'error',
          confirmButtonText: 'Ok'
        }).finally(() => console.log(err)),
        complete: () => console.log('create complete')
      });
    }
  }

  moderate() {
    this.fileService.moderate(this.post.id).subscribe({
      next: () => {
        Swal.fire({
          title: 'Success',
          text: 'Post moderated successfully',
          icon: 'success',
          confirmButtonText: 'Ok'
        }).finally(() => this.router.navigateByUrl('/explore'));
      },
      error: err => Swal.fire({
        title: 'Error',
        text: 'Error moderating post',
        icon: 'error',
        confirmButtonText: 'Ok'
      }).finally(() => console.log(err)),
      complete: () => console.log('moderate complete')
    });
  }
}
