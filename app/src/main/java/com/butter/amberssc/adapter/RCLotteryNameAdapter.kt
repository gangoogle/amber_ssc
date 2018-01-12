package com.butter.amberssc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.butter.amberssc.R
import com.butter.amberssc.model.CaiPiaoUrl
import kotlinx.android.synthetic.main.item_lottery_url.view.*

/**
 * Created by zgyi on 2017-12-28.
 */
class RCLotteryNameAdapter(val context: Context, val urls: List<CaiPiaoUrl>) : RecyclerView.Adapter<RCLotteryNameAdapter.ViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.view?.tv_lottery_name?.text = urls[position].name
//        when (position % 3) {
//            2 -> holder?.view?.tv_lottery_name?.setBackgroundColor(context.resources.getColor(R.color.list_red))
//            1 -> holder?.view?.tv_lottery_name?.setBackgroundColor(context.resources.getColor(R.color.list_violet))
//            0 -> holder?.view?.tv_lottery_name?.setBackgroundColor(context.resources.getColor(R.color.list_blue))
//        }
        holder?.view?.tv_lottery_name?.setOnClickListener {
            mOnItemClickListener?.onClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lottery_url, parent, false))
    }

    override fun getItemCount(): Int = urls.size
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var setOnItemClickListener = {onItemClick: OnItemClickListener -> this.mOnItemClickListener = onItemClick }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }


}