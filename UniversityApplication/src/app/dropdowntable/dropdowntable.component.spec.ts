import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdowntableComponent } from './dropdowntable.component';

describe('DropdowntableComponent', () => {
  let component: DropdowntableComponent;
  let fixture: ComponentFixture<DropdowntableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DropdowntableComponent]
    });
    fixture = TestBed.createComponent(DropdowntableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
