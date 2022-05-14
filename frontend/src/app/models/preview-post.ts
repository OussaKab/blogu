export interface PreviewPost {
  id: number;
  title: string;
  mimeType: string;
  thumbnail: string;
  createdAt: Date;
  views: number;
  createdBy: string;
  moderationReason: string;
}
