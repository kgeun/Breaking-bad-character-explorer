package to.chip.dogsgallery.data.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import to.chip.dogsgallery.constants.CDGConstants
import to.chip.dogsgallery.data.model.ui.CDGBreedItem
import to.chip.dogsgallery.data.model.ui.CDGImageItem

@Dao
interface CDGMainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreed(breedItem: CDGBreedItem)

    @Query("SELECT * FROM dogBreed ORDER BY breedId ASC")
    fun getBreedsList(): LiveData<List<CDGBreedItem>?>

    @Query("DELETE FROM dogBreed")
    fun truncateBreedsList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(breedItem: CDGImageItem)

    @Query("SELECT * FROM dogImage WHERE breedName=:breedName ORDER BY imageId DESC LIMIT ${CDGConstants.MAX_SHOWING_IMAGE}")
    fun getImagesList(breedName: String): LiveData<List<CDGImageItem>?>

    @Query("DELETE FROM dogImage WHERE breedName=:breedName")
    fun deleteImageList(breedName: String)

    @Query("DELETE FROM dogImage")
    fun truncateImage()

    @Query("DELETE FROM dogBreed")
    fun truncateBreeds()
}