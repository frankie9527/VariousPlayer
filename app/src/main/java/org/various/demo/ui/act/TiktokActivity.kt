package org.various.demo.ui.act

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import org.easy.ui.recycler.listener.ItemClickListener
import org.various.demo.data.SimpleData
import org.various.demo.databinding.ActivityTiktokBinding
import org.various.demo.ui.adapter.TikTokAdapter
import org.various.player.core.PlayerManager


/**
 * @author: Frankie
 * @Date: 2024/3/12
 * @Description:
 */
class TiktokActivity : AppCompatActivity(), ItemClickListener {
    val binding by lazy {
        ActivityTiktokBinding.inflate(layoutInflater)
    }
    var adapter: TikTokAdapter? = null;
    var currentPlayPosition = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    fun initView() {
        adapter = TikTokAdapter();
        binding.recycler.adapter = adapter;
        binding.recycler.offscreenPageLimit=1;
        binding.recycler.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                playTikTokItem(position)
            }
        });
        adapter!!.setRecycler(binding.recycler.getChildAt(0) as RecyclerView)
        adapter!!.setData(SimpleData.dataList)
    }

    override fun onItemClick(p0: View?, p1: Int) {

    }

    fun playTikTokItem(position: Int) {
        if (position == currentPlayPosition) {
            return
        }
        val videoView = adapter!!.getVideoView(position);
        val data = adapter!!.getDataInPostion(position);
        videoView!!.setDataAndPlay(data.url)
        currentPlayPosition=position;
    }
}



