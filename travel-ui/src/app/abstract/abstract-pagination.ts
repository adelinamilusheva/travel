import { HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { SortingPagingData } from "../data/sorting-paging-data";

export abstract class AbstractPagination {
  public sortingPaging:  SortingPagingData;
  public pageSizeOptions: number[] = [5, 10, 15];

  protected isPageLoading: boolean;
  
  pageChanged(page: number): void {
    if (!this.isPageLoading) {
      this.isPageLoading = true;
      this.sortingPaging.pageNumber = page;
      this.loadData();
    }
  }

  onPageSizeChange(event: any) {
    if (!this.isPageLoading) {
      this.isPageLoading = true;
      this.sortingPaging.pageSize = event.target.value;
      this.loadData();
    }
  }

  onSort(sortBy: string) {
    if (!this.isPageLoading) {
      this.isPageLoading = true;
      if (this.sortingPaging.sortAsc == null) {
        this.sortingPaging.sortAsc = true;
      } else {
        this.sortingPaging.sortAsc = !this.sortingPaging.sortAsc;
      }
      this.sortingPaging.sortBy = sortBy;
      this.loadData();
    }
  }

  protected abstract loadData(id?: number) : void;

}