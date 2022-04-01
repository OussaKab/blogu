export interface Post{
    id: number;
    title: string;
    description: string;
    views: number;
    //TODO: exercice
    likes: number;
    dislikes: number;
    createdDate: Date;
    createdBy: string;
}
