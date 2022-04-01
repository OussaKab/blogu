import {PreviewPost} from "./preview-post";

export interface Profile{
  username:string;
  createdAt: Date;
  posts: PreviewPost[];
  totalViews: number;
}
