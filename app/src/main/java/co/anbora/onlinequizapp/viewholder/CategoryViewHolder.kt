package co.anbora.onlinequizapp.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import co.anbora.onlinequizapp.actions.ItemClickListener
import co.anbora.onlinequizapp.databinding.CategoryLayoutBinding

class CategoryViewHolder: RecyclerView.ViewHolder, View.OnClickListener {

    private val binding: CategoryLayoutBinding
    private lateinit var itemClickListener: ItemClickListener

    constructor(view: CategoryLayoutBinding): super(view.root) {
        binding = view

        view.root.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(view: View) {
        itemClickListener.onClick(view, adapterPosition, false)
    }
}