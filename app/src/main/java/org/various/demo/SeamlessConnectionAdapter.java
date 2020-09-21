package org.various.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.util.Assertions;

import org.easy.ui.recycler.base.BaseRecyclerAdapter;
import org.easy.ui.recycler.base.BaseRecyclerHolder;
import org.various.player.core.VariousExoPlayer;
import org.various.player.utils.DefaultTrackNameProvider;
import org.various.player.utils.TrackNameProvider;

/**
 * Created by 江雨寒 on 2020/9/21
 * Email：847145851@qq.com
 * func: 视频无缝衔接播放的adapter
 */
public class SeamlessConnectionAdapter extends BaseRecyclerAdapter<String, SeamlessConnectionAdapter.Holder> {
    private TrackNameProvider trackNameProvider;

    public SeamlessConnectionAdapter(Context mContext) {
        trackNameProvider=new DefaultTrackNameProvider(mContext.getResources());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_seamless_recycler_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void MyHolder(@NonNull Holder holder, int position) {
        super.MyHolder(holder, position);
        holder.tv.setText(list.get(position));
    }

    public void setVideoData(){
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo =
                Assertions.checkNotNull(VariousExoPlayer.trackSelector.getCurrentMappedTrackInfo());
        DefaultTrackSelector.Parameters parameters = VariousExoPlayer.trackSelector.getParameters();
        TrackGroupArray trackGroupArray= mappedTrackInfo.getTrackGroups(0);
        TrackGroup group= trackGroupArray.get(0);
        for (int i=0;i<group.length;i++){
            String str=trackNameProvider.getTrackName(group.getFormat(i));
            list.add(str);
        }
      notifyDataSetChanged();
    }

    class Holder extends BaseRecyclerHolder{
        TextView tv;
        public Holder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            tv.setOnClickListener(this);
        }
    }
}
