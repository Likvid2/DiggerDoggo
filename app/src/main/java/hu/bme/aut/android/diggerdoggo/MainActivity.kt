package hu.bme.aut.android.diggerdoggo

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*


var bones = 0
var bonesptap = 1


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        loadState()

        btnDog.setOnClickListener{
            bones += bonesptap
            Mbonetxt.text = getString(R.string.bones, bones)
        }

        btnMUpgrades.setOnClickListener {
            saveState()
            val upgradeIntent = Intent(this, UpgradeActivity::class.java)
            startActivity(upgradeIntent)
        }

        btnMScenery.setOnClickListener {
            saveState()
            val sceneryIntent = Intent(this, SceneryActivity::class.java)
            startActivity(sceneryIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadState()

    }

    private fun saveState() {
        val sharedPreferences = getSharedPreferences("bonePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putInt("BONES_KEY", bones)
            putInt("BONESPTAP_KEY", bonesptap)
        }.apply()
    }

    private fun loadState() {
        val sharedbPreferences = getSharedPreferences("bonePrefs", Context.MODE_PRIVATE)
        bones = sharedbPreferences.getInt("BONES_KEY", 0)
        bonesptap = sharedbPreferences.getInt("BONESPTAP_KEY", 1)
        Mbpctxt.text = getString(R.string.bonespertap, bonesptap)
        Mbonetxt.text = getString(R.string.bones, bones)
        val sharedPreferences = getSharedPreferences("skinPrefs", Context.MODE_PRIVATE)
        when(sharedPreferences.getInt("SKIN_KEY", 0)) {
            0 -> btnDog.setImageResource(R.drawable.skinstarter)
            1 -> btnDog.setImageResource(R.drawable.skindoge)
            2 -> btnDog.setImageResource(R.drawable.skinplush)
        }

        val mainbg = findViewById<ConstraintLayout>(R.id.MainBg)

        when(sharedPreferences.getInt("BG_KEY", 0)) {
            0 -> mainbg.setBackgroundResource(R.drawable.bgstarter)
            1 -> mainbg.setBackgroundResource(R.drawable.bgfield)
            2 -> mainbg.setBackgroundResource(R.drawable.bgspace)
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