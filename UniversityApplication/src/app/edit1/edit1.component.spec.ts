import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Edit1Component } from './edit1.component';

describe('Edit1Component', () => {
  let component: Edit1Component;
  let fixture: ComponentFixture<Edit1Component>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Edit1Component]
    });
    fixture = TestBed.createComponent(Edit1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
