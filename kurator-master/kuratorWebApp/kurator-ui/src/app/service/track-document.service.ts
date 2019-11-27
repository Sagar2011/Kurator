import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TrackDocumentService {

  documentUrl = environment.getAllDocument;
  indexedUrl = environment.getIndexedOn;
  intentUrl = environment.getIntent;
    constructor(private http: HttpClient) { }

  public getDocument() {
    return this.http.get(this.documentUrl);
    // console.log(this.http.get(this.documentUrl));
  }
  public getIndexedOn( documentId: string) {
    return this.http.get(`${this.indexedUrl}?documentId=${documentId}`);
  }
  public getIntentOf( documentId: string) {
    return this.http.get(`${this.intentUrl}?documentId=${documentId}`);
  }
  // topicModel: Concept;
  // private valueObs: BehaviorSubject<Array<Concept> > = new BehaviorSubject<Array<Concept> >([]);
  // constructor(private http: HttpClient) { }
  // public setValue(value: Array< Concept>): void {
  //   this.valueObs.next(value);
  // }
  // public getValue(): Observable<Array<Concept> > {
  //   return this.valueObs;
  // }
  // public getTopics(): Observable <Array< Concept > > {
  //   return this.http.get<Array<Concept> >(this.topicUrl);
  // }

}
