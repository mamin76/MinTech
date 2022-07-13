import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CitationPenaltiesTotalComponent } from './citation-penalties-total.component';

describe('CitationPenaltiesTotalComponent', () => {
  let component: CitationPenaltiesTotalComponent;
  let fixture: ComponentFixture<CitationPenaltiesTotalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CitationPenaltiesTotalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CitationPenaltiesTotalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
