export class PagedList<T> {
    constructor(
      public currentPage?: number,
      public totalPages?: number,
      public pageSize?: number,
      public totalCount?: number,
      public hasPrevious?: boolean,
      public HasNext?: boolean,
      public items?: T[]
    ) {}
  }
  