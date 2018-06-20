package fr.coppernic.demos.ask

import android.media.AudioManager
import android.media.ToneGenerator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.coppernic.demos.ask.power.ConePowerInteractor
import fr.coppernic.demos.ask.rfid.AskInteractor
import fr.coppernic.sdk.ask.RfidTag
import fr.coppernic.sdk.utils.core.CpcBytes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var presenter:MainPresenter
    private val tg:ToneGenerator by lazy {
        ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenterImpl(this, ConePowerInteractor(this), AskInteractor(this))
    }

    override fun onStart() {
        super.onStart()
        presenter.setUp()
        presenter.initRfid()
    }

    override fun onStop() {
        super.onStop()
        presenter.dispose()
    }

    /**
     * Displays tag informations on screen
     *
     * @param rfidTag Tag to be displayed
     */
    override fun showTag(rfidTag: RfidTag) {
        runOnUiThread {
            beepGood()
            tvAtr.text = CpcBytes.byteArrayToString(rfidTag.atr, rfidTag.atr.size, false)
            tvType.text = rfidTag.communicationMode.toString()
        }

        presenter.startPolling()
    }

    /**
     * Displays read count
     *
     * @param count Read count
     */
    override fun showCount(count: Int) {
        runOnUiThread {
            tvCount.text = count.toString()
        }
    }

    /**
     * Plays a beep
     */
    private fun beepGood() {
        try {
            tg.startTone(ToneGenerator.TONE_PROP_BEEP)
        } catch (e1: Exception) {

        }
    }
}
