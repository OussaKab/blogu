import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FileService} from "../../services/file.service";
import {PreviewPost} from "../../models/preview-post";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit, OnDestroy {

  search: string = '';
  posts: PreviewPost[] = [];
  private sub: Subscription | undefined;

  constructor(private searchService: FileService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.search = this.route.snapshot.params['query'];
    this.sub = this.searchService.search(this.search).subscribe(posts => this.posts = posts);
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }
}
