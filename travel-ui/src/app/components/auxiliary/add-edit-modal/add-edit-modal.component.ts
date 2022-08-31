import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommentService } from 'src/app/services/comment.service';
import { Comment } from 'src/app/data/comment.model'

@Component({
  selector: 'app-add-edit-modal',
  templateUrl: './add-edit-modal.component.html',
  styleUrls: ['./add-edit-modal.component.css']
})
export class AddEditModalComponent implements OnInit {
  @Input() id: number;
  @Input() content: string;
  @Input() ipAddress: string;
  @Input() publicationId: number;

  constructor(
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private commentService: CommentService
  ) {}
  
  ngOnInit(): void {
    if(this.id) {
      this.addEditForm.get('content')?.patchValue(this.content);
    }
  }
  
  addEditForm = this.formBuilder.group({
    content: [null, [Validators.required, Validators.maxLength(4096)]]
  });

  save() {
    if (this.addEditForm.valid) {
    
      const newComment = new Comment(this.id, this.addEditForm.get('content')?.value, this.ipAddress, this.publicationId);

      if(this.id != null) {
        this.commentService.updateComment(this.id, newComment).subscribe(() => {
          this.activeModal.close();
        });
      } else {
        this.commentService.createComment(newComment).subscribe(() => {
          this.activeModal.close();
        });
      }
    }
  }

  isInvalid(property: string) {
    const field = this.addEditForm.get(property)
    return field?.invalid && (field?.dirty || field?.touched)
  }
}
