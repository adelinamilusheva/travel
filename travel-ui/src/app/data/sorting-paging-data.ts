export class SortingPagingData {
  pageSize: number;
  pageNumber: number;
  totalElements: number;

  fromRow?: number;
  toRow?: number;

  sortBy: string;
  sortAsc?: boolean | null;

  public constructor(pageSize?: number) {
    if (pageSize) {
      this.pageSize = pageSize;
    } else {
      this.pageSize = 10;
    }
    this.pageNumber = 1;
  }

  public isSortingValid(): boolean {
    return this.sortBy != "" && this.sortAsc != null;
  }

  public getPageSize(): string {
    if (this.pageSize == null) {
      return '';
    } else {
      return this.pageSize.toString();
    }
  }

  public getPageNumber(): string {
    if (this.pageNumber == null) {
      return '';
    } else {
      return this.pageNumber.toString();
    }
  }

  public static buildSortData(sortBy: string, sortAsc: boolean | null) {
    let data = new SortingPagingData();
    data.sortBy = sortBy;
    data.sortAsc = sortAsc;
    return data;
  }

  public calcFromToRow(responseSize: number) {
    this.fromRow = (this.pageNumber - 1) * this.pageSize + 1;
    this.toRow = ((this.pageNumber - 1) * this.pageSize) + responseSize;
  }

  public getDirection() {
    if (this.sortAsc) {
      return 'asc';
    } else {
      return 'desc';
    }
  }
  
}