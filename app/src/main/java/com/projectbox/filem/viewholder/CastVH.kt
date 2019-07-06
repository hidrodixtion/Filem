package com.projectbox.filem.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projectbox.filem.BuildConfig
import com.projectbox.filem.model.Cast
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cast.*

/**
 * Created by adinugroho
 */
class CastVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(cast: Cast) {
        txt_name.text = cast.name
        txt_character.text = cast.character
        
        val faceUrl = "${BuildConfig.FACE_IMAGE_URL}${cast.image}"
        Glide.with(containerView).load(faceUrl).into(image_view)
    }
}