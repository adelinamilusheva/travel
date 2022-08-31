export class Rating {
    value: number;
    ipAddress: string;
    publicationId: number;

    constructor(value: number, ipAddress: string, publicationId: number) {
        this.value = value;
        this.ipAddress = ipAddress;
        this.publicationId = publicationId;
    }
}