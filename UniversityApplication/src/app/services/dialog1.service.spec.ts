import { TestBed } from '@angular/core/testing';

import { Dialog1Service } from './dialog1.service';

describe('Dialog1Service', () => {
  let service: Dialog1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Dialog1Service);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
