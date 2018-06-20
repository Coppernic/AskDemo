package fr.coppernic.demos.ask.rfid

import fr.coppernic.sdk.ask.ReaderListener

interface RfidInteractor {
    interface RfidListener {
        fun onSetUp()
    }
    fun setUp(rfidListener: RfidListener)
    fun dispose()
    fun startPolling(readerListener: ReaderListener)
    fun stopPolling()
}