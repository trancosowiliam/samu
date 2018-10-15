package br.com.jwk.samu.component.conditionFloatDialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Condition
import kotlinx.android.synthetic.main.condition_float_dialog.view.*

class ConditionFloatDialog : FrameLayout {

    private var conditions = mutableListOf<Condition>()

    var onButtonClickListener: ((List<Condition>) -> Unit)? = null

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
        View.inflate(context, R.layout.condition_float_dialog, this)
        cfdImgArrow.setOnClickListener {
            onButtonClickListener?.invoke(conditions)
        }
    }

    fun addCondition(condition: Condition) {
        conditions.add(condition)
        cfdConditions.text = conditions.joinToString(", ") { it.name }
    }

    fun removeCondition(condition: Condition) {
        conditions.remove(condition)
        cfdConditions.text = conditions.joinToString(", ") { it.name }
    }
}