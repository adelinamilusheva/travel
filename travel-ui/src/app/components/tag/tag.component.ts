import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AbstractPagination } from 'src/app/abstract/abstract-pagination';
import { ShortPublication } from 'src/app/data/short-publication.model';
import { ShortTag } from 'src/app/data/short-tag.model';
import { SortingPagingData } from 'src/app/data/sorting-paging-data';
import { PublicationService } from 'src/app/services/publication.service';
import { TagService } from 'src/app/services/tag.service';

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.css']
})
export class TagComponent extends AbstractPagination implements OnInit {
  tag: ShortTag;
  publications: ShortPublication[];

  constructor(
    private route: ActivatedRoute,
    private tagService: TagService,
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
    this.tagService.getById(id).subscribe(response => {
      this.tag = response;
    });

    this.publicationService.getAllPublicationsByTagPaged(id, this.sortingPaging).subscribe(response => {
      this.publications = response.results;
      this.sortingPaging.pageNumber = response.pageIndex + 1;
      this.sortingPaging.pageSize = response.pageSize;
      this.sortingPaging.totalElements = response.totalElements;

      this.sortingPaging.calcFromToRow(response.results.length);
      
      this.isPageLoading = false;
    });
  }
}
