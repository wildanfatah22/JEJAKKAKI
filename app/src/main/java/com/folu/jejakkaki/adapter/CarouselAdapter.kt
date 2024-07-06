package com.folu.jejakkaki.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.folu.jejakkaki.R

class CarouselAdapter(
    private val itemList: List<Pair<Int, Int>>,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val (imageResId, colorResId) = itemList[position]
        holder.bind(imageResId, colorResId)
    }

    override fun getItemCount(): Int = itemList.size

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val view: View = itemView.findViewById(R.id.view)

        fun bind(imageResId: Int, colorResId: Int) {
            imageView.setImageResource(imageResId)
            view.setBackgroundColor(itemView.context.getColor(colorResId))
            itemView.setOnClickListener {
                itemClickListener(imageResId)
            }
        }
    }
}
