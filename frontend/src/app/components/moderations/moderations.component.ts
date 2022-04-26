import {Component, OnInit} from '@angular/core';
import {PreviewPost} from "../../models/preview-post";
import {FileService} from "../../services/file.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-moderations',
  templateUrl: './moderations.component.html',
  styleUrls: ['./moderations.component.css']
})
export class ModerationsComponent implements OnInit {
  moderations: PreviewPost[] = [];

  constructor(private postService: FileService, private auth: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    let role: string = this.auth.getRole() || '';

    if (role && role !== 'ROLE_MODERATOR')
      this.router.navigateByUrl('/explore')


    this.postService.getAllModerateForUser().subscribe({
      next: posts => {
        this.moderations = posts;
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log('completed');
      }
    });
  }

}
