import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Concept } from '../data-model/concept';
@Injectable({
  providedIn: 'root'
})
export class TopicViewService {
  topicUrl = environment.getConcept;
  // topicModel: Concept;
  private valueObs: BehaviorSubject<Array<Concept> > = new BehaviorSubject<Array<Concept> >([]);
  constructor(private http: HttpClient) { }
  public setValue(value: Array< Concept>): void {
    this.valueObs.next(value);
  }
  public getValue(): Observable<Array<Concept> > {
    return this.valueObs;
  }
  public getTopics(): Observable <Array< Concept > > {
    return this.http.get<Array<Concept> >(this.topicUrl);
  }

}
