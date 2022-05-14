import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FileService} from "../../services/file.service";
import {Subscription} from "rxjs";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-view-content',
  templateUrl: './view-content.component.html',
  styleUrls: ['./view-content.component.css']
})
export class ViewContentComponent implements OnInit, OnDestroy {

  @Input() src: string = '';
  @Input() type: string | undefined;
  formattedSrc: any;
  @Input() autoplay: boolean = false;
  private sub: Subscription | undefined;

  constructor(private dom: DomSanitizer, private fileService: FileService) {
  }

  ngOnInit(): void {
    let formData = new FormData();
    formData.append('path', this.src);
    this.sub = this.fileService.getFile(formData).subscribe({
      next: data => {
        const unsafeUrl = URL.createObjectURL(data);
        this.formattedSrc = this.dom.bypassSecurityTrustUrl(unsafeUrl);
      },
      error: err => console.log(err),
      complete: () => console.log('complete')
    });
  }

  ngOnDestroy(): void {
    if (this.sub)
      this.sub.unsubscribe();
  }
}
