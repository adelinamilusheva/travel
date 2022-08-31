import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';
import { ReadComment } from '../data/read-comment.model';
import { Comment } from '../data/comment.model';

const URL_COMMENTS = environment.apiUrl + '/comments';
const URL_CREATE_COMMENT = URL_COMMENTS + '/create';
const URL_UPDATE_COMMENT = URL_COMMENTS + '/update';

@Injectable({
  providedIn: 'root'
})
export class CommentService extends AbstractService {

  constructor(private http: HttpClient) {
    super();
  }

  getAllActiveByPublication(publicationId: number): Observable<ReadComment[]> {
    return this.http.get<ReadComment[]>(`${URL_COMMENTS}/${publicationId}`);
  }

  createComment(comment: Comment): Observable<any> {
    return this.http.post(URL_CREATE_COMMENT, comment, this.httpOptions).pipe(
      catchError(this.handleError<any>('createComment'))
    );
  }

  updateComment(id: number, comment: Comment): Observable<any> {
    return this.http.put(`${URL_UPDATE_COMMENT}/${id}`, comment, this.httpOptions);
  }

  deleteComment(id: number): Observable<any> {
    return this.http.delete(`${URL_COMMENTS}/${id}`, this.httpOptions);
  }
}
