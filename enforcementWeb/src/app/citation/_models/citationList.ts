export class CitationList {
  constructor(
    public id?: number,
    public plateNumberEn?: string,
    public violationName?: string,
    public citationPenalties?: [],
    public imagesIds?: [],
    public isExpanded?: boolean
  ) {}
}