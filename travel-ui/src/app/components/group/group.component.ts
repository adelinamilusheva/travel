import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AbstractPagination } from 'src/app/abstract/abstract-pagination';
import { Group } from 'src/app/data/group.model';
import { ShortPublication } from 'src/app/data/short-publication.model';
import { SortingPagingData } from 'src/app/data/sorting-paging-data';
import { GroupService } from 'src/app/services/group.service';
import { PublicationService } from 'src/app/services/publication.service';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent extends AbstractPagination implements OnInit {
  group: Group;
  publications: ShortPublication[];

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
    private publicationService: PublicationService
  ) {
    super();
  }

  ngOnInit() {
    this.sortingPaging = new SortingPagingData();
    this.isPageLoading = false;
    this.route.params.subscribe(
      param => {
        const id = param['id']
        this.loadData(id)
      }
    );
  }

  loadData(id: number) {
    this.groupService.getById(id).subscribe(response => {
      this.group = response;
    });

    this.publicationService.getAllPublicationsByGroupPaged(id, this.sortingPaging).subscribe(response => {
      this.publications = response.results;
      this.sortingPaging.pageNumber = response.pageIndex + 1;
      this.sortingPaging.pageSize = response.pageSize;
      this.sortingPaging.totalElements = response.totalElements;

      this.sortingPaging.calcFromToRow(response.results.length);
      
      this.isPageLoading = false;
    });
  }
}
