export class Comment {
    id: number;
    content: string;
    ipAddress: string;
    publicationId: number;

    constructor(id: number, content: string, ipAddress: string, publicationId: number) {
        this.id = id;
        this.content = content;
        this.ipAddress = ipAddress;
        this.publicationId = publicationId;
    }
}