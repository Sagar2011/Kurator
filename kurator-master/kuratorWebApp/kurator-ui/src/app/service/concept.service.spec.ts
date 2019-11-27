import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ConceptService } from './concept.service';
import { from } from 'rxjs';

describe('ConceptService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [ConceptService]
  }));

  it('should be created', () => {
    const service: ConceptService = TestBed.get(ConceptService);
    expect(service).toBeTruthy();
  });
});
