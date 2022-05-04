import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {FileService} from "../../services/file.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import Swal from "sweetalert2";
import {ContentType} from "../../models/content-type";

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit, OnDestroy {

  uploadForm: FormGroup;
  sub: Subscription | undefined;
  content: File | undefined;
  progress = 0;
  thumbnail: SafeUrl = '';
  thumbnailUrl: SafeUrl = '';
  contentUrl: any;
  typeOfContent: any;

  constructor(
    private fileService: FileService,
    private router: Router,
    private domSanitizer: DomSanitizer
  ) {
    this.uploadForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      content: new FormControl('', [Validators.required]),
      thumbnail: new FormControl('', [Validators.required])
    });
  }

  ngOnInit(): void {
  }

  onContentChange(event: any) {
    this.onChange('content', event);
  }

  onChange(name: string, event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.fileDrop(name, file);
    }
  }

  onThumbnailChange(event: any) {
    this.onChange('thumbnail', event);
  }

  upload() {
    if (this.uploadForm.valid) {
      const title = this.uploadForm.get('title')?.value as string;
      const description = this.uploadForm.get('description')?.value as string;


      const uploadData = new FormData();
      uploadData.append('title', title);
      uploadData.append('description', description);
      uploadData.append('contentType', ContentType[this.typeOfContent.toUpperCase() as keyof typeof ContentType]);
      uploadData.append('thumbnail', this.uploadForm.get('thumbnail')?.value as File);
      uploadData.append('content', this.uploadForm.get('content')?.value as File);

      this.sub = this.fileService.upload(uploadData)
        .subscribe({
          next: (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              const id = parseInt(event.body);
              this.router.navigate(['/posts', id])
            }
          },
          error: err => {
            console.log(err);
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: err.error?.message || 'Something went wrong!'
            });
          }
        });
    }
  }

  ngOnDestroy(): void {
    if (this.sub)
      this.sub.unsubscribe();
  }

  onContentDrop(file: File) {
    this.fileDrop('content', file);
  }

  onThumbnailDrop(file: File) {
    this.fileDrop('thumbnail', file)
  }

  fileDrop(name: string, file: File) {
    const url = URL.createObjectURL(file);
    const safeUrl = this.domSanitizer.bypassSecurityTrustUrl(url);

    if (name == 'content') {
      this.content = file;
      this.typeOfContent = file.type.split('/')[0];
      this.contentUrl = safeUrl;
    } else if (name == 'thumbnail') {
      this.thumbnail = file;
      this.thumbnailUrl = safeUrl;
    }

    this.uploadForm.patchValue({[name]: file});
    this.uploadForm.get(name)?.updateValueAndValidity();
  }

}
