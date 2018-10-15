package br.com.jwk.samu.component.actionFloadDialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Condition
import kotlinx.android.synthetic.main.action_float_dialog.view.*
import kotlinx.android.synthetic.main.condition_float_dialog.view.*

class ActionFloatDialog : FrameLayout {

    var onButtonClickListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.action_float_dialog, this)
        afdImgArrow.setOnClickListener {
            onButtonClickListener?.invoke()
        }

        context.theme.obtainStyledAttributes(attrs, R.styleable.ActionFloatDialog, 0, 0).apply {
            try {
                afdLblAction.text = getString(R.styleable.ActionFloatDialog_action_text)
            } finally {
                recycle()
            }
        }
    }
}