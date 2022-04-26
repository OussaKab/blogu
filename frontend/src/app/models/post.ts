export interface Post {
  id: number;
  title: string;
  description: string;
  views: number;
  likes: number;
  dislikes: number;
  createdDate: Date;
  createdBy: string;
}
