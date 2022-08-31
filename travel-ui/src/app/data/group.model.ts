import { MenuSubGroup } from "./menu-subgroup.model"

export class Group {
    id: number;
    name: string;
    description?: string;
    parent?: Group;
    subgroups: MenuSubGroup[];
}