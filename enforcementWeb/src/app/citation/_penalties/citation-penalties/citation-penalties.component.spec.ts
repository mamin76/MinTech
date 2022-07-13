import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CitationPenaltiesComponent } from './citation-penalties.component';

describe('CitationPenaltiesComponent', () => {
  let component: CitationPenaltiesComponent;
  let fixture: ComponentFixture<CitationPenaltiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CitationPenaltiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CitationPenaltiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
