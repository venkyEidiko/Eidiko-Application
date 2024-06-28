import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckboxdropdownComponent } from './checkboxdropdown.component';

describe('CheckboxdropdownComponent', () => {
  let component: CheckboxdropdownComponent;
  let fixture: ComponentFixture<CheckboxdropdownComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckboxdropdownComponent]
    });
    fixture = TestBed.createComponent(CheckboxdropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
