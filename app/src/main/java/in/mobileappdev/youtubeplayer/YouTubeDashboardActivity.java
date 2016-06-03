package in.mobileappdev.youtubeplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubePlayer;

import in.mobileappdev.enums.Orientation;
import in.mobileappdev.enums.Quality;
import in.mobileappdev.utils.Config;
import in.mobileappdev.utils.YouTubeThumbnail;

public class YouTubeDashboardActivity extends AppCompatActivity {

  YouTubePlayer.PlayerStyle playerStyle;
  Orientation orientation;
  boolean showAudioUi;
  boolean showFadeAnim;

  ImageView playerImageView;
  ImageButton playButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_you_tube_dashboard);

    playerImageView = (ImageView) findViewById(R.id.thumbnail);
    playButton = (ImageButton) findViewById(R.id.play_bt);

    playerStyle = YouTubePlayer.PlayerStyle.DEFAULT;
    orientation = Orientation.AUTO;
    showAudioUi = true;
    showFadeAnim = true;

    Glide.with(this).load(YouTubeThumbnail.getUrlFromVideoId(Config.YOUTUBE_VIDEO_CODE, Quality
        .HIGH))
        .thumbnail(0.5f)
        .crossFade()
        .error(R.drawable.loading_thumbnail)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(playerImageView);

    playButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(YouTubeDashboardActivity.this, "Playing........", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(YouTubeDashboardActivity.this, YouTubePlayerActivity.class);
        intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, Config.YOUTUBE_VIDEO_CODE);
        intent.putExtra(YouTubePlayerActivity.EXTRA_PLAYER_STYLE, playerStyle);
        intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, orientation);
        intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, showAudioUi);
        intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);
        startActivityForResult(intent, 1);
      }
    });

  }
}
