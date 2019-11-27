import { TestBed } from '@angular/core/testing';

import { FlagDocumentService } from './flag-document.service';

describe('FlagDocumentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FlagDocumentService = TestBed.get(FlagDocumentService);
    expect(service).toBeTruthy();
  });
});
