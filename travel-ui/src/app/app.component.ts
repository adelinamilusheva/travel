import { Component, OnInit } from '@angular/core';
import { MenuGroup } from './data/menu-group.model';
import { GroupService } from 'src/app/services/group.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NotificationService } from './services/notification.service';
import { InfoComponent } from './components/auxiliary/info/info.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InfoStatus } from './data/info-status.enum copy';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  groups: MenuGroup[];
  themeClass: string;

  title = 'travel';

  validatingForm: FormGroup;
  email: string;

  constructor(
    private groupService: GroupService,
    private notificationService: NotificationService,
    private modalService: NgbModal,
    public translateService: TranslateService,
    public authService: AuthService
  ) {

    this.validatingForm = new FormGroup({
      email: new FormControl(null, Validators.email)
    });
   }

  ngOnInit(): void {
    if(localStorage.getItem('lang')) {
      this.translateService.use(localStorage.getItem('lang') || 'bg');
    }

    this.themeClass = localStorage.getItem('theme') ? localStorage.getItem('theme')! : 'default';
    
    this.email = '';
    this.loadData();
  }

  logout() {
    this.authService.logout();
  }

  changeTheme(theme: string) {
    this.themeClass = theme;
    localStorage.setItem('theme', theme);
  }

  changeLanguage(lang: string) {
    this.translateService.use(lang);
    localStorage.setItem('lang', lang);
  }

  subscribe() {
    if (this.validatingForm.valid) {
      this.notificationService.subscribe(this.email).subscribe(() => {
        this.email = '';

        const modalRef = this.modalService.open(InfoComponent);
        modalRef.componentInstance.message = InfoStatus.SUBSCRIBE;
      });
    }
  }

  get input() { return this.validatingForm.get('email'); }

  private loadData() {
    this.groupService.getMenuGroups()
      .subscribe(groups => {
          this.groups = groups
        }
      );
  }
}
