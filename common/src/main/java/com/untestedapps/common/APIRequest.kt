package com.untestedapps.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import java.util.HashMap

/**
 * Created by kevin on 4/23/18.
 */
class APIRequest {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private
        lateinit var context: Context

        fun setup(context: Context){
            this.context = context
            FuelManager().addRequestInterceptor(requestInterceptor())
        }

        fun performActualCall(host: String, path: String, method: String): Response {
            return FuelManager().request(Method.valueOf(method), "$host/$path").response().second
        }

        private fun requestInterceptor() = {
            next: (Request) -> Request ->
            { req: Request ->
                req.authenticate(context.getString(R.string.api_username), context.getString(R.string.api_password))
                next(req)
            }
        }
    }
}