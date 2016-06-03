package in.mobileappdev.youtubeplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import in.mobileappdev.models.Video;

/**
 * Created by satyanarayana.avv on 23-05-2016.
 */

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

  private Context mContext;
  private List<Video> mVideoIds;
  private OnVideoSelectedListener onVideoSelectedListener;
  private int currentSelectedPosition;

  public PlayListAdapter(Context mContext, List<Video> mVideoIds) {
    this.mContext = mContext;
    this.mVideoIds = mVideoIds;
  }

  @Override
  public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.playlist_item, parent, false);
    return new PlayListViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(PlayListViewHolder holder, final int position) {
    Video currentVideo = mVideoIds.get(position);
    /*if(currentSelectedPosition == position){
      holder.parent.setBackgroundResource(R.color.bg);
    }else{
      holder.parent.setBackgroundResource(R.color.list_item_bg);
    }*/

    holder.title.setText(currentVideo.getVideo_title());
    holder.duration.setText(currentVideo.getVideo_duration());
    String imgUrl = "http://img.youtube.com/vi/"+currentVideo.getVideo_id()+"/0.jpg";

    Glide.with(mContext).load(imgUrl)
        .thumbnail(0.5f)
        .crossFade()
        .error(R.drawable.loading_thumbnail)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(holder.thumbnail);
    holder.parent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(onVideoSelectedListener != null){
          onVideoSelectedListener.onVideoClick(position);
        }
      }
    });
  }

  public void setCurrentplayingItem(int position){
    currentSelectedPosition = position;
    notifyDataSetChanged();
  }


  public void setOnVideoSelectedListener(OnVideoSelectedListener onVideoSelectedListener){
    this.onVideoSelectedListener = onVideoSelectedListener;
  }

  @Override
  public int getItemCount() {
    return mVideoIds.size();
  }

  public class PlayListViewHolder extends RecyclerView.ViewHolder{
    ImageView thumbnail;
    TextView title;
    TextView duration;
    RelativeLayout parent;
    public PlayListViewHolder(View itemView) {
      super(itemView);
      thumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
      title = (TextView) itemView.findViewById(R.id.video_title);
      parent = (RelativeLayout) itemView.findViewById(R.id.parent_list_item);
      duration = (TextView)itemView.findViewById(R.id.video_duration);
    }
  }

  public interface OnVideoSelectedListener{
    public void onVideoClick(int index);
  }
}
