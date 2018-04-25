package com.untestedapps.common

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.core.Response


open class ConnectionSystemManager {
    private val hosts = HashMap<String, String>()
    val callSystems = HashMap<String, (host: String, path: String, method: String) -> Response>()
    val ssidList = ArrayList<String>()

    open fun call(context: Context, path: String, method: String = "GET"): Response {
        setup(context)
        setupCallSystems()
        APIRequest.setup(context)
        return perform(connectivityTypeVerification(context), path, method)
    }

    open fun perform(origin: String, path: String, method: String): Response {
        val action = callSystems[origin]
        return action?.invoke(hosts[origin]!!, path, method)!!
    }

    open fun connectivityTypeVerification(context: Context):String{
        return "undefined"
    }

    private fun dummyResponse():Response{
        return Response()
    }

    open fun setup(context: Context){
        hosts["local"] = context.getString(R.string.localhost)
        hosts["remote"] = context.getString(R.string.remotehost)
        ssidList.add(context.getString(R.string.home_main_ssid))
        ssidList.add(context.getString(R.string.home_pi_ssid))
        ssidList.add(context.getString(R.string.home_secondary_ssid))
    }

    open fun setupCallSystems(){
        callSystems["undefined"] = { _: String, _: String, _: String -> dummyResponse() }
    }
}