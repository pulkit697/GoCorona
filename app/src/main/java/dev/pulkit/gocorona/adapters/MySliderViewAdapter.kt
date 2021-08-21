package dev.pulkit.gocorona.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import dev.pulkit.gocorona.R
import kotlinx.android.synthetic.main.list_item_slider_view.view.*

class MySliderViewAdapter(private val list:List<Int>):SliderViewAdapter<MySliderViewAdapter.MySliderViewHolder>() {
    class MySliderViewHolder(itemView:View):SliderViewAdapter.ViewHolder(itemView) {
        fun onBind(image:Int){
            itemView.ivSliderImageHomeFrag.setImageResource(image)
        }
    }
    override fun getCount(): Int  = list.size

    override fun onCreateViewHolder(parent: ViewGroup): MySliderViewHolder =
        MySliderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_slider_view,parent,false))

    override fun onBindViewHolder(viewHolder: MySliderViewHolder, position: Int) {
        viewHolder.onBind(list[position])
    }
}