package fr.coppernic.demos.ask.rfid

import android.content.Context
import android.util.Log
import fr.coppernic.sdk.ask.*
import fr.coppernic.sdk.utils.core.CpcDefinitions
import fr.coppernic.sdk.utils.io.InstanceListener

class AskInteractor(val context: Context):RfidInteractor {
    private val TAG = "AskInteractor"
    private lateinit var reader: Reader

    override fun setUp(rfidListener: RfidInteractor.RfidListener) {
        Reader.getInstance(context, object : InstanceListener<Reader> {
            override fun onDisposed(p0: Reader) {

            }

            override fun onCreated(p0: Reader) {
                reader = p0
                initReader()
                rfidListener.onSetUp()
            }
        })
    }

    override fun dispose() {
        reader.cscClose()
    }

    override fun startPolling(readerListener: ReaderListener) {
        // Sets the card detection
        val search = sCARD_SearchExt()
        search.OTH = 1
        search.CONT = 0
        search.INNO = 1
        search.ISOA = 1
        search.ISOB = 1
        search.MIFARE = 1
        search.MONO = 1
        search.MV4k = 1
        search.MV5k = 1
        search.TICK = 1
        val mask = Defines.SEARCH_MASK_INNO or Defines.SEARCH_MASK_ISOA or Defines.SEARCH_MASK_ISOB or Defines.SEARCH_MASK_MIFARE or Defines.SEARCH_MASK_MONO or Defines.SEARCH_MASK_MV4K or Defines.SEARCH_MASK_MV5K or Defines.SEARCH_MASK_TICK or Defines.SEARCH_MASK_OTH
        val parameters = SearchParameters(search, mask, 0x01.toByte(), 0x00.toByte())
        // Starts card detection
        reader.startDiscovery(parameters, readerListener)
    }

    override fun stopPolling() {
        reader.stopDiscovery()
    }

    fun initReader() {
        reader.cscOpen(CpcDefinitions.ASK_READER_PORT, 115200, false)
        val sb = StringBuilder()
        reader.cscVersionCsc(sb)
        Log.d(TAG, sb.toString())
    }
}