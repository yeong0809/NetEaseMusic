package shellhub.github.neteasemusic.presenter.impl;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import shellhub.github.neteasemusic.model.SearchModel;
import shellhub.github.neteasemusic.model.SongModel;
import shellhub.github.neteasemusic.model.impl.SearchModelImpl;
import shellhub.github.neteasemusic.model.impl.SongModelImpl;
import shellhub.github.neteasemusic.networking.NetEaseMusicService;
import shellhub.github.neteasemusic.presenter.SearchPresenter;
import shellhub.github.neteasemusic.response.search.mp3.SongResponse;
import shellhub.github.neteasemusic.response.search.SearchResponse;
import shellhub.github.neteasemusic.response.search.artist.ArtistResponse;
import shellhub.github.neteasemusic.response.search.hot.HotResponse;
import shellhub.github.neteasemusic.response.search.song.detail.SongDetailResponse;
import shellhub.github.neteasemusic.response.search.video.VideoResponse;
import shellhub.github.neteasemusic.util.TagUtils;
import shellhub.github.neteasemusic.view.SearchView;

public class SearchPresenterImpl implements SearchPresenter, SearchModel.Callback, SongModel.Callback {
    private String TAG = TagUtils.getTag(this.getClass());
    private SearchModel mSearchModel;
    private NetEaseMusicService mNetEaseMusicService;
    private SearchView mSearchView;
    private SongModel mSongModel;

    public SearchPresenterImpl(SearchView searchView, NetEaseMusicService netEaseMusicService) {
        this.mSearchView = searchView;
        this.mNetEaseMusicService = netEaseMusicService;
        this.mSearchModel = new SearchModelImpl(this.mNetEaseMusicService);
        this.mSongModel = new SongModelImpl(this.mNetEaseMusicService);

    }

    @Override
    public void searchHot() {
        mSearchView.showProgress();
        mSearchModel.searchHot(this);
    }

    @Override
    public void loadHistory() {
        mSearchModel.loadHistory(this);
    }

    @Override
    public void saveHistory(String keyword) {
        mSearchModel.saveHistory(keyword);
    }

    @Override
    public void search(String keyword) {
        mSearchView.showProgress();
        mSearchModel.searchByKeywords(keyword, this);
    }

    @Override
    public void searchVideo(String keyword) {
        mSearchView.showProgress();
        mSearchModel.searchVideo(keyword, this);
    }

    @Override
    public void searchArtist(String keyword) {
        mSearchView.showProgress();
        mSearchModel.searchArtist(keyword, this);
    }

    @Override
    public void getSong(int id) {
        mSongModel.getSongUrl(id, this);
    }

    @Override
    public void onHotSuccess(HotResponse response) {
        mSearchView.hideProgress();
        mSearchView.showHots(response);
    }

    @Override
    public void onKeywordSuccess(SearchResponse searchResponse) {
        mSearchView.hideProgress();
        mSearchView.showSearchResult(searchResponse);
    }

    @Override
    public void onVideoSuccess(VideoResponse videoResponse) {
        mSearchView.hideProgress();
        mSearchView.showVideos(videoResponse);
    }

    @Override
    public void onArtistSuccess(ArtistResponse artistResponse) {
        mSearchView.hideProgress();
        mSearchView.showArtist(artistResponse);
    }

    @Override
    public void onHistory(List<String> histories) {
        LogUtils.d(TAG, histories);
        mSearchView.showHistory(histories);
    }

    @Override
    public void onHotFail() {
        //TODO
    }

    @Override
    public void onSongSuccess(SongResponse songResponse) {
        mSearchView.playSong(songResponse);
    }

    @Override
    public void onSongDetailSuccess(SongDetailResponse songDetailResponse) {

    }

    @Override
    public void onFail() {

    }
}