package com.sample.optionmenu

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var customOptionMenuWidget : CustomOptionMenuWidget?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customOptionMenuWidget = CustomOptionMenuWidget.createCustomOptionMenuWidget(this, R.id.title_right_btn)
        customOptionMenuWidget!!.addItem(CustomOptionMenuItem.OptionMenuType.COPY, View.OnClickListener {

        })
        customOptionMenuWidget!!.addItem(CustomOptionMenuItem.OptionMenuType.CUT, View.OnClickListener {

        })
        customOptionMenuWidget!!.addItem(CustomOptionMenuItem.OptionMenuType.PASTE, View.OnClickListener {

        })

        optionMenuFold.setOnClickListener {
            customOptionMenuWidget!!.onFold()
        }

        optionMenuUnFold.setOnClickListener {
            customOptionMenuWidget!!.onUnfold()
        }

    }
}
