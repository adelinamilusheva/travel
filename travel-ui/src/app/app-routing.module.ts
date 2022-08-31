import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateGroupComponent } from './components/admin/create-group/create-group.component';
import { CreatePublicationComponent } from './components/admin/create-publication/create-publication.component';
import { CreateTagComponent } from './components/admin/create-tag/create-tag.component';
import { GroupsStatisticsComponent } from './components/admin/groups-statistics/groups-statistics.component';
import { ListGroupsComponent } from './components/admin/list-groups/list-groups.component';
import { ListPublicationsComponent } from './components/admin/list-publications/list-publications.component';
import { ListStatisticsComponent } from './components/admin/list-statistics/list-statistics.component';
import { ListTagsComponent } from './components/admin/list-tags/list-tags.component';
import { PublicationsStatisticsComponent } from './components/admin/publications-statistics/publications-statistics.component';
import { TagsStatisticsComponent } from './components/admin/tags-statistics/tags-statistics.component';
import { PageNotFoundComponent } from './components/auxiliary/page-not-found/page-not-found.component';
import { GroupComponent } from './components/group/group.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { PublicationComponent } from './components/publication/publication.component';
import { TagComponent } from './components/tag/tag.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'groups/:id', component: GroupComponent },
  { path: 'tags/:id', component: TagComponent },
  { path: 'publications/:id', component: PublicationComponent },

  { path: 'admin/create-publication', component: CreatePublicationComponent },
  { path: 'admin/create-group', component: CreateGroupComponent },
  { path: 'admin/create-tag', component: CreateTagComponent },
  { path: 'admin/list-publications', component: ListPublicationsComponent },
  { path: 'admin/list-groups', component: ListGroupsComponent },
  { path: 'admin/list-tags', component: ListTagsComponent },
  { path: 'admin/list-statistics', component: ListStatisticsComponent },
  { path: 'admin/tags-statistics', component:  TagsStatisticsComponent},
  { path: 'admin/groups-statistics', component:  GroupsStatisticsComponent},
  { path: 'admin/publications-statistics', component:  PublicationsStatisticsComponent},
  
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }