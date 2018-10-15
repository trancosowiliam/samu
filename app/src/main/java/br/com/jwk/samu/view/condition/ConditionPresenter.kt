package br.com.jwk.samu.view.condition

import br.com.jwk.samu.data.model.Condition

class ConditionPresenter : ConditionContract.Presenter {

    override lateinit var view: ConditionContract.View

    override fun addCondition(condition: Condition) {
        view.addCondition(condition)
    }

    override fun removeCondition(condition: Condition) {
        view.removeCondition(condition)
    }

    override fun searchCondition(query: String) {
        view.searchCondition(query)
    }
}
