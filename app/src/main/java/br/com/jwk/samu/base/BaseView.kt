package br.com.jwk.samu.base

interface BaseView<out T : BasePresenter<*>> {
    val presenter: T
}