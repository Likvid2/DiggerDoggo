package hu.bme.aut.android.diggerdoggo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upgrades")
data class UpgradeItem (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "effect") val effect: Int,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "isbought") val isbought: Boolean
)