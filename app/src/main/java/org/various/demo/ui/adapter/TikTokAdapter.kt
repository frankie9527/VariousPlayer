package org.various.demo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.easy.ui.recycler.base.BaseRecyclerAdapter
import org.easy.ui.recycler.base.BaseRecyclerHolder
import org.various.demo.R
import org.various.demo.data.Bean
import org.various.demo.databinding.ActivityTiktokItemBinding
import org.various.player.ui.tiktok.DemoTiktokVideoView


/**
 * @author: Frankie
 * @Date: 2024/3/3
 * @Description:
 */
class TikTokAdapter() :
    BaseRecyclerAdapter<Bean, BaseRecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerHolder {
        val binding: ViewBinding;
        binding =
            ActivityTiktokItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TikTokHolder(binding)

    }

    override fun MyHolder(holder: BaseRecyclerHolder, position: Int) {
        super.MyHolder(holder, position)
        val data = list.get(position)
        val tikTokHolder = holder as TikTokHolder;
        tikTokHolder.bin(data, position)

    }


    class TikTokHolder(private val binding: ActivityTiktokItemBinding) :
        BaseRecyclerHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bin(data: Bean, position: Int) {


        }
    }

    var recyclerView: RecyclerView? = null
    fun setRecycler(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView;
    }

    fun getVideoView(position: Int): DemoTiktokVideoView? {
        val viewHolder = recyclerView!!.findViewHolderForAdapterPosition(position)
        return if (viewHolder != null && viewHolder is TikTokHolder) {
            viewHolder.itemView.findViewById(R.id.video_view);
        } else {
            null
        }

    }
}