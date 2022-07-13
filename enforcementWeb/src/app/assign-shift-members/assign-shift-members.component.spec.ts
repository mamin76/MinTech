import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignShiftMembersComponent } from './assign-shift-members.component';

describe('AssignShiftMembersComponent', () => {
  let component: AssignShiftMembersComponent;
  let fixture: ComponentFixture<AssignShiftMembersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignShiftMembersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignShiftMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
