import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TagService } from 'src/app/services/tag.service';
import { InfoStatus } from 'src/app/data/info-status.enum copy';
import { InfoComponent } from '../../auxiliary/info/info.component';
import { catchError, concatMap, EMPTY, forkJoin, tap } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-create-tag',
  templateUrl: './create-tag.component.html',
  styleUrls: ['./create-tag.component.css']
})
export class CreateTagComponent implements OnInit {

  constructor(
    private tagService: TagService,
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
    description: [null, [Validators.required, Validators.maxLength(256)]]
  });

  tag$ = this.route.queryParams.pipe(
    concatMap(params => {
        return forkJoin([
            this.tagService.getById(params['id'])
        ]).pipe(
            tap(([tag]) => {
              console.log(tag)
                this.addEditForm.patchValue(tag);
                console.log(this.addEditForm.get('id')?.value)
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
        this.tagService.updateTag(this.addEditForm.value).subscribe(() => {
          const modalRef = this.modalService.open(InfoComponent);
          modalRef.componentInstance.message = InfoStatus.CREATE_PUB;
  
          this.router.navigate(['/home']);
        });
      } else {
        this.tagService.createTag(this.addEditForm.value).subscribe(() => {
          const modalRef = this.modalService.open(InfoComponent);
          modalRef.componentInstance.message = InfoStatus.CREATE_PUB;
  
          this.router.navigate(['/home']);
        });
      }
    }
     
  }

}
