package com.projectbox.filem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectbox.filem.R
import com.projectbox.filem.model.Cast
import com.projectbox.filem.viewholder.CastVH

/**
 * Created by adinugroho
 */
class CastAdapter(private var casts: List<Cast>) : RecyclerView.Adapter<CastVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return CastVH(view)
    }

    override fun getItemCount(): Int = casts.size

    override fun onBindViewHolder(holder: CastVH, position: Int) {
        holder.bind(casts[position])
    }

    fun update(casts: List<Cast>) {
        this.casts = casts
        notifyDataSetChanged()
    }
}