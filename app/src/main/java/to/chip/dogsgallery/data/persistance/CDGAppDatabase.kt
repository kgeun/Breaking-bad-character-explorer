package to.chip.dogsgallery.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import to.chip.dogsgallery.data.model.ui.CDGBreedItem
import to.chip.dogsgallery.data.model.ui.CDGImageItem

@Database(
    entities = [CDGBreedItem::class, CDGImageItem::class],
    version = 1,
    exportSchema = false
)
abstract class CDGAppDatabase : RoomDatabase() {
    abstract fun CDGMainDao(): CDGMainDao
}