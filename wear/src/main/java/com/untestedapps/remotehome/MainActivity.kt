package com.untestedapps.remotehome

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.github.kittinunf.fuel.core.Response
import com.untestedapps.common.APIRequest
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAmbientEnabled() // Enables Always-on

        relativeLayout{
            val toggleButton = button("Toggle")

            toggleButton.onClick {
                callService()
            }
        }


    }

    private fun callService(){
        doAsync {
            val response = ConnectionSystemManager().call(applicationContext, getString(R.string.toggle_lights), "POST")
            uiThread {
                showResponse(response)
            }
        }
    }

    private fun showResponse(response: Response){
        toast(response.httpStatusCode.toString())
    }
}
