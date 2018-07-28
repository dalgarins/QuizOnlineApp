package co.anbora.onlinequizapp.bindings

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Carga una imagen en view especifico, este metodo es bindeado desde el layout
 * @param view ImageView donde se cargara la imagen
 * @param url String con la direccion de la imagen
 */
@BindingAdapter("bind:imageUrl")
fun loadImage(view: ImageView, url: String) {

    val options = RequestOptions()
            .centerCrop()
            //.error(R.drawable.account_circle)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

    Glide.with(view.context)
            .load(url)
            .apply(options)
            .into(view)
}