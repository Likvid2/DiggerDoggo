package hu.bme.aut.android.diggerdoggo.data

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(entities = [UpgradeItem::class], version = 1)
abstract class UpgradeItemDatabase : RoomDatabase() {
    abstract fun UpgradeItemDao(): UpgradeItemDao
}