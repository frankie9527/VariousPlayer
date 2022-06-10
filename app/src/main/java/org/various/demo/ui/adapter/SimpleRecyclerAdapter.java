package org.various.demo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.easy.ui.recycler.base.BaseRecyclerAdapter;
import org.easy.ui.recycler.base.BaseRecyclerHolder;
import org.various.demo.R;
import org.various.demo.data.Bean;
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
        Bean bean=list.get(position);
        holder.tv_title.setText(bean.getTitle());
        holder.simple_view.setPlayData(bean.getUrl(),bean.getTitle());

    }

    static class Holder extends BaseRecyclerHolder {
        SimpleVideoView simple_view;
        TextView tv_title;
        public Holder(View itemView) {
            super(itemView);
            simple_view=itemView.findViewById(R.id.simple_view);
            tv_title=itemView.findViewById(R.id.tv_title);
        }
    }

}
