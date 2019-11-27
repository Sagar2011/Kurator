import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.prod';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserprofileService {
  constructor(private http: HttpClient) { }
  private url = environment.profileUrl;
  private editProfileUrl = environment.updateProfileUrl;
  private availableTopics = environment.availableTopics;
  private interestedTopics = environment.interestedTopics;
  private followUser = environment.followUser;
  private userPlaylist = environment.userPlayList;
  private documentPlaylist = environment.documentPlaylist;

  playlist = [];
  userTopicList = [];
  followingUserList = [];
  public playSubject: BehaviorSubject<any> = new BehaviorSubject([]);
  userTopicsObs: BehaviorSubject<any> = new BehaviorSubject([]);
  followingList: BehaviorSubject<any> = new BehaviorSubject(
    this.followingUserList
  );
  private subject = new BehaviorSubject(true);

  public getValueTopics(): Observable<any> {
    return this.userTopicsObs;
  }

  public getvalueFollowUser(): Observable<any> {
    return this.followingList;
  }

  entryPoint() {
    this.subject.next(false);
  }

  entryPointBooleanValue() {
    return this.subject;
  }

  // get user profile information
  userProfile() {
    return this.http.get(this.url);
  }

  // update user profile inforamation
  updateProfile(params) {
    return this.http.patch(
      this.editProfileUrl + '/' + params.email + '/',
      params
    );
  }

  // gettin topic list
  getAllTopics(): Observable<any> {
    return this.http.get(this.availableTopics);
  }

  // selecting topic list
  setAllTopics(param) {
    return this.http.patch(`${this.interestedTopics}`, param);
  }

  // following a user
  followingUser(followingPeople) {
    return this.http.patch(`${this.followUser}`, followingPeople);
  }

  // getting list of following user
  getfollowingUser() {
    return this.http.get(`${this.followUser}`).subscribe((fav: any) => {
      this.followingUserList = fav.result;
      this.followingList.next(this.followingUserList);
    });
  }

  // unfollow a user
  unFollowUser(followerName) {
    return this.http
      .delete(`${this.followUser}?followerName=${followerName}`)
      .pipe(
        tap((favlist1: any) => {
          const updatedTopics = this.followingUserList.filter(fav => {
            if (fav !== followerName) {
              return fav;
            }
          });
          this.followingUserList = updatedTopics;
          this.followingList.next(this.followingUserList);
        })
      );
  }

  // for fetching users Playlist

  getPlay(): Observable<any> {
    return this.playSubject;
  }

  // for creating playlist
  createPlaylist(playlist: any): Observable<any> {
    return this.http.patch(`${this.userPlaylist}`, playlist, httpOptions).pipe(
      tap((playlist1: any) => {
        console.log('playlist11111 :::', playlist);
        this.playlist.push(playlist);
        console.log('this.playlist ::::', this.playlist);
        this.playSubject.next(this.playlist);
      })
    );
  }

  getPlaylist() {
    this.http.get(`${this.userPlaylist}`).subscribe((res: any) => {
      this.playlist = res.result;
      console.log(this.playlist);
      this.playSubject.next(this.playlist);
    });
  }

  removePlaylist(pl: string) {
    return (
      this.http
        .delete(`users/playlist?playlistName=${pl}`)
        .pipe(
          //   tap((playlist1: any) => {
          // console.log('playlist delete ::', playlist1, playlistName);
          // const updatedList = this.playlist.filter(list => {
          //   if (list.playlistName !== playlistName) {
          //     return list;
          //   }
          // });
          // this.playlist = updatedList;
          //   this.playSubject.next(this.playlist);
          // })
          tap(status => console.log(status))
        )
    );
  }

  addDocumentToPlaylist(params: any, param: any): Observable<any> {
    return this.http.patch(
      `${this.documentPlaylist}?playlists=${params}&documentID=${param}`,
      {}
    );
  }

  openUserPlaylist(param: any): Observable<any> {
    return this.http.get(`${this.documentPlaylist}/${param}/documents`);
  }

  getUserFollowTopics() {
    return this.http.get('/users/userTopics').subscribe((fav: any) => {
      this.userTopicList = fav.result;
      this.userTopicsObs.next(this.userTopicList);
    });
  }

  // unfollow topics

  deleteUserFollowTopic(topic: any) {
    topic = encodeURIComponent(topic);
    console.log('service ::', topic);
    console.log(`${this.interestedTopics}?topicName=${topic}`);
    return this.http
      .delete(`${this.interestedTopics}?topicName=${topic}`, {})
      .pipe(
        tap((favlist1: any) => {
          const updatedTopics = this.userTopicList.filter(fav => {
            if (fav !== topic) {
              return fav;
            }
          });
          this.userTopicList = updatedTopics;
          this.userTopicsObs.next(this.userTopicList);
        })
      );
  }
}
