package fr.coppernic.demos.ask

import fr.coppernic.sdk.ask.RfidTag

interface MainView {
    fun showTag(rfidTag: RfidTag)
    fun showCount(count:Int)
}