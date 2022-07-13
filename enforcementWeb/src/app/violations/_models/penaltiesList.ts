export class PenaltiesList {
  constructor(
    public id?: number,
    public enName?: string,
    public arName?: string,
    public type?: string,
    public fees?: number,
    public email?: string,
    public subject?: string,
    public body?: string,
    public method?: string,
    public description?: string
  ) { }
}