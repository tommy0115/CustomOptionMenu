package com.sample.optionmenu

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

typealias NotifyClickEvent = ()-> Unit

/**
 * 커스텀 옵션 메뉴의 각 아이템을 정의한 클래스
 * @author tmkwon
 */
class CustomOptionMenuItem : FrameLayout {

    val view: View
    var type: OptionMenuType = OptionMenuType.NONE
        set(value) {
            field = value

            var icon: Int
            var text: String

            when (field) {

                OptionMenuType.COPY -> {
                    icon = R.drawable.ic_content_copy_black_24dp
                    text = context.resources.getString(R.string.copy)
                }

                OptionMenuType.CUT -> {
                    icon = R.drawable.ic_content_cut_black_24dp
                    text = context.resources.getString(R.string.cut)
                }

                OptionMenuType.PASTE -> {
                    icon = R.drawable.ic_content_paste_black_24dp
                    text = context.resources.getString(R.string.paste)
                }

                else -> return
            }

            view.findViewById<ImageView>(R.id.option_menu_icon).setImageResource(icon)
            view.findViewById<TextView>(R.id.option_menu_text).text = text
        }

    var notifyClickEvent: NotifyClickEvent? = null

    constructor(context: Context, type: OptionMenuType) : super(context) {
        this.view = View.inflate(context, R.layout.custom_option_menu_item, null)
        this.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.type = type
        addView(view)
    }

    fun setVisibleBorderLine(visibility: Int) {
        when (visibility) {
            View.VISIBLE -> {
                view.findViewById<TextView>(R.id.option_menu_text).setBackgroundResource(R.drawable.option_bottom_line)
            }

            View.INVISIBLE, View.GONE -> {
                view.findViewById<TextView>(R.id.option_menu_text).background = null
            }

            else -> {
                view.findViewById<TextView>(R.id.option_menu_text).background = null
            }
        }
    }

}