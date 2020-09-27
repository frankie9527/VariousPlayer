package org.various.demo.ui.act;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.easy.ui.recycler.listener.ItemClickListener;
import org.various.demo.R;
import org.various.demo.base.BaseActivity;
import org.various.demo.data.SimpleData;
import org.various.demo.ui.adapter.SimpleRecyclerAdapter;

/**
 * Created by 江雨寒 on 2020/9/27
 * Email：847145851@qq.com
 * func:
 */
public class SimpleRecyclerActivity extends BaseActivity implements ItemClickListener {
    RecyclerView recycler;
    SimpleRecyclerAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_recycler;
    }

    @Override
    protected void initView() {
        super.initView();
        recycler=findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter=new SimpleRecyclerAdapter();
        adapter.setItemListener(this);
        recycler.setAdapter(adapter);
        adapter.setData(SimpleData.dataList);
    }

    @Override
    public void onItemClick(View view, int postion) {

    }
}
