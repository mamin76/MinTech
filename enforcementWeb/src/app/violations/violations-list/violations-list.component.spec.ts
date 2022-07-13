import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViolationsListComponent } from './violations-list.component';

describe('ViolationsListComponent', () => {
  let component: ViolationsListComponent;
  let fixture: ComponentFixture<ViolationsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViolationsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViolationsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
