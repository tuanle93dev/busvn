package com.example.busvn.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.busvn.R
import com.example.busvn.objects.Constant
import com.example.busvn.objects.FavoritePlace
import java.util.*

class FavoritePlaceAdapter(private val listData: ArrayList<FavoritePlace>,private val context: Context):BaseAdapter() {

    override fun getCount(): Int {
        return listData.size
    }

    override fun getItem(i: Int): Any? {
        return listData[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View? {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = view
        if (view == null) view = inflater?.inflate(R.layout.item_home, viewGroup, false)
        val favoritePlace: FavoritePlace = listData!![i]
        val textView = view?.findViewById<View>(R.id.textView) as TextView
        textView.text = favoritePlace.getName()
        val textView2 = view.findViewById<View>(R.id.textView2) as TextView
        val imageView = view.findViewById<View>(R.id.image) as ImageView
        when {
            favoritePlace.getType() === Constant.TYPE_HOME -> {
                imageView.setImageResource(R.drawable.ic_home_black_36px)
            }
            favoritePlace.getType() === Constant.TYPE_COMPANY -> {
                imageView.setImageResource(R.drawable.ic_business_center_black_36px)
            }
            favoritePlace.getType() === Constant.TYPE_OTHER -> {
                imageView.setImageResource(R.drawable.ic_star_black_36px)
                textView.setText(favoritePlace.getName())
            }
        }
        if (favoritePlace.getAddress() != null) textView2.setText(favoritePlace.getAddress()) else textView2.text =
            "Chạm để sửa"
        return view
    }
}