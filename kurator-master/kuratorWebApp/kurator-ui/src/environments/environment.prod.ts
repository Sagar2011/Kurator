export const environment = {
  production: true,
  searchUrl: '/search/documents',
  profileUrl: '/users/userprofile',
  updateProfileUrl: '/users/updateprofile',
  availableTopics: '/users/available-topics',
  interestedTopics: '/users/interested-topics',
  guestUrl: '/search/guest/documents',
  addedDocuments: '/docapi/addedDocuments',
  trendingdocsUrl: '/docapi/documents/recentAdded',
  dialogflow: {
    kuratorbot: 'fb3e661ac9e4437e9c66ecfdaa1c1796'
  },
  wikiApi: 'https://en.wikipedia.org/w/api.php?origin=*&action=opensearch&search=',
  favUrl: '/users/favourites',
  getFav: '/users/favourites',
  recentUrl: '/users/history/view',
  getRecent: '/users/history/view',
  getConcept: '/search/concepts',
  deleteFavouriteUrl: '/users/favourites',
  followUser: '/users/people',
  userPlayList : 'users/playlist',
  documentPlaylist : 'users/userplaylist',
  getAllDocument: '/docapi/documents',
  getIndexedOn: '/index/indexedOn',
  getIntent: '/search/intent/confRating'
};
