import {Component, OnDestroy, OnInit} from '@angular/core';
import {PreviewPost} from "../../models/preview-post";
import {FileService} from "../../services/file.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-moderations',
  templateUrl: './moderations.component.html',
  styleUrls: ['./moderations.component.css']
})
export class ModerationsComponent implements OnInit, OnDestroy {
  moderations: PreviewPost[] = [];
  role: string = 'ROLE_MODERATOR';
  subs: Subscription[] = [];

  constructor(
    private postService: FileService,
    private auth: AuthService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.role = this.auth.getRole() || '';

    if (this.role && this.role !== 'ROLE_MODERATOR')
      this.router.navigateByUrl('/explore')
    const username = this.auth.getUsername() || '';
    this.subs.push(this.postService.getAllModerateForUser(username).subscribe({
      next: posts => this.moderations = posts,
      error: err => console.log(err),
      complete: () => console.log('completed')
    }));
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe());
  }
}
