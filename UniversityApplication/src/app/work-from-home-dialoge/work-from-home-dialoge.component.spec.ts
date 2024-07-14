import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkFromHomeDialogeComponent } from './work-from-home-dialoge.component';

describe('WorkFromHomeDialogeComponent', () => {
  let component: WorkFromHomeDialogeComponent;
  let fixture: ComponentFixture<WorkFromHomeDialogeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkFromHomeDialogeComponent]
    });
    fixture = TestBed.createComponent(WorkFromHomeDialogeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
