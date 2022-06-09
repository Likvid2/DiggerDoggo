package hu.bme.aut.android.diggerdoggo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import hu.bme.aut.android.diggerdoggo.adapter.UpgradeAdapter
import hu.bme.aut.android.diggerdoggo.data.UpgradeItem
import hu.bme.aut.android.diggerdoggo.data.UpgradeItemDatabase
import kotlinx.android.synthetic.main.activity_upgrade.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates

lateinit var database: UpgradeItemDatabase

class UpgradeActivity : AppCompatActivity(), UpgradeAdapter.BonesListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UpgradeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upgrade)

        Ubpctxt.text = getString(R.string.bonespertap, bonesptap)
        Ubonetxt.text = getString(R.string.bones, bones)

        database = Room.databaseBuilder(
            applicationContext,
            UpgradeItemDatabase::class.java,
            "upgrades"
        ).build()

        firstRun()

        initRecyclerView()



        btnUDig.setOnClickListener {
            saveState()
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        btnUScenery.setOnClickListener {
            saveState()
            val sceneryIntent = Intent(this, SceneryActivity::class.java)
            startActivity(sceneryIntent)
        }

    }

    private fun initRecyclerView() {
        recyclerView = MainRecyclerView
        adapter = UpgradeAdapter(this, this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.UpgradeItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onBonesChanged() {
        Ubpctxt.text = getString(R.string.bonespertap, bonesptap)
        Ubonetxt.text = getString(R.string.bones, bones)
        saveState()
    }

    private fun addUpgradeItem(
        uicon: String,
        uname: String,
        udescription: String,
        ueffect: Int,
        uprice: Int
    ) {
        thread {
            val newItem = UpgradeItem(
                id = null,
                icon = uicon,
                name = uname,
                description = udescription,
                effect = ueffect,
                price = uprice,
                isbought = false
            )
            val newId = database.UpgradeItemDao().insert(newItem)
            val newUpgradeItem = newItem.copy(
                id = newId
            )
            runOnUiThread {
                adapter.addItem(newUpgradeItem)
            }
        }
    }

    private fun initDatabase() {
        thread {
            database.clearAllTables()
            addUpgradeItem(
                "R.drawable.bowl",
                "bowl",
                "First let's get something to eat from.",
                1,
                50
            )
            Thread.sleep(10)
            addUpgradeItem(
                "R.drawable.pedigrii",
                "pedigrii",
                "We also need something to actually like... you know... eat? .",
                2,
                200
            )
            Thread.sleep(10)
            addUpgradeItem(
                "R.drawable.fantasticks",
                "fantasticks",
                "Gotta take good care of those teeth. Not like that influenced your digging skills...",
                4,
                500
            )
            Thread.sleep(10)
            addUpgradeItem(
                "R.drawable.basictoy",
                "basic toy",
                "Once in a while between all this digging, we also gotta have some fun right?",
                10,
                1200
            )
            Thread.sleep(10)
            addUpgradeItem(
                "R.drawable.ball",
                "ball",
                "Even more fun! Although you should really be focusing on the digging thing.",
                20,
                2500
            )
            Thread.sleep(10)
            addUpgradeItem(
                "R.drawable.squeakychicken",
                "squeaky chicken",
                "An annoying rubber chicken, that makes u wanna dig faster.",
                50,
                6500
            )
            Thread.sleep(10)
            addUpgradeItem(
                "R.drawable.angryplushie",
                "angry plushie",
                "It's super cute, but oh boy is it judging you hard.",
                100,
                12000
            )
            Thread.sleep(10)
            addUpgradeItem(
                "R.drawable.strangemushroom",
                "strange mushroom",
                "Don't eat that, Bad dog!",
                1000,
                25000
            )
        }
    }

    private fun resetGame() {
        initDatabase()
        thread {
            val sharedPreferences = getSharedPreferences("skinPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply {
                putInt("SKIN_KEY", 0)
                putInt("BG_KEY", 0)
            }.apply()
        }
        bones = 0
        bonesptap = 1
        Ubpctxt.text = getString(R.string.bonespertap, bonesptap)
        Ubonetxt.text = getString(R.string.bones, bones)
        saveState()
        Thread.sleep(100)
        loadItemsInBackground()
    }

    fun showPopUp(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.reset_menu, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnreset -> {
                    Toast.makeText(
                        this,
                        "The game has been reset",
                        Toast.LENGTH_SHORT
                    ).show()
                    resetGame()
                }
            }
            true
        }
    }

    private fun saveState() {
        val sharedPreferences = getSharedPreferences("bonePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putInt("BONES_KEY", bones)
            putInt("BONESPTAP_KEY", bonesptap)
        }.apply()
    }

    private fun firstRun() {
        val sharedPreferences = getSharedPreferences("bonePrefs", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("FIRSTRUN_KEY", true)) {
            initDatabase()
            val editor = sharedPreferences.edit()
            editor.apply {
                putBoolean("FIRSTRUN_KEY", false)
            }.apply()
        }
    }

    override fun onStop() {
        super.onStop()
        saveState()
    }

}