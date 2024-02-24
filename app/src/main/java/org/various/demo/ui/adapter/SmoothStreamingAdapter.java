package org.various.demo.ui.adapter;

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
import org.various.demo.R;
import org.various.player.core.VariousExoPlayer;
import org.various.player.utils.DefaultTrackNameProvider;
import org.various.player.utils.ToastUtils;
import org.various.player.utils.TrackNameProvider;

/**
 * Created by 江雨寒 on 2020/9/21
 * Email：847145851@qq.com
 * func: 视频无缝衔接播放的adapter
 */
public class SmoothStreamingAdapter extends BaseRecyclerAdapter<String, SmoothStreamingAdapter.Holder> {
    private TrackNameProvider trackNameProvider;
    private MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
    private DefaultTrackSelector.Parameters parameters;
    private VariousExoPlayer player;

    public SmoothStreamingAdapter(Context mContext) {
        trackNameProvider = new DefaultTrackNameProvider(mContext.getResources());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_smooth_streaming_recycler_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void MyHolder(@NonNull Holder holder, int position) {
        super.MyHolder(holder, position);
        holder.tv.setText(list.get(position));
    }

    public void setVideoData(VariousExoPlayer variousExoPlayer) {
        this.player = variousExoPlayer;
        list.add("自动");
        mappedTrackInfo =
                Assertions.checkNotNull(player.getTrackSelector().getCurrentMappedTrackInfo());
        parameters = player.getTrackSelector().getParameters();
        TrackGroupArray trackGroupArray = mappedTrackInfo.getTrackGroups(0);
        TrackGroup group = trackGroupArray.get(0);
        for (int i = 0; i < group.length; i++) {
            String str = trackNameProvider.getTrackName(group.getFormat(i));
            list.add(str);
        }
        notifyDataSetChanged();
    }

    /**
     * position :0  是自动模式不用设置setSelectionOverride
     * position 不为0 的时候SelectionOverride groupIndex永远设置为0 是因为我们在setVideoData 的时候是获取角标为0 的数组集合
     */
    public void onClickOnPosition(int position) {
        DefaultTrackSelector.SelectionOverride o = new DefaultTrackSelector.SelectionOverride(0, position);
        ToastUtils.show("play the" + position + " item");
        DefaultTrackSelector.Parameters.Builder  builder = parameters.buildUpon();
        for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
            builder.clearSelectionOverrides(i).setRendererDisabled(i, false);
            if (i == 0 && position != 0) {
                builder.setSelectionOverride(i, mappedTrackInfo.getTrackGroups(i), o);
            }
        }
        player.getTrackSelector().setParameters(builder);
    }

    class Holder extends BaseRecyclerHolder {
        TextView tv;

        public Holder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
