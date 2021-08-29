package to.chip.dogsgallery.data.model.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogBreed")
data class CDGBreedItem(
    @PrimaryKey var breedId: Int? = 0,
    var name: String? = "",
    var thumbnailUrl: String? = "",
    var thumbnailColor: Int? = 0
)