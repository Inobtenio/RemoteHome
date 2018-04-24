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
