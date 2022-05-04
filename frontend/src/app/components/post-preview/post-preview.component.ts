import {Component, Input, OnInit} from '@angular/core';
import {PreviewPost} from "../../models/preview-post";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-post-preview',
  templateUrl: './post-preview.component.html',
  styleUrls: ['./post-preview.component.css']
})
export class PostPreviewComponent implements OnInit {

  @Input() posts: PreviewPost[] = [];
  @Input() hideUsername = false;
  url= '';

  constructor(private domSanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
  }

  toSrc(thumbnail: string, mime: string) {
    return this.domSanitizer.bypassSecurityTrustUrl(`data:${mime};base64,${thumbnail}`);
  }

}
