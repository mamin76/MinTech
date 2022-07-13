import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EssentialUsersComponent } from './essential-users.component';

describe('EssentialUsersComponent', () => {
  let component: EssentialUsersComponent;
  let fixture: ComponentFixture<EssentialUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EssentialUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EssentialUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
