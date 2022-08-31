import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';
import { StatisticsGroup } from '../data/statistics-group.model';
import { StatisticsPublication } from '../data/statistics-publication.model';
import { StatisticsTag } from '../data/statistics-tag.model';

const URL_STATISTICS = environment.apiUrl + '/statistics';

const URL_STATISTICS_TAGS = URL_STATISTICS + '/tags';
const URL_STATISTICS_PUBLICATIONS = URL_STATISTICS + '/publications';
const URL_STATISTICS_GROUPS = URL_STATISTICS + '/groups';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService extends AbstractService {

  constructor(private http: HttpClient) {
    super();
  }

  getStatisticsTags(): Observable<StatisticsTag> {
    return this.http.get<StatisticsTag>(URL_STATISTICS_TAGS);
  }

  getStatisticsPublications(): Observable<StatisticsPublication> {
    return this.http.get<StatisticsPublication>(URL_STATISTICS_PUBLICATIONS);
  }

  getStatisticsGroups(): Observable<StatisticsGroup> {
    return this.http.get<StatisticsGroup>(URL_STATISTICS_GROUPS);
  }
}
