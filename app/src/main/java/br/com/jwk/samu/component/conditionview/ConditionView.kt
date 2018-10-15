package br.com.jwk.samu.component.conditionview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Condition
import br.com.jwk.samu.extension.isVisible
import kotlinx.android.synthetic.main.condition_view.view.*

class ConditionView : FrameLayout {

    private lateinit var condition: Condition

    var isLocked: Boolean = false
        set(value) {
            field = value
            if (value)
                isSelected = true
        }

    var onButtonClickListener: ((condition: Condition, selected: Boolean) -> Unit)? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.condition_view, this)
        this.setOnClickListener {
            if (!isLocked) {
                isSelected = !isSelected
                onButtonClickListener?.invoke(condition, isSelected)
            }
        }
    }

    fun populate(condition: Condition?) {
        condition?.let {
            this.condition = it
            txtCvName.text = it.name
            cvImgCondition.setBackgroundResource(it.resDrawable)
        }

        isActivated = condition != null
    }
}