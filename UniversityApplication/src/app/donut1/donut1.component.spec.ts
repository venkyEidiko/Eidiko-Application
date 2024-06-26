import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Donut1Component } from './donut1.component';

describe('Donut1Component', () => {
  let component: Donut1Component;
  let fixture: ComponentFixture<Donut1Component>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Donut1Component]
    });
    fixture = TestBed.createComponent(Donut1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
