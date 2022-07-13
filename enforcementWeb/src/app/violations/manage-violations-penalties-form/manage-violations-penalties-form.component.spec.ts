import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageViolationsPenaltiesFormComponent } from './manage-violations-penalties-form.component';

describe('ManageViolationsPenaltiesFormComponent', () => {
  let component: ManageViolationsPenaltiesFormComponent;
  let fixture: ComponentFixture<ManageViolationsPenaltiesFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageViolationsPenaltiesFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageViolationsPenaltiesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
