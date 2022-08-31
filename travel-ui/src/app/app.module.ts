import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { DatePipe } from '@angular/common';
import { HomeComponent } from './components/home/home.component';
import { ErrorComponent } from './components/auxiliary/error/error.component';
import { PageNotFoundComponent } from './components/auxiliary/page-not-found/page-not-found.component';
import { GlobalHttpInterceptorService } from './services/global-http-interceptor.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './components/menu/header/header.component';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbAccordionModule } from 'mdb-angular-ui-kit/accordion';
import { MdbCarouselModule } from 'mdb-angular-ui-kit/carousel';
import { MdbCheckboxModule } from 'mdb-angular-ui-kit/checkbox';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { MdbDropdownModule } from 'mdb-angular-ui-kit/dropdown';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { MdbPopoverModule } from 'mdb-angular-ui-kit/popover';
import { MdbRadioModule } from 'mdb-angular-ui-kit/radio';
import { MdbRangeModule } from 'mdb-angular-ui-kit/range';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbScrollspyModule } from 'mdb-angular-ui-kit/scrollspy';
import { MdbTabsModule } from 'mdb-angular-ui-kit/tabs';
import { MdbTooltipModule } from 'mdb-angular-ui-kit/tooltip';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';
import { NgChartsModule } from 'ng2-charts';
import { ScrollToTopComponent } from './components/auxiliary/scroll-to-top/scroll-to-top.component';
import { InfoComponent } from './components/auxiliary/info/info.component';
import { PublicationComponent } from './components/publication/publication.component';
import { GroupComponent } from './components/group/group.component';
import { TagComponent } from './components/tag/tag.component';
import { CreatePublicationComponent } from './components/admin/create-publication/create-publication.component';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { CreateTagComponent } from './components/admin/create-tag/create-tag.component';
import { CreateGroupComponent } from './components/admin/create-group/create-group.component';
import { ListGroupsComponent } from './components/admin/list-groups/list-groups.component';
import { ListPublicationsComponent } from './components/admin/list-publications/list-publications.component';
import { ListTagsComponent } from './components/admin/list-tags/list-tags.component';
import { ListStatisticsComponent } from './components/admin/list-statistics/list-statistics.component';
import { TagsStatisticsComponent } from './components/admin/tags-statistics/tags-statistics.component';
import { GroupsStatisticsComponent } from './components/admin/groups-statistics/groups-statistics.component';
import { PublicationsStatisticsComponent } from './components/admin/publications-statistics/publications-statistics.component';
import { AddEditModalComponent } from './components/auxiliary/add-edit-modal/add-edit-modal.component';
import { LoginComponent } from './components/login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ErrorComponent,
    PageNotFoundComponent,
    HeaderComponent,
    ScrollToTopComponent,
    InfoComponent,
    PublicationComponent,
    GroupComponent,
    TagComponent,
    CreatePublicationComponent,
    CreateTagComponent,
    CreateGroupComponent,
    ListGroupsComponent,
    ListPublicationsComponent,
    ListTagsComponent,
    ListStatisticsComponent,
    TagsStatisticsComponent,
    GroupsStatisticsComponent,
    PublicationsStatisticsComponent,
    AddEditModalComponent,
    LoginComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AngularEditorModule,
    NgChartsModule,
    
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [HttpClient],
      },
      defaultLanguage: 'bg'
  }),
    
    NgbModule,
    
    MdbAccordionModule,
    
    MdbCarouselModule,
    
    MdbCheckboxModule,
    
    MdbCollapseModule,

    MdbFormsModule,
    
    MdbDropdownModule,
    
    MdbModalModule,
    
    MdbPopoverModule,
    
    MdbRadioModule,
    
    MdbRangeModule,
    
    MdbRippleModule,
    
    MdbScrollspyModule,
    
    MdbTabsModule,
    
    MdbTooltipModule,
    
    MdbValidationModule,
    
    NoopAnimationsModule
  ],
  providers: [
    DatePipe,
    { provide: HTTP_INTERCEPTORS, useClass: GlobalHttpInterceptorService, multi: true  }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}