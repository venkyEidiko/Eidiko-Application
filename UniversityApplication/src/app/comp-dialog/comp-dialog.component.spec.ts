import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompDialogComponent } from './comp-dialog.component';

describe('CompDialogComponent', () => {
  let component: CompDialogComponent;
  let fixture: ComponentFixture<CompDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompDialogComponent]
    });
    fixture = TestBed.createComponent(CompDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
