package com.untestedapps.remotehome

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.github.kittinunf.fuel.core.Response
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAmbientEnabled() // Enables Always-on
        APIRequest.setup(applicationContext)

        relativeLayout{
            val toggle_button = button("Toggle")

            toggle_button.onClick {
                callService()
            }
        }


    }

    private fun callService(){
        doAsync {
            val response = APIRequest.call("POST", getString(R.string.toggle_lights))
            uiThread {
                showResponse(response)
            }
        }
    }

    private fun showResponse(response: Response){
        toast(response.httpStatusCode.toString())
    }
}
