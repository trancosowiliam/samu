package br.com.jwk.samu.base

interface BasePresenter<T> {
    var view:T

    operator fun invoke(view: T) {
        this.view = view
    }
}