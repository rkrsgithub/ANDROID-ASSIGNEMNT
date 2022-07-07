package com.rkr.androidassignment.data

import com.rkr.androidassignment.data.model.IpDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.inject.Inject

/**
 * Class that handles save and retrieve devices from local data base
 */
class IpAddressDataSource @Inject constructor() {

    suspend fun getIpAddressDetails(): IpDetailModel? {
        return withContext(Dispatchers.IO) {
            val ip = getCurrentPublicIP()
            ip?.let {
                try {
                    val response = URL("https://ipinfo.io/$it/geo").readText()
                    val jsonObject = JSONObject(response)
                    if (jsonObject.has("ip")) {
                        IpDetailModel(
                            ip = jsonObject.optString("ip"),
                            hostname = jsonObject.optString("hostname"),
                            city = jsonObject.optString("city"),
                            region = jsonObject.optString("region"),
                            country = jsonObject.optString("country"),
                            loc = jsonObject.optString("loc"),
                            org = jsonObject.optString("org"),
                            postal = jsonObject.optString("postal"),
                            timezone = jsonObject.optString("timezone"),
                            readme = jsonObject.optString("readme")
                        )
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    private suspend fun getCurrentPublicIP(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = URL("https://api.ipify.org/?format=json").readText()
                val jsonObject = JSONObject(response)
                if (jsonObject.has("ip")) {
                    jsonObject.optString("ip")
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
