package hu.bme.aut.android.diggerdoggo.data

import androidx.room.*

@Dao
interface UpgradeItemDao {
    @Query("SELECT * FROM upgrades")
    fun getAll(): List<UpgradeItem>

    @Insert
    fun insert(upgradeItem: UpgradeItem): Long

    @Update(entity = UpgradeItem::class)
    fun update(upgradeItem: UpgradeItem)

    @Delete
    fun deleteItem(upgradeItem: UpgradeItem)
}