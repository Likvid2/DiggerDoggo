package hu.bme.aut.android.diggerdoggo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_scenery.*

class SceneryActivity : AppCompatActivity() {
    private var skin : Int = 0
    private var bg : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scenery)

        loadData()
        setActiveSkin()
        setActiveBg()

        Sbpctxt.text = getString(R.string.bonespertap, bonesptap)
        Sbonetxt.text = getString(R.string.bones, bones)

        btnStarterDoggo.setOnClickListener{
            skin = 0
            setActiveSkin()
        }

        btnDogeDoggo.setOnClickListener{
            skin = 1
            setActiveSkin()
        }

        btnPlushieDoggo.setOnClickListener{
            skin = 2
            setActiveSkin()
        }

        btnStarterBg.setOnClickListener{
            bg = 0
            setActiveBg()
        }

        btnFieldBg.setOnClickListener{
            bg = 1
            setActiveBg()
        }

        btnSpaceBg.setOnClickListener{
            bg = 2
            setActiveBg()
        }

        btnSDig.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        btnSUpgrades.setOnClickListener {
            val upgradeIntent = Intent(this, UpgradeActivity::class.java)
            startActivity(upgradeIntent)
        }
    }

    private fun saveState() {
        val sharedPreferences = getSharedPreferences("skinPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putInt("SKIN_KEY", skin)
            putInt("BG_KEY", bg)
        }.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("skinPrefs", Context.MODE_PRIVATE)
        skin = sharedPreferences.getInt("SKIN_KEY", 0)
        bg = sharedPreferences.getInt("BG_KEY", 0)
    }

    private fun setActiveSkin() {
        when(skin) {
            0 -> {
                btnStarterDoggo.setBackgroundResource(R.drawable.roundedcornerselected)
                btnDogeDoggo.setBackgroundResource(R.drawable.roundedcorner)
                btnPlushieDoggo.setBackgroundResource(R.drawable.roundedcorner)
            }
            1 -> {
                btnStarterDoggo.setBackgroundResource(R.drawable.roundedcorner)
                btnDogeDoggo.setBackgroundResource(R.drawable.roundedcornerselected)
                btnPlushieDoggo.setBackgroundResource(R.drawable.roundedcorner)
            }
            2 -> {
                btnStarterDoggo.setBackgroundResource(R.drawable.roundedcorner)
                btnDogeDoggo.setBackgroundResource(R.drawable.roundedcorner)
                btnPlushieDoggo.setBackgroundResource(R.drawable.roundedcornerselected)
            }
        }
    }

    private fun setActiveBg() {
        when(bg) {
            0 -> {
                btnStarterBg.setBackgroundColor(Color.LTGRAY)
                btnFieldBg.setBackgroundColor(Color.BLACK)
                btnSpaceBg.setBackgroundColor(Color.BLACK)
            }
            1 -> {
                btnStarterBg.setBackgroundColor(Color.BLACK)
                btnFieldBg.setBackgroundColor(Color.LTGRAY)
                btnSpaceBg.setBackgroundColor(Color.BLACK)
            }
            2 -> {
                btnStarterBg.setBackgroundColor(Color.BLACK)
                btnFieldBg.setBackgroundColor(Color.BLACK)
                btnSpaceBg.setBackgroundColor(Color.LTGRAY)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveState()
    }

    override fun onStop() {
        super.onStop()
        saveState()
    }
}