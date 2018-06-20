package fr.coppernic.demos.ask

import fr.coppernic.demos.ask.power.PowerInteractor
import fr.coppernic.demos.ask.rfid.RfidInteractor
import fr.coppernic.sdk.ask.ReaderListener
import fr.coppernic.sdk.ask.RfidTag
import fr.coppernic.sdk.power.api.PowerListener
import fr.coppernic.sdk.power.api.peripheral.Peripheral
import fr.coppernic.sdk.utils.core.CpcBytes
import fr.coppernic.sdk.utils.core.CpcResult

class MainPresenterImpl(val view:MainView, val powerInteractor:PowerInteractor, val rfidInteractor:RfidInteractor):MainPresenter, PowerListener, ReaderListener {

    private lateinit var prevRfidTag:RfidTag
    private var count = 0

    /**
     * Sets up presenter
     */
    override fun setUp() {
        powerInteractor.setUp(this)
    }

    /**
     * Disposes presenter
     */
    override fun dispose() {
        // Stops discover
        rfidInteractor.stopPolling()
        // Power off reader
        powerInteractor.power(false)
        // Disposes power
        powerInteractor.dispose()
        // Disposes reader
        rfidInteractor.dispose()
    }

    /**
     * Initializes RFID reader
     */
    override fun initRfid() {
        powerInteractor.power(true)
    }

    /**
     * RFID reader power up listener
     */
    override fun onPowerUp(p0: CpcResult.RESULT?, p1: Peripheral?) {
        rfidInteractor.setUp(object : RfidInteractor.RfidListener {
            override fun onSetUp() {
                rfidInteractor.startPolling(this@MainPresenterImpl)
            }
        })
    }

    /**
     * RFID reader power down listener
     */
    override fun onPowerDown(p0: CpcResult.RESULT?, p1: Peripheral?) {

    }

    /**
     * Discovery stopped listener
     */
    override fun onDiscoveryStopped() {

    }

    /**
     * Tag discovered listener
     */
    override fun onTagDiscovered(rfidTag: RfidTag) {
        try {
            if (CpcBytes.arrayCmp(prevRfidTag.atr, rfidTag.atr) && prevRfidTag.communicationMode == rfidTag.communicationMode) {
                count++
            } else {
                prevRfidTag = rfidTag
                count = 1
            }
        } catch (e:UninitializedPropertyAccessException) {
            prevRfidTag = rfidTag
            count = 1
        }
        view.showTag(rfidTag)
        view.showCount(count)
    }

    /**
     * Starts RFID polling
     */
    override fun startPolling() {
        rfidInteractor.startPolling(this)
    }
}