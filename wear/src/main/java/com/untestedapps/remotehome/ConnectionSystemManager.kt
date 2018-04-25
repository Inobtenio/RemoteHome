package com.untestedapps.remotehome
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import com.github.kittinunf.fuel.core.Response
import com.untestedapps.common.APIRequest
import com.untestedapps.common.ConnectionSystemManager as CommonConnectionSystemManager

class ConnectionSystemManager : CommonConnectionSystemManager() {

    private fun onSite(host: String, path: String, method: String): Response {
        return APIRequest.performActualCall(host, path, method)
    }

    override fun setupCallSystems(){
        super.setupCallSystems()
        callSystems["local"] = { host: String, path: String, method: String -> onSite(host, path, method) }
        callSystems["remote"] = { host: String, path: String, method: String -> onSite(host, path, method) }
    }

    override fun connectivityTypeVerification(context: Context):String {

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (manager.activeNetworkInfo != null ){
            if (manager.activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE){
                "remote"
            } else {
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                if (ssidList.contains(wifiManager.connectionInfo.ssid.replace("\"", ""))){
                    "local"
                } else {
                    "remote"
                }
            }
        } else {
            "undefined"
        }
    }
}