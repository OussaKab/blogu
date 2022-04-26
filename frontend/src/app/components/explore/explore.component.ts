import {Component, OnInit} from '@angular/core';
import {FileService} from "../../services/file.service";
import {PreviewPost} from "../../models/preview-post";
import Swal from "sweetalert2";

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css']
})
export class ExploreComponent implements OnInit {

  files: PreviewPost[] = [];

  constructor(private fileService: FileService) {
  }

  ngOnInit(): void {
    this.fileService.getAll().subscribe({
      next: files => this.files = files,
      error: err => {
        Swal.fire({
          title: 'Error',
          text: err?.error?.message,
          icon: 'error'
        });
      }
    });
  }

}
