import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CitationControlMultiComponent } from './citation-control-multi.component';

describe('CitationControlMultiComponent', () => {
  let component: CitationControlMultiComponent;
  let fixture: ComponentFixture<CitationControlMultiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CitationControlMultiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CitationControlMultiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
