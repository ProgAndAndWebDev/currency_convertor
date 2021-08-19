package com.example.currencyconvertor.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.roundToInt

class HomeViewModel :ViewModel() {

    val valueU= MutableStateFlow<String>("1")

    val valueQ = MutableStateFlow<String>("1460")

    val conn=MediatorLiveData<String>().apply {

        addSource(valueU.asLiveData()){ newValueString->
            valueQ.updateValue(newValueString,Double::convertToDinar)
        }

        addSource(valueQ.asLiveData()){ newValueString->
            valueU.updateValue(newValueString){
                div(1460.0)
            }
        }

    }

    private fun MutableStateFlow<String>.updateValue(newValueU: String,operation:Double.()->Double){
        newValueU.toDoubleOrNull()?.operation()?.roundTwoPlaces()?.let {newValue->
            checkIfChange(this,newValue)?.let {
                postValue(it,newValue)
            }
        }
    }

    private fun postValue(liveData: MutableStateFlow<String>, newValue: String) {
        viewModelScope.launch { liveData.emit(newValue) }
    }

    private fun checkIfChange(liveData: MutableStateFlow<String>,newValue:String) =
        liveData.takeIf {
            it.value.toDoubleOrNull()?.roundToInt() !=
                newValue.toDoubleOrNull()?.roundToInt()
        }


}

fun  Double.convertToDinar()=this*1460

fun Double.roundTwoPlaces(): String = DecimalFormat("#.####").format(this)
