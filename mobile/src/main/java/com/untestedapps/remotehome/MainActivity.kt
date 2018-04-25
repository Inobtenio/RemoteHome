package com.untestedapps.remotehome

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.untestedapps.common.APIRequest
import com.github.kittinunf.fuel.core.Response
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val message = if (response.httpStatusCode != 200){
            response.toString()
        } else {
            response.httpStatusCode.toString()
        }
        toast(message)
    }
}
