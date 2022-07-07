package com.rkr.androidassignment.data.model


import androidx.annotation.Keep

@Keep
data class IpDetailModel(
    var ip: String? = null,
    var hostname: String? = null,
    var city: String? = null,
    var region: String? = null,
    var country: String? = null,
    var loc: String? = null,
    var org: String? = null,
    var postal: String? = null,
    var timezone: String? = null,
    var readme: String? = null
)
