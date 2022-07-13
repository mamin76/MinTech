import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkforceListComponent } from './workforce-list.component';

describe('WorkforceListComponent', () => {
  let component: WorkforceListComponent;
  let fixture: ComponentFixture<WorkforceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkforceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkforceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
