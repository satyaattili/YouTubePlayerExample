package in.mobileappdev.youtubeplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.mobileappdev.models.Video;
import in.mobileappdev.models.VideosResponse;
import in.mobileappdev.rest.RestApi;
import in.mobileappdev.rest.RetrofitClient;
import in.mobileappdev.utils.Config;
import in.mobileappdev.utils.ViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends YouTubeBaseActivity implements
		YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaylistEventListener {

	private static final int RECOVERY_DIALOG_REQUEST = 1;
	private static final String TAG = "MainActivity";
	private RestApi restApi;

	// YouTube player view
	private YouTubePlayerView youTubeView;
	private RecyclerView videoList;
	private YouTubePlayer player;
	List<Video> videos = new ArrayList<>();
	private int playlistMaxCount;
	private int currentVideoIndex = 0;
	private PlayListAdapter adapter;
	private ImageButton playButton;
	private TextView mVideoTitle;
	private TextView mLessExpand;
	private LinearLayout mMorelayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_youtube);
		playButton = (ImageButton) findViewById(R.id.play_bt);
		mVideoTitle = (TextView) findViewById(R.id.screen_video_title);
		mLessExpand = (TextView) findViewById(R.id.screen_video_less);
		mMorelayout = (LinearLayout) findViewById(R.id.more_layout);
		collapseMoreLayout();
		mLessExpand.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(mLessExpand.getContentDescription().equals(getString(R.string.more))){
						expandMoreLayout();
				}else{
						collapseMoreLayout();
				}

			}
		});

		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		videoList = (RecyclerView) findViewById(R.id.video_list);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
		videoList.setLayoutManager(mLayoutManager);
		videoList.setItemAnimator(new DefaultItemAnimator());

		//videoList.addItemDecoration(new DividerItemDecoration(MainActivity.this,
		//		DividerItemDecoration.VERTICAL_LIST));

		adapter = new PlayListAdapter(MainActivity.this, videos);
		videoList.setAdapter(adapter);
		adapter.setOnVideoSelectedListener(new PlayListAdapter.OnVideoSelectedListener() {
			@Override
			public void onVideoClick(int index) {
				currentVideoIndex = index;
						if(player != null && Config.googleIOvideos.length>index){
							//player.loadVideos(videos.get(index).getVideo_id(), index, 0);
							player.loadVideos(getVideoIds(),index,0);
							mVideoTitle.setText(videos.get(index).getVideo_title());
							adapter.setCurrentplayingItem(index);
						}
			}
		});

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(player != null){
					player.play();
				}
			}
		});
		
		getVideos();

	}


	private void expandMoreLayout() {
		mLessExpand.setContentDescription(getString(R.string.less));
		mLessExpand.setText(getString(R.string.less));
		ViewUtils.expand(mMorelayout);
	}


	private void collapseMoreLayout() {
		mLessExpand.setContentDescription(getString(R.string.more));
		mLessExpand.setText(getString(R.string.more));
		ViewUtils.collapse(mMorelayout);
	}

	private void getVideos() {
		restApi = RetrofitClient.getApiService(this);
		restApi.getVideos("allvideos").enqueue(new Callback<VideosResponse>() {
			@Override
			public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
				Log.d(TAG, "Respo : "+response.body().getVideos().size());
				videos.addAll(response.body().getVideos());
				adapter.notifyDataSetChanged();
				playlistMaxCount = response.body().getVideos().size();
				// Initializing video player with developer key
				youTubeView.initialize(Config.DEVELOPER_KEY, MainActivity.this);
			}

			@Override
			public void onFailure(Call<VideosResponse> call, Throwable t) {

			}
		});
	}

	@Override
	public void onInitializationFailure(YouTubePlayer.Provider provider,
			YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
		} else {
			String errorMessage = String.format(
					getString(R.string.error_player), errorReason.toString());
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		   this.player = player;

		    //player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
		if (!wasRestored) {
			player.setPlaylistEventListener(this);
			player.setPlaybackEventListener(playbackEventListener);
			//player.setPlayerStateChangeListener(playerStateChangeListener);
			//player.loadVideo(videos.get(0).getVideo_id());
			mVideoTitle.setText(videos.get(0).getVideo_title());
			// loadVideo() will auto play video
			// Use cueVideo() method, if you don't want to play it automatically
			//player.loadVideo(Config.YOUTUBE_VIDEO_CODE);
			//player.loadVideo(videos.get());



			if(videos.size()>0){
				player.loadVideos(getVideoIds());
			}


			// Hiding player controls
			//player.setPlayerStyle(PlayerStyle.CHROMELESS);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RECOVERY_DIALOG_REQUEST) {
			// Retry initialization if user performed a recovery action
			getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
		}
	}

	private YouTubePlayer.Provider getYouTubePlayerProvider() {
		return (YouTubePlayerView) findViewById(R.id.youtube_view);
	}

	@Override
	public void onPrevious() {
		currentVideoIndex--;
		adapter.setCurrentplayingItem(currentVideoIndex);
		Log.d(TAG, "onPrevious : Current Video Index : "+currentVideoIndex);
	}

	@Override
	public void onNext() {
		if(currentVideoIndex < playlistMaxCount){
			currentVideoIndex++;
		}
		adapter.setCurrentplayingItem(currentVideoIndex);
		Log.d(TAG, "onNext : Current Video Index : "+currentVideoIndex);

	}

	@Override
	public void onPlaylistEnded() {
		//currentVideoIndex = 0;
	}


	private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

		@Override
		public void onBuffering(boolean arg0) {
		}

		@Override
		public void onPaused() {
			long time = player.getCurrentTimeMillis();
			Log.d(TAG, "onPaused : "+getTime(time));
		}

		@Override
		public void onPlaying() {
		}

		@Override
		public void onSeekTo(int arg0) {
			Log.d(TAG, "onSeekTo : "+arg0);
		}

		@Override
		public void onStopped() {
			long time = player.getCurrentTimeMillis();
			Log.d(TAG, "onStopped : "+getTime(time));
			/*if(currentVideoIndex<videos.size())
				player.loadVideo(videos.get(currentVideoIndex+1).getVideo_id());*/
		}

	};

	private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

		@Override
		public void onAdStarted() {
		}

		@Override
		public void onError(YouTubePlayer.ErrorReason arg0) {
		}

		@Override
		public void onLoaded(String arg0) {
		}

		@Override
		public void onLoading() {
		}

		@Override
		public void onVideoEnded() {
			if(currentVideoIndex<videos.size())
			player.loadVideo(videos.get(currentVideoIndex+1).getVideo_id());
		}


		@Override
		public void onVideoStarted() {
			Log.d(TAG, "onVideoStarted : "+getTime(player.getDurationMillis()));
		}
	};


	private String getTime(long millis){
		Date date = new Date(millis);
		DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
		String dateFormatted = formatter.format(date);
		return  dateFormatted;
	}

	private List<String> getVideoIds(){
		List<String> vids = new ArrayList<String>();

		for(int i=0;i<videos.size();i++){
			vids.add(videos.get(i).getVideo_id());
		}


		return vids;
	}

}
