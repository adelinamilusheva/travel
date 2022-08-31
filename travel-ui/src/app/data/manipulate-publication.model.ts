import { Group } from "./group.model"
import { ShortTag } from "./short-tag.model"

export class ManipulatePublication {
    title: string
    subtitle?: string
    content: string
    image: string | undefined

    groups: Group[]
    tags: ShortTag[]
    

    constructor(title: string, subtitle: string | undefined, content: string, image: string | undefined, groups: Group[], tags: ShortTag[]) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.image = image;
        this.groups = groups;
        this.tags = tags;
    }
}