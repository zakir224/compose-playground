package com.learn.composesample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VehicleViewModel: ViewModel() {

    private var dummyList = mutableMapOf<Int, Vehicle>()

    fun removeVehicle(vehicle: Vehicle) {
        dummyList.remove(vehicle.id)
        _vehicle.postValue(dummyList.values.toList())
    }

    fun followVehicle(vehicle: Vehicle) {
        dummyList[vehicle.id] = vehicle.copy(followed = vehicle.followed.not())
        _vehicle.postValue(dummyList.values.toList())
    }

    private val _vehicle = MutableLiveData<List<Vehicle>>()

    val vehicle : LiveData<List<Vehicle>> = _vehicle

    init {
        dummyList[1] = Vehicle(make = "Honda", model = "Accord", year = "2001",listPrice = 45000f, id= 1)
        dummyList[2] = Vehicle(make = "Honda", model = "Accord", year = "2002",listPrice = 50000f, id= 2)
        dummyList[3] = Vehicle(make = "Honda", model = "Accord", year = "2003",listPrice = 50000f, id= 3)
        dummyList[4] = Vehicle(make = "Honda", model = "Accord", year = "2004",listPrice = 20000f, id= 4)
        dummyList[5] = (Vehicle(make = "Honda", model = "Accord", year = "2005",listPrice = 15000f, id= 5))
        dummyList[6] = (Vehicle(make = "Honda", model = "Accord", year = "2006",listPrice = 45455f, id= 6))
        dummyList[7] = (Vehicle(make = "Honda", model = "Accord", year = "2007",listPrice = 45455f, id= 7))
        dummyList[8] = (Vehicle(make = "Honda", model = "Accord", year = "2008",listPrice = 50000f, id= 8))
        dummyList[9] = (Vehicle(make = "Honda", model = "Accord", year = "2009",listPrice = 50000f, id= 9))
        dummyList[10] = (Vehicle(make = "Honda", model = "Accord", year = "2010",listPrice = 45455f, id= 10))
        dummyList[11] = (Vehicle(make = "Honda", model = "Accord", year = "2011",listPrice = 15000f, id= 11))
        dummyList[12] = (Vehicle(make = "Honda", model = "Accord", year = "2012",listPrice = 45455f, id= 12))
        dummyList[13] = (Vehicle(make = "Honda", model = "Accord", year = "2013",listPrice = 20000f, id= 13))
        _vehicle.postValue(dummyList.values.toList())
    }
}