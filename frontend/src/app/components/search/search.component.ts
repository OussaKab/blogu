import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FileService} from "../../services/file.service";
import {PreviewPost} from "../../models/preview-post";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  search: string = '';
  posts: PreviewPost[] = [];

  constructor(private searchService: FileService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.search = this.route.snapshot.params['query'];
    this.searchService.search(this.search).subscribe(posts => {
      this.posts = posts;
    });
  }
}
