package com.untestedapps.common

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
        val callMechs = HashMap<String, (method: String, path: String) -> Response>()
        var localApiHost = String()
        var remoteApiHost = String()
        lateinit var context: Context

        fun setup(context: Context){
            this.context = context
            setupHosts()
            setupCallMechanisms()
            FuelManager().addRequestInterceptor(requestInterceptor())
        }

        fun call(method: String, path: String, key: String = requestAgent()): Response {
            val action = callMechs.get(key)
            return action?.invoke(method, path)!!
        }

        private fun performLocally(method: String, path: String): Response {
            val full_path = "$localApiHost/$path"
            return FuelManager().request(Method.valueOf(method), full_path).response().second
        }

        private fun performOnPhone(method: String, path: String): Response {
            // Code to perform the actual call on the phone
            return Response()
        }

        private fun requestAgent() : String{
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo ?: return "remote"

            return "local"

        }

        fun requestInterceptor() = {
            next: (Request) -> Request ->
            { req: Request ->
                req.authenticate(context.getString(R.string.api_username), context.getString(R.string.api_password))
                next(req)
            }
        }

        fun setupCallMechanisms(){
            callMechs.put("local", { method: String, path: String -> performLocally(method, path) })
            callMechs.put("remote", { method: String, path: String -> performOnPhone(method, path) })
        }

        fun setupHosts(){
            localApiHost = context.getString(R.string.localhost)
            remoteApiHost = context.getString(R.string.remotehost)
        }
    }
}