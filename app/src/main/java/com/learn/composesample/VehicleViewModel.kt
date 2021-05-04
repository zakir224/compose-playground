package com.learn.composesample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VehicleViewModel: ViewModel() {

    private var dummyList = linkedSetOf<Vehicle>()

    fun removeVehicle(vehicle: Vehicle) {
        dummyList.remove(vehicle)
        _vehicle.postValue(dummyList.toList())
    }

    private val _vehicle = MutableLiveData<List<Vehicle>>()

    val vehicle : LiveData<List<Vehicle>> = _vehicle

    init {
        for(i in 1..10) {
            dummyList.add(Vehicle(make = "Honda $i", model = "MDX ${i+1}", year =
            "2009",listPrice = 45455f, firstPhoto = ""))
        }
        _vehicle.postValue(dummyList.toList())
    }
}