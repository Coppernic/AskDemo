package fr.coppernic.demos.ask

interface MainPresenter {
    fun setUp()
    fun dispose()
    fun initRfid()
    fun startPolling()
}