import { MenuGroup } from "./menu-group.model"
import { MenuTag } from "./menu-tag.model"

export class Publication {
    id: number
    title: string
    subtitle?: string
    content: string
    image: string
    publishedAt: string
    createdBy: string
    city: string;

    groups: MenuGroup[]
    tags: MenuTag[]
}