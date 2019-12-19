package com.sample.optionmenu

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * 커스텀 옵션 아이템을 추가, 위치, 기능들을 관리 하고 보여주는 클래스
 *
 * @author tmkwon
 */
class CustomOptionMenuWidget : LinearLayout {


    companion object {
        /**
         * 커스텀 뷰 위젯 생성 함수
         *
         * @ param Activity 액티비티 내에 배경 되는 뷰를 찾기 위한 파라미터
         * @ param Int 위치 또는 클릭을 위한 뷰의 아이디 지정
         * @ return CustomOptionMenuWidget, 뷰를 찾지 못하면 null 을 반환
         */
        fun createCustomOptionMenuWidget(
            activity: Activity,
            optionIconResId: Int
        ): CustomOptionMenuWidget? {

            val view = activity.findViewById(android.R.id.content) as View
            if (view is ViewGroup) {
                val baseViewGroup = view.getChildAt(0) as ViewGroup
                val optionIcon = activity.findViewById(optionIconResId) as View
                return CustomOptionMenuWidget(activity, optionIcon, baseViewGroup)
            }

            return null
        }
    }

    private val optionMenuItems: MutableList<CustomOptionMenuItem> = mutableListOf()
    private val dependenciesView: View // 기준이 되는 위치의 아이콘 뷰
    private val baseView: ViewGroup // 배경이 되는 뷰그룹

    constructor(context: Context?, dependenciesView: View, baseView: ViewGroup) : super(context) {
        this.dependenciesView = dependenciesView
        this.baseView = baseView
        this.visibility = View.INVISIBLE
        this.orientation = VERTICAL
        this.setBackgroundResource(R.drawable.option_menu_bg)
        this.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        this.dependenciesView.setOnClickListener {
            if (this.visibility == View.INVISIBLE)
                viewUnfold(this)
            else
                viewFold(this)
        }

        baseView.addView(this, layoutParams)

        this.baseView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->

            val stPosX =
                dependenciesView.x + (dependenciesView.paddingLeft + dependenciesView.width) / 2
            val stPosY = dependenciesView.y + dependenciesView.height

            this.x = stPosX - this.width
            this.y = stPosY
        }

        baseView.setOnTouchListener { _, _ ->
            if (this@CustomOptionMenuWidget.visibility == View.VISIBLE)
                viewFold(this@CustomOptionMenuWidget)

            false
        }

        attachHideOptionMenuTouchListener(baseView)
    }

    fun addItem(type: OptionMenuType, clickListener: OnClickListener?) {
        val item = CustomOptionMenuItem(context, type).apply {
            this.setOnClickListener {
                clickListener?.onClick(it)
                this.notifyClickEvent?.invoke()
            }
        }

        item.notifyClickEvent = {
            viewFold(this@CustomOptionMenuWidget)
        }

        optionMenuItems.add(item)
        addView(item)
        updateItem()
    }

    fun updateItem() {

        for ((index, value) in optionMenuItems.withIndex()) {
            value.setVisibleBorderLine(View.GONE)
            if (optionMenuItems.size > 1 && optionMenuItems.size - 1 != index) {
                value.setVisibleBorderLine(View.VISIBLE)
            }
        }

        requestLayout()

    }

    fun clear() {
        optionMenuItems.clear()
    }

    fun removeItem(index: Int) {
        optionMenuItems.removeAt(index)
    }

    fun getItem(type: OptionMenuType): CustomOptionMenuItem? {
        for (i in 0 until optionMenuItems.size) {
            if (optionMenuItems.get(i).type == type) {
                return optionMenuItems.get(i)
            }
        }

        return null
    }

    override fun removeDetachedView(child: View?, animate: Boolean) {
        super.removeDetachedView(child, animate)
    }

    override fun addOnAttachStateChangeListener(listener: OnAttachStateChangeListener?) {
        super.addOnAttachStateChangeListener(listener)
    }

    fun onFold() {
        if (this@CustomOptionMenuWidget.visibility == View.VISIBLE)
            viewFold(this@CustomOptionMenuWidget)
    }

    fun onUnfold() {
        if (this@CustomOptionMenuWidget.visibility == View.INVISIBLE)
            viewUnfold(this@CustomOptionMenuWidget)
    }

    private fun viewFold(view: View) {


        val anim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        anim.duration = 250

        val animSetXY = AnimatorSet()
        animSetXY.playTogether(anim)
        animSetXY.start()
        animSetXY.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.INVISIBLE
                animSetXY.removeAllListeners()
            }

            override fun onAnimationCancel(animation: Animator?) {
                view.visibility = View.INVISIBLE
                animSetXY.removeAllListeners()
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
    }

    private fun viewUnfold(view: View) {
        val anim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        anim.duration = 250

        val animSetXY = AnimatorSet()
        animSetXY.play(anim)
        animSetXY.start()
        animSetXY.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                animSetXY.removeAllListeners()
            }

            override fun onAnimationCancel(animation: Animator?) {
                animSetXY.removeAllListeners()
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

        view.visibility = View.VISIBLE
    }

    private fun attachHideOptionMenuTouchListener(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {

            val child = viewGroup.getChildAt(i)

            if (child is ViewGroup) {
                attachHideOptionMenuTouchListener(child)
            } else {
                if (dependenciesView == child) {
                    continue
                }

                child.setOnTouchListener { _, _ ->
                    if (this@CustomOptionMenuWidget.visibility == View.VISIBLE)
                        viewFold(this@CustomOptionMenuWidget)

                    return@setOnTouchListener false
                }
            }
        }
    }


}