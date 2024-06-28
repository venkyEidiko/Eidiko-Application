import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusdropComponent } from './statusdrop.component';

describe('StatusdropComponent', () => {
  let component: StatusdropComponent;
  let fixture: ComponentFixture<StatusdropComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StatusdropComponent]
    });
    fixture = TestBed.createComponent(StatusdropComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
