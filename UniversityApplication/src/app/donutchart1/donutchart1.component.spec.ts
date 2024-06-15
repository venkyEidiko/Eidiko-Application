import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Donutchart1Component } from './donutchart1.component';

describe('Donutchart1Component', () => {
  let component: Donutchart1Component;
  let fixture: ComponentFixture<Donutchart1Component>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Donutchart1Component]
    });
    fixture = TestBed.createComponent(Donutchart1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
