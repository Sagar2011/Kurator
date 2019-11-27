import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { AddDocumentService } from './add-document.service';

describe('AddDocumentService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule
    ],
    providers: [AddDocumentService]
  }));

  it('should be created', () => {
    const service: AddDocumentService = TestBed.get(AddDocumentService);
    expect(service).toBeTruthy();
  });
});
