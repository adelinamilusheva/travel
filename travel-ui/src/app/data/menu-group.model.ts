import { MenuSubGroup } from "./menu-subgroup.model"

export class MenuGroup {
    id: number;
    name: string;
    isActive: boolean;
    subgroups: MenuSubGroup[];
}