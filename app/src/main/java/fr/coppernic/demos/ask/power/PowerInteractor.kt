package fr.coppernic.demos.ask.power

import fr.coppernic.sdk.power.api.PowerListener

interface PowerInteractor {
    fun setUp(listener: PowerListener)
    fun dispose()
    fun power(on:Boolean)
}