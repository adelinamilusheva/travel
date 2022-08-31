import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, defaultIfEmpty, EMPTY, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';
import { ListTag } from '../data/list-tag.model';
import { LitePage } from '../data/lite-page.model';
import { MenuTag } from '../data/menu-tag.model';
import { ShortTag } from '../data/short-tag.model';
import { SortingPagingData } from '../data/sorting-paging-data';

const URL_TAGS = environment.apiUrl + '/tags';

const URL_CREATE_TAG = URL_TAGS + '/create';
const URL_UPDATE_TAG = URL_TAGS + '/update';
const URL_ALL_TAGS = URL_TAGS + '/all';
const URL_ALL_VALID_TAGS = URL_TAGS + '/all-valid';
const URL_UPDATE_STATUS = URL_TAGS + '/status';
const URL_ALL_TAGS_PAGING = URL_TAGS + '/paging';

@Injectable({
  providedIn: 'root'
})
export class TagService extends AbstractService {

  constructor(private http: HttpClient) {
    super();
  }

  getById(id: number): Observable<MenuTag> {
    if (id === undefined || id === null) {
      return EMPTY.pipe(
        defaultIfEmpty(new MenuTag())
      );
    }
    
    return this.http.get<MenuTag>(`${URL_TAGS}/${id}`);
  }

  getAllTags(): Observable<ListTag[]> {
    return this.http.get<ListTag[]>(URL_ALL_TAGS);
  }

  getAllValidTags(): Observable<ShortTag[]> {
    return this.http.get<ShortTag[]>(URL_ALL_VALID_TAGS);
  }

  getAllTagsPaged(sortingPaging: SortingPagingData): Observable<LitePage<ListTag>> {
    let requestParams = new HttpParams();
    requestParams = requestParams.set("size", sortingPaging.pageSize);
    requestParams = requestParams.set("page", sortingPaging.pageNumber - 1);
    if (sortingPaging.sortBy && sortingPaging.sortAsc != null) {
      requestParams = requestParams.set("sortBy", sortingPaging.sortBy);
      requestParams = requestParams.set("sortDirection", sortingPaging.getDirection());
    }

    return this.http.get<LitePage<ListTag>>(URL_ALL_TAGS_PAGING, {params: requestParams});
  }

  createTag(tag: ShortTag): Observable<any> {
    return this.http.post(URL_CREATE_TAG, tag, this.httpOptions).pipe(
      catchError(this.handleError<any>('createTag'))
    );
  }

  updateTag(tag: ShortTag): Observable<any> {
    return this.http.put(`${URL_UPDATE_TAG}/${tag.id}`, tag, this.httpOptions);
  }

  updateIsActiveTag(id: number, isActive: boolean): Observable<any> {
    return this.http.patch(`${URL_UPDATE_STATUS}/${id}/${isActive}`, this.httpOptions);
  }
}
