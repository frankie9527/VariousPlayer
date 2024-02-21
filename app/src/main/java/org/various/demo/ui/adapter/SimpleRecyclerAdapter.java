package org.various.demo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.easy.ui.recycler.base.BaseRecyclerAdapter;
import org.easy.ui.recycler.base.BaseRecyclerHolder;
import org.various.demo.R;
import org.various.demo.data.Bean;
import org.various.player.ui.recycler.RecyclerItemVideoView;
import org.various.player.ui.simple.SimpleVideoView;


/**
 * Created by 江雨寒 on 2020/9/27
 * Email：847145851@qq.com
 * func:
 */
public class SimpleRecyclerAdapter extends BaseRecyclerAdapter<Bean, SimpleRecyclerAdapter.Holder> {
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_simple_recycler_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void MyHolder(@NonNull Holder holder, int position) {
        super.MyHolder(holder, position);
        Bean bean = list.get(position);
        holder.tv_title.setText(bean.getTitle());
        holder.item_view.setPlayData(bean.getUrl(), bean.getTitle());
        holder.item_view.getControlView().getCentView().setOnCenterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.item_view.startSyncPlayOrPause(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class Holder extends BaseRecyclerHolder {
        RecyclerItemVideoView item_view;
        RelativeLayout rl_bottom;
        TextView tv_title;

        public Holder(View itemView) {
            super(itemView);
            item_view = itemView.findViewById(R.id.item_view);
            tv_title = itemView.findViewById(R.id.tv_title);
            rl_bottom = itemView.findViewById(R.id.rl_bottom);
            rl_bottom.setOnClickListener(this);
        }
    }

}
