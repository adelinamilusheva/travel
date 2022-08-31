import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, defaultIfEmpty, EMPTY, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';
import { Group } from '../data/group.model';
import { ListGroup } from '../data/list-group.model';
import { LitePage } from '../data/lite-page.model';
import { MenuGroup } from '../data/menu-group.model';
import { ShortGroup } from '../data/short-group.model';
import { SortingPagingData } from '../data/sorting-paging-data';

const URL_GROUPS = environment.apiUrl + '/groups';
const URL_MENU_GROUPS = URL_GROUPS + '/menu';
const URL_ALL_GROUPS = URL_GROUPS + '/all';
const URL_ALL_VALID_GROUPS = URL_GROUPS + '/all-valid';
const URL_CREATE_GROUP = URL_GROUPS + '/create';
const URL_UPDATE_GROUP = URL_GROUPS + '/update';
const URL_ALL_SHORT_GROUPS = URL_GROUPS + '/all-short';
const URL_UPDATE_STATUS = URL_GROUPS + '/status';
const URL_ALL_GROUPS_PAGING = URL_GROUPS + '/paging';

@Injectable({
  providedIn: 'root'
})
export class GroupService extends AbstractService {

  constructor(private http: HttpClient) {
    super();
  }

  getById(id: number): Observable<Group> {
    if (id === undefined || id === null) {
      return EMPTY.pipe(
        defaultIfEmpty(new Group())
      );
    }
    
    return this.http.get<Group>(`${URL_GROUPS}/${id}`);
  }

  getMenuGroups(): Observable<MenuGroup[]> {
    return this.http.get<MenuGroup[]>(URL_MENU_GROUPS);
  }

  getAllGroups(): Observable<ListGroup[]> {
    return this.http.get<ListGroup[]>(URL_ALL_GROUPS);
  }

  getAllShortGroups(): Observable<ShortGroup[]> {
    return this.http.get<ShortGroup[]>(URL_ALL_SHORT_GROUPS);
  }

  getAllValidGroups(): Observable<Group[]> {
    return this.http.get<Group[]>(URL_ALL_VALID_GROUPS);
  }

  getAllGroupsPaged(sortingPaging: SortingPagingData): Observable<LitePage<ListGroup>> {
    let requestParams = new HttpParams();
    requestParams = requestParams.set("size", sortingPaging.pageSize);
    requestParams = requestParams.set("page", sortingPaging.pageNumber - 1);
    if (sortingPaging.sortBy && sortingPaging.sortAsc != null) {
      requestParams = requestParams.set("sortBy", sortingPaging.sortBy);
      requestParams = requestParams.set("sortDirection", sortingPaging.getDirection());
    }

    return this.http.get<LitePage<ListGroup>>(URL_ALL_GROUPS_PAGING, {params: requestParams});
  }

  createGroup(group: Group): Observable<any> {
    return this.http.post(URL_CREATE_GROUP, group, this.httpOptions).pipe(
      catchError(this.handleError<any>('createGroup'))
    );
  }

  updateGroup(group: Group): Observable<any> {
    return this.http.put(`${URL_UPDATE_GROUP}/${group.id}`, group, this.httpOptions);
  }

  updateIsActiveGroup(id: number, isActive: boolean): Observable<any> {
    return this.http.patch(`${URL_UPDATE_STATUS}/${id}/${isActive}`, this.httpOptions);
  }
}
