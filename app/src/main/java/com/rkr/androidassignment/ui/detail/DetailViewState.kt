package com.rkr.androidassignment.ui.detail

import com.rkr.androidassignment.data.model.IpDetailModel

sealed interface DetailViewState

object DetailViewLoadingState : DetailViewState
object DetailViewErrorState : DetailViewState
data class DetailViewLoadedState(val ipDetailModel: IpDetailModel) : DetailViewState
