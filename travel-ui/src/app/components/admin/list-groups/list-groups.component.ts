import { Component, OnInit } from '@angular/core';
import { AbstractPagination } from 'src/app/abstract/abstract-pagination';
import { ListGroup } from 'src/app/data/list-group.model';
import { SortingPagingData } from 'src/app/data/sorting-paging-data';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group.service';

@Component({
  selector: 'app-list-groups',
  templateUrl: './list-groups.component.html',
  styleUrls: ['./list-groups.component.css']
})
export class ListGroupsComponent extends AbstractPagination implements OnInit {
  groups: ListGroup[];

  constructor(
    private groupService: GroupService,
    public authService: AuthService
    ) {
    super();
  }

  ngOnInit(): void {
    this.sortingPaging = new SortingPagingData();
    this.isPageLoading = false;
    this.loadData();
  }

  changeGroupStatus(id: number, status: boolean) {
    this.groupService.updateIsActiveGroup(id, status).subscribe(() => window.location.reload());  
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
    this.groupService.getAllGroupsPaged(this.sortingPaging).subscribe(groups => {

      this.groups = groups.results;
      this.sortingPaging.pageNumber = groups.pageIndex + 1;
      this.sortingPaging.pageSize = groups.pageSize;
      this.sortingPaging.totalElements = groups.totalElements;

      this.sortingPaging.calcFromToRow(groups.results.length);
      
      this.isPageLoading = false;
    });
  }
}
