package com.example.busvn

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.example.busvn.adapters.FavoritePlaceAdapter
import com.example.busvn.objects.Constant
import com.example.busvn.objects.FavoritePlace
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class HomeFragment : Fragment() {

    private var lsView: ListView?= null
    private var tvBlink: TextView? = null
    private var tvLable:TextView? = null
    private var tvHeader:TextView? = null
    private var tvWeather:TextView? = null
    private var btnTimkiem: ImageButton? = null
    private var btAddPlace: ImageView? = null

    private var stData: String? = null
    private var lsPlace: ArrayList<FavoritePlace> = ArrayList()
    private var adapter: FavoritePlaceAdapter? = null

    private val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
    private val PLACE_AUTOCOMPLETE_REQUEST_EDIT = 2
    private val PLACE_AUTOCOMPLETE_REQUEST_ADD = 3
    var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val animZoomOut = AnimationUtils.loadAnimation(
            activity!!.applicationContext, R.anim.zoom_out
        )
        val animBlink = AnimationUtils.loadAnimation(
            activity!!.applicationContext, R.anim.blink
        )

        tvBlink = view.findViewById<View>(R.id.tvBlink) as TextView
        btnTimkiem = view.findViewById<View>(R.id.btnTimkiem) as ImageButton
        tvHeader = view.findViewById<View>(R.id.tvHeader) as TextView
        btAddPlace = view.findViewById<View>(R.id.btAddPlace) as ImageView

        tvBlink?.startAnimation(animBlink)
        creatData()

        lsView = view.findViewById<View>(R.id.lvData) as ListView
        adapter = context?.let { FavoritePlaceAdapter(lsPlace, it) }
        lsView?.adapter = adapter
        adapter!!.notifyDataSetChanged()
        lsView?.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            val favoritePlace = lsPlace[i]
            if (favoritePlace.getLatLng() == null) {
                try {

                    val intent: Intent = Intent(context,PlaceAutocompleteActivity::class.java)
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_EDIT)
                } catch (e: GooglePlayServicesRepairableException) {
                    e.printStackTrace()
                } catch (e: GooglePlayServicesNotAvailableException) {
                    e.printStackTrace()
                }
                position = i
            } else {
                val myIntent = Intent(activity, FindPathActivity::class.java)
                myIntent.putExtra("id", favoritePlace.getId())
                myIntent.putExtra("name", favoritePlace.getAddress())
                myIntent.putExtra("lat", favoritePlace.getLatLng()!!.latitude)
                myIntent.putExtra("lng", favoritePlace.getLatLng()!!.longitude)
                activity!!.startActivity(myIntent)
            }
        }
        return view
    }


    fun creatData() {
        val prefs = activity!!.getSharedPreferences("MyShare", Context.MODE_PRIVATE)
        stData = prefs.getString("stData", "")
        val listType: Type = object : TypeToken<ArrayList<FavoritePlace?>?>() {}.type
        if (stData.equals("", ignoreCase = true)) {
            lsPlace = ArrayList<FavoritePlace>()
            lsPlace.add(FavoritePlace("Nhà", Constant.TYPE_HOME))
            lsPlace.add(FavoritePlace("Công ty", Constant.TYPE_COMPANY))
            stData = Gson().toJson(lsPlace, listType)
            prefs.edit().putString("stData", stData).commit()
        } else {
            lsPlace = Gson().fromJson(stData, listType)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        val prefs = activity?.getSharedPreferences("MyShare", Context.MODE_PRIVATE)
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                val place = PlaceAutocomplete.getPlace(activity, data)
//                val myIntent = Intent(activity, FindPathActivity::class.java)
//                myIntent.putExtra("id", place.id.toString())
//                myIntent.putExtra("name", place.name.toString())
//                myIntent.putExtra("lat", place.latLng.latitude)
//                myIntent.putExtra("lng", place.latLng.longitude)
//                activity!!.startActivity(myIntent)
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                val status = PlaceAutocomplete.getStatus(
//                    activity, data
//                )
//            }
//        }
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_EDIT) {
//            if (resultCode == Activity.RESULT_OK) {
//                val place = PlaceAutocomplete.getPlace(activity, data)
//                if (position == 1) {
//                    lsPlace[position] = FavoritePlace(
//                        "Công ty",
//                        place.address.toString()
//                            .substring(0, place.address.toString().indexOf(", ")),
//                        place.id, place.latLng, Constant.TYPE_COMPANY
//                    )
//                } else lsPlace[position] = FavoritePlace(
//                    "Nhà",
//                    place.address.toString().substring(0, place.address.toString().indexOf(", ")),
//                    place.id, place.latLng, Constant.TYPE_HOME
//                )
//                adapter!!.notifyDataSetChanged()
//                val type = object : TypeToken<java.util.ArrayList<FavoritePlace?>?>() {}.type
//                stData = Gson().toJson(lsPlace, type)
//                prefs.edit().putString("stData", stData).commit()
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                val status = PlaceAutocomplete.getStatus(
//                    activity, data
//                )
//            }
//        }
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_ADD) {
//            if (resultCode == Activity.RESULT_OK) {
//                val place = PlaceAutocomplete.getPlace(activity, data)
//                val favoritePlace = FavoritePlace(
//                    place.name.toString(),
//                    place.address.toString().substring(0, place.address.toString().indexOf(", ")),
//                    place.id, place.latLng, Constant.TYPE_OTHER
//                )
//                lsPlace.add(favoritePlace)
//                adapter!!.notifyDataSetChanged()
//                val type = object : TypeToken<java.util.ArrayList<FavoritePlace?>?>() {}.type
//                stData = Gson().toJson(lsPlace, type)
//                prefs.edit().putString("stData", stData).commit()
//            }
//        }
    }

}