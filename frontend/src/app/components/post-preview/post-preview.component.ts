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

  constructor(private domSanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
  }

  toSrc(thumbnail: number[], mime: string) {
    // @ts-ignore
    let blob = new Blob(new Uint8Array(thumbnail), {type: mime});
    let url = URL.createObjectURL(blob);
    return this.domSanitizer.bypassSecurityTrustUrl(url);
  }

}
