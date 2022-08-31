import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { catchError, concatMap, EMPTY, forkJoin, tap } from 'rxjs';
import { Group } from 'src/app/data/group.model';
import { InfoStatus } from 'src/app/data/info-status.enum copy';
import { ManipulatePublication } from 'src/app/data/manipulate-publication.model';
import { Publication } from 'src/app/data/publication.model';
import { ShortTag } from 'src/app/data/short-tag.model';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group.service';
import { PublicationService } from 'src/app/services/publication.service';
import { TagService } from 'src/app/services/tag.service';
import { InfoComponent } from '../../auxiliary/info/info.component';

@Component({
  selector: 'app-create-publication',
  templateUrl: './create-publication.component.html',
  styleUrls: ['./create-publication.component.css']
})
export class CreatePublicationComponent implements OnInit {
  image: string | ArrayBuffer | null;
  publication: Publication;
  fileToUpload: File | null = null;
  groups: Group[];
  tags: ShortTag[];

  constructor(
    private publicationService: PublicationService,
    private groupService: GroupService,
    private tagService: TagService,
    private modalService: NgbModal,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    public authService: AuthService
  ) { }

  addEditForm = this.formBuilder.group({
    id: null,
    title: [null, [Validators.required, Validators.maxLength(128)]],
    subtitle: [null, [Validators.required, Validators.maxLength(256)]],
    image:  null,
    groups: [null, Validators.required],
    tags: [null, Validators.required]
  });

  publication$ = this.route.queryParams.pipe(
    concatMap(params => {
        return forkJoin([
            this.publicationService.getById(params['id']),
            this.groupService.getAllValidGroups(),
            this.tagService.getAllValidTags()
        ]).pipe(
            tap(([publication, groups, tags]) => {
                this.htmlContent = publication.content;
                this.image = publication.image;
                this.groups = groups;
                this.tags = tags;
                this.addEditForm.patchValue(publication);
                
                console.log(publication)
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

  htmlContent = '';

  config: AngularEditorConfig = {
    sanitize: false,
    editable: true,
    spellcheck: true,
    height: '500pt',
    minHeight: '5rem',
    placeholder: 'Въведи...',
    translate: 'no',
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
    defaultFontSize: '3',
    toolbarHiddenButtons: [],
    customClasses: [
      {
        name: "quote",
        class: "quote",
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: "titleText",
        class: "titleText",
        tag: "h1",
      },
    ]
  };

  ngOnInit(): void {
  }

  public onImageChange(file: File | undefined) {
    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);

      reader.onload = () => {
        this.image = reader.result;
      };
    }
  }

  save() {
    this.addEditForm.get('image')?.patchValue(this.image);
    if (this.addEditForm.valid && this.htmlContent.length > 0) {
    
      const newPub = new ManipulatePublication(this.addEditForm.get('title')?.value, this.addEditForm.get('subtitle')?.value, 
                                              this.htmlContent, this.image?.toString(), this.addEditForm.get('groups')?.value, this.addEditForm?.get('tags')?.value);

                                   
      if(this.addEditForm.get('id')?.value != null) {
        this.publicationService.updatePublication(this.addEditForm.get('id')?.value, newPub).subscribe(() => {
          const modalRef = this.modalService.open(InfoComponent);
          modalRef.componentInstance.message = InfoStatus.CREATE_PUB;

          this.router.navigate(['/home']);
        });
      } else {
        this.publicationService.createPublication(newPub).subscribe(() => {
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
