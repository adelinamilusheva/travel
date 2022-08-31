import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, defaultIfEmpty, EMPTY, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';
import { ListPublication } from '../data/list-publication.model';
import { LitePage } from '../data/lite-page.model';
import { ManipulatePublication } from '../data/manipulate-publication.model';
import { Publication } from '../data/publication.model';
import { ReadingStats } from '../data/reading-stats.model';
import { ShortPublication } from '../data/short-publication.model';
import { SortingPagingData } from '../data/sorting-paging-data';

const URL_PUBLICATIONS = environment.apiUrl + '/publications';
const URL_LATEST_PUBLICATIONS = URL_PUBLICATIONS + '/latest';
const URL_SIMILAR_PUBLICATIONS =  '/similar';
const URL_PUBLICATION_READINGS = '/readings';
const URL_ALL_PUBLICATIONS = URL_PUBLICATIONS + '/all';
const URL_ALL_PUBLICATIONS_PAGING = URL_PUBLICATIONS + '/paging';
const URL_GROUP_PUBLICATIONS = URL_PUBLICATIONS + '/group';
const URL_TAG_PUBLICATIONS = URL_PUBLICATIONS + '/tag';

const URL_CREATE_PUBLICATION = URL_PUBLICATIONS + '/create';
const URL_UPDATE_PUBLICATION = URL_PUBLICATIONS + '/update';
const URL_ADD_READING = '/add-reading';

const URL_UPDATE_STATUS = URL_PUBLICATIONS + '/status';

@Injectable({
  providedIn: 'root'
})
export class PublicationService extends AbstractService {

  constructor(private http: HttpClient) {
    super();
  }

  getById(id: number): Observable<Publication> {
    if (id === undefined || id === null) {
      return EMPTY.pipe(
        defaultIfEmpty(new Publication())
      );
    }
    
    return this.http.get<Publication>(`${URL_PUBLICATIONS}/${id}`);
  }

  getLatest(): Observable<ShortPublication[]> {
    return this.http.get<ShortPublication[]>(URL_LATEST_PUBLICATIONS);
  }

  getSimilar(id: number): Observable<ShortPublication[]> {
    return this.http.get<ShortPublication[]>(`${URL_PUBLICATIONS}/${id}${URL_SIMILAR_PUBLICATIONS}`);
  }

  getReadings(id: number): Observable<ReadingStats> {
    return this.http.get<ReadingStats>(`${URL_PUBLICATIONS}/${id}${URL_PUBLICATION_READINGS}`);
  }

  getByGroup(groupId: number): Observable<ShortPublication[]> {
    return this.http.get<ShortPublication[]>(`${URL_GROUP_PUBLICATIONS}/${groupId}`);
  }

  getAllPublicationsByGroupPaged(groupId: number, sortingPaging: SortingPagingData): Observable<LitePage<ShortPublication>> {
    let requestParams = new HttpParams();
    requestParams = requestParams.set("size", sortingPaging.pageSize);
    requestParams = requestParams.set("page", sortingPaging.pageNumber - 1);
    if (sortingPaging.sortBy && sortingPaging.sortAsc != null) {
      requestParams = requestParams.set("sortBy", sortingPaging.sortBy);
      requestParams = requestParams.set("sortDirection", sortingPaging.getDirection());
    }

    return this.http.get<LitePage<ShortPublication>>(`${URL_GROUP_PUBLICATIONS}/${groupId}/paging`, {params: requestParams});
  }

  getByTag(tagId: number): Observable<ShortPublication[]> {
    return this.http.get<ShortPublication[]>(`${URL_TAG_PUBLICATIONS}/${tagId}`);
  }

  getAllPublicationsByTagPaged(tagId: number, sortingPaging: SortingPagingData): Observable<LitePage<ShortPublication>> {
    let requestParams = new HttpParams();
    requestParams = requestParams.set("size", sortingPaging.pageSize);
    requestParams = requestParams.set("page", sortingPaging.pageNumber - 1);
    if (sortingPaging.sortBy && sortingPaging.sortAsc != null) {
      requestParams = requestParams.set("sortBy", sortingPaging.sortBy);
      requestParams = requestParams.set("sortDirection", sortingPaging.getDirection());
    }

    return this.http.get<LitePage<ShortPublication>>(`${URL_TAG_PUBLICATIONS}/${tagId}/paging`, {params: requestParams});
  }

  getAllPublications(): Observable<ListPublication[]> {
    return this.http.get<ListPublication[]>(URL_ALL_PUBLICATIONS);
  }

  getAllPublicationsPaged(sortingPaging: SortingPagingData): Observable<LitePage<ListPublication>> {
    let requestParams = new HttpParams();
    requestParams = requestParams.set("size", sortingPaging.pageSize);
    requestParams = requestParams.set("page", sortingPaging.pageNumber - 1);
    if (sortingPaging.sortBy && sortingPaging.sortAsc != null) {
      requestParams = requestParams.set("sortBy", sortingPaging.sortBy);
      requestParams = requestParams.set("sortDirection", sortingPaging.getDirection());
    }

    return this.http.get<LitePage<ListPublication>>(URL_ALL_PUBLICATIONS_PAGING, {params: requestParams});
  }

  addReading(id: number, ip: string): Observable<any> {
    return this.http.post(`${URL_PUBLICATIONS}/${id}${URL_ADD_READING}`, ip, this.httpOptions).pipe(
      catchError(this.handleError<any>('addReading'))
    );
  }

  createPublication(publication: ManipulatePublication): Observable<any> {
    return this.http.post(URL_CREATE_PUBLICATION, publication, this.httpOptions).pipe(
      catchError(this.handleError<any>('createPublication'))
    );
  }

  updatePublication(id: number, publication: ManipulatePublication): Observable<any> {
    return this.http.put(`${URL_UPDATE_PUBLICATION}/${id}`, publication, this.httpOptions);
  }

  updateIsActivePublication(id: number, isActive: boolean): Observable<any> {
    return this.http.patch(`${URL_UPDATE_STATUS}/${id}/${isActive}`, this.httpOptions);
  }
}
