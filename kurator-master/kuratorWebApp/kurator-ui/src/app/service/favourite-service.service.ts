import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FavouriteServiceService {
  addFavouriteUrl = environment.favUrl;
  getFavouriteUrl = environment.getFav;
  deleteFavouriteUrl = environment.deleteFavouriteUrl;
  constructor(private http: HttpClient) { }
  favList = [];
  favSubject: BehaviorSubject<any> = new BehaviorSubject(this.favList);

  getFavouriteList(): Observable<any> {
    return this.favSubject;
  }

  addFav(favlist: any): Observable<any> {
    return this.http.patch(
      `${this.addFavouriteUrl}`,
      favlist)
      .pipe(
        tap((favlist1: any) => {
          this.favList.push(favlist1);
          this.favSubject.next(this.favList);

        })
      );
  }

  getFav() {
    this.http.get(`${this.getFavouriteUrl}`).subscribe((fav: any) => {
      this.favList = fav;
      this.favSubject.next(this.favList);
    });
  }

  deleteFav(documentId: string) {
    return this.http.delete(
      `${this.deleteFavouriteUrl}?documentId=${documentId}`).pipe(
        tap((favlist1: any) => {
          const updatedFav = this.favList.filter((fav) => {
            if (fav.documentId !== documentId) {
              return fav;
            }
          });
          this.favList = updatedFav;
          this.favSubject.next(this.favList);
        })
      );
  }
}
