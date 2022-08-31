import { Component, OnInit } from '@angular/core';
import { AbstractPagination } from 'src/app/abstract/abstract-pagination';
import { ListTag } from 'src/app/data/list-tag.model';
import { SortingPagingData } from 'src/app/data/sorting-paging-data';
import { AuthService } from 'src/app/services/auth.service';
import { TagService } from 'src/app/services/tag.service';

@Component({
  selector: 'app-list-tags',
  templateUrl: './list-tags.component.html',
  styleUrls: ['./list-tags.component.css']
})
export class ListTagsComponent extends AbstractPagination implements OnInit {
  tags: ListTag[];

  constructor(
    private tagService: TagService,
    public authService: AuthService
    ) {
    super();
  }

  ngOnInit(): void {
    this.sortingPaging = new SortingPagingData();
    this.isPageLoading = false;
    this.loadData();
  }

  changeTagStatus(id: number, status: boolean) {
    this.tagService.updateIsActiveTag(id, status).subscribe(() => window.location.reload());  
  }

  onSubmitSearchFormSort(event: any) {
    if (!this.isPageLoading) {
      this.isPageLoading = true;
      this.sortingPaging.sortAsc = event.sortAsc;
      this.sortingPaging.sortBy = event.sortBy;
      this.loadData();
    }
  }

  protected loadData(): void {
    this.tagService.getAllTagsPaged(this.sortingPaging).subscribe(tags => {

      this.tags = tags.results;
      this.sortingPaging.pageNumber = tags.pageIndex + 1;
      this.sortingPaging.pageSize = tags.pageSize;
      this.sortingPaging.totalElements = tags.totalElements;

      this.sortingPaging.calcFromToRow(tags.results.length);
      
      this.isPageLoading = false;
    });
  }
}
