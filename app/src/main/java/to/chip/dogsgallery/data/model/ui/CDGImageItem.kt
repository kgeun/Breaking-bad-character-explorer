package to.chip.dogsgallery.data.model.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogImage")
data class CDGImageItem(
    @PrimaryKey var imageId: Long? = 0,
    var breedName: String? = "",
    var thumbnailUrl: String? = ""
)