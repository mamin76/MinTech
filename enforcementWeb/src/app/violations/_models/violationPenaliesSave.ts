export class ViolationPenaliesSave {
  constructor(
    public operationId?: number,
    public violationId?: number,
    public penaltiesIds?: Array<number>,

  ) { }
}