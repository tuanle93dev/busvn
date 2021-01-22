package com.example.busvn.objects

import com.google.android.gms.maps.model.LatLng

class FavoritePlace {

    private var name: String? = null
    private var address: String? = null
    private var id: String? = null
    private var latLng: LatLng? = null
    private var type = 0

    constructor(name: String?, type: Int) {
        this.name = name
        this.type = type
    }


    constructor(name: String?, address: String?, id: String?, latLng: LatLng?, type: Int) {
        this.name = name
        this.address = address
        this.id = id
        this.latLng = latLng
        this.type = type
    }
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getLatLng(): LatLng? {
        return latLng
    }

    fun setLatLng(latLng: LatLng?) {
        this.latLng = latLng
    }

    fun getType(): Int {
        return type
    }

    fun setType(type: Int) {
        this.type = type
    }
}