export class essentialUsersDto {
    constructor(
      public id?: number,
      public Name?: string,
      public Username?: string,
      public Email?: string,
      public Phone?: string,
      public Role?: string,
      public Operation?: string,
      public isExpanded?: boolean
    ) {}
  }