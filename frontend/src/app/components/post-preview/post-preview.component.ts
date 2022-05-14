import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {PreviewPost} from "../../models/preview-post";
import {DomSanitizer} from "@angular/platform-browser";
import {FileService} from "../../services/file.service";
import {Subscription} from "rxjs";
import Swal from "sweetalert2";

@Component({
  selector: 'app-post-preview',
  templateUrl: './post-preview.component.html',
  styleUrls: ['./post-preview.component.css']
})
export class PostPreviewComponent implements OnInit, OnDestroy {

  @Input() posts: PreviewPost[] = [];
  @Input() hideUsername = false;
  @Input() role = 'ROLE_BLOGGER';
  subs: Subscription[] = [];

  constructor(private domSanitizer: DomSanitizer, private fileService: FileService) {
  }

  ngOnInit(): void {
  }

  toSrc(thumbnail: string, mime: string) {
    return this.domSanitizer.bypassSecurityTrustUrl(`data:${mime};base64,${thumbnail}`);
  }

  unblock(id: number) {
    this.subs.push(this.fileService.unmoderate(id).subscribe({
      next: () => Swal.fire({
        title: 'Success',
        text: 'Post unblocked',
        icon: 'success'
      }).finally(() => location.reload()),
      error: err => {
        Swal.fire({
          title: 'Error',
          text: err.error.message,
          icon: 'error'
        }).finally(() => console.error(err))
      },
      complete: () => {
      }
    }));
  }

  ngOnDestroy(): void {
    this.subs.forEach(s => s.unsubscribe());
  }
}
