package com.rkr.androidassignment.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkr.androidassignment.data.IpAddressRepository
import com.rkr.androidassignment.data.model.IpDetailModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val ipAddressRepository: IpAddressRepository
) : ViewModel() {

    private val _detailViewState = MutableStateFlow<DetailViewState>(DetailViewLoadingState)
    val detailViewState: StateFlow<DetailViewState> = _detailViewState

    init {
        getIpDetails()
    }

    private fun getIpDetails() {
        viewModelScope.launch {
            val ipDetailModel = ipAddressRepository.getDetailsOfCurrentIpAddress()
            _detailViewState.emit(ipDetailModel?.let {
                DetailViewLoadedState(ipDetailModel)
            } ?: DetailViewErrorState)
        }
    }

    fun getIpDetailModel(detailViewState: DetailViewState): IpDetailModel? {
        return if (detailViewState is DetailViewLoadedState) {
            detailViewState.ipDetailModel
        } else {
            null
        }
    }

    fun isLoading(detailViewState: DetailViewState) = detailViewState is DetailViewLoadingState

    fun isError(detailViewState: DetailViewState) = detailViewState is DetailViewErrorState

}
