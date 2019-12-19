package com.sample.optionmenu

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var customOptionMenuWidget : CustomOptionMenuWidget?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customOptionMenuWidget = CustomOptionMenuWidget.createCustomOptionMenuWidget(this, R.id.title_right_btn)
        customOptionMenuWidget!!.addItem(OptionMenuType.COPY, View.OnClickListener {
            Toast.makeText(this, "COPY", Toast.LENGTH_SHORT).show()
        })
        customOptionMenuWidget!!.addItem(OptionMenuType.CUT, View.OnClickListener {
            Toast.makeText(this, "CUT", Toast.LENGTH_SHORT).show()
        })
        customOptionMenuWidget!!.addItem(OptionMenuType.PASTE, View.OnClickListener {
            Toast.makeText(this, "PASTE", Toast.LENGTH_SHORT).show()
        })

        optionMenuFold.setOnClickListener {
            customOptionMenuWidget!!.onFold()
        }

        optionMenuUnFold.setOnClickListener {
            customOptionMenuWidget!!.onUnfold()
        }

    }
}
