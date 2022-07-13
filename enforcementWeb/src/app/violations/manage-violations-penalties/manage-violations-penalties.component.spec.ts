import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageViolationsPenaltiesComponent } from './manage-violations-penalties.component';

describe('ManageViolationsPenaltiesComponent', () => {
  let component: ManageViolationsPenaltiesComponent;
  let fixture: ComponentFixture<ManageViolationsPenaltiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageViolationsPenaltiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageViolationsPenaltiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
