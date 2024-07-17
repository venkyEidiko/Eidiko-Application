import { TestBed } from '@angular/core/testing';

import { CompdialogService } from './compdialog.service';

describe('CompdialogService', () => {
  let service: CompdialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompdialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
