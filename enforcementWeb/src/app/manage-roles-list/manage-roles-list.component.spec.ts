import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageRolesListComponent } from './manage-roles-list.component';

describe('ManageRolesListComponent', () => {
  let component: ManageRolesListComponent;
  let fixture: ComponentFixture<ManageRolesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageRolesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageRolesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
