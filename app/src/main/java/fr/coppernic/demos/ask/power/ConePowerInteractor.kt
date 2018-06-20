package fr.coppernic.demos.ask.power

import android.content.Context
import fr.coppernic.sdk.power.PowerManager
import fr.coppernic.sdk.power.api.PowerListener
import fr.coppernic.sdk.power.impl.cone.ConePeripheral

class ConePowerInteractor(val context: Context):PowerInteractor {
    override fun setUp(listener:PowerListener) {
        PowerManager.get().registerListener(listener)
    }

    override fun dispose() {
        PowerManager.get().unregisterAll()
        PowerManager.get().releaseResources()
    }

    /**
     * Powers on/off RFID reader
     *
     * @param on
     */
    override fun power(on: Boolean) {
        if(on) {
            ConePeripheral.RFID_ASK_UCM108_GPIO.on(context)
        } else {
            ConePeripheral.RFID_ASK_UCM108_GPIO.off(context)
        }
    }
}