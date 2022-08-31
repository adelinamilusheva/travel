import { Component, OnInit } from '@angular/core';
import { AbstractPagination } from 'src/app/abstract/abstract-pagination';
import { ListPublication } from 'src/app/data/list-publication.model';
import { SortingPagingData } from 'src/app/data/sorting-paging-data';
import { AuthService } from 'src/app/services/auth.service';
import { PublicationService } from 'src/app/services/publication.service';

@Component({
  selector: 'app-list-publications',
  templateUrl: './list-publications.component.html',
  styleUrls: ['./list-publications.component.css']
})
export class ListPublicationsComponent extends AbstractPagination implements OnInit {
  publications: ListPublication[];

  constructor(
    private publicationService: PublicationService,
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
    this.publicationService.updateIsActivePublication(id, status).subscribe(() => window.location.reload());  
  }

  loadData() {
    this.publicationService.getAllPublicationsPaged(this.sortingPaging).subscribe(publications => {

      this.publications = publications.results;
      this.sortingPaging.pageNumber = publications.pageIndex + 1;
      this.sortingPaging.pageSize = publications.pageSize;
      this.sortingPaging.totalElements = publications.totalElements;

      this.sortingPaging.calcFromToRow(publications.results.length);
      
      this.isPageLoading = false;
    });
  }
}
