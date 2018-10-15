package br.com.jwk.samu.view.condition

import br.com.jwk.samu.base.BasePresenter
import br.com.jwk.samu.base.BaseView
import br.com.jwk.samu.data.model.Condition

interface ConditionContract {
    interface Presenter : BasePresenter<View> {
        fun addCondition(condition: Condition)
        fun removeCondition(condition: Condition)
        fun searchCondition(query: String)
    }

    interface View : BaseView<Presenter> {
        fun addCondition(condition: Condition)
        fun removeCondition(condition: Condition)
        fun searchCondition(query: String)
    }
}