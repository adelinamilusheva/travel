import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GroupService } from 'src/app/services/group.service';
import { InfoStatus } from 'src/app/data/info-status.enum copy';
import { InfoComponent } from '../../auxiliary/info/info.component';
import { catchError, concatMap, EMPTY, forkJoin, tap } from 'rxjs';
import { Group } from 'src/app/data/group.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css']
})
export class CreateGroupComponent implements OnInit {
  allGroups: Group[];

  constructor(
    private groupService: GroupService,
    private modalService: NgbModal,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    public authService: AuthService
    ) { }

  ngOnInit(): void {
  }

  addEditForm = this.formBuilder.group({
    id: null,
    name: [null, [Validators.required, Validators.maxLength(64)]],
    description: [null, [Validators.required, Validators.maxLength(256)]],
    parent: null
  });

  group$ = this.route.queryParams.pipe(
    concatMap(params => {
        return forkJoin([
            this.groupService.getById(params['id']),
            this.groupService.getAllValidGroups()
        ]).pipe(
            tap(([group, allGroups]) => {
              this.allGroups = allGroups;
              this.addEditForm.patchValue(group);
            }),
            catchError(err => {
              console.log(err)
              return EMPTY;
            })
        );
      })
    );

  isInvalid(property: string) {
    const field = this.addEditForm.get(property)
    return field?.invalid && (field?.dirty || field?.touched)
  }

  save() {
      if (this.addEditForm.valid) {
        if(this.addEditForm.get('id')?.value != null) {

          this.groupService.updateGroup(this.addEditForm.value).subscribe(() => {
            const modalRef = this.modalService.open(InfoComponent);
            modalRef.componentInstance.message = InfoStatus.CREATE_PUB;
    
            this.router.navigate(['/home']);
          });
        } else {
          this.groupService.createGroup(this.addEditForm.value).subscribe(() => {
            const modalRef = this.modalService.open(InfoComponent);
            modalRef.componentInstance.message = InfoStatus.CREATE_PUB;
      
            this.router.navigate(['/home']);
          });
        }
      }
  }

  compareById(optionOne: any, optionTwo: any): boolean {
    return optionOne && optionTwo && optionOne.id === optionTwo.id;
  }
}
