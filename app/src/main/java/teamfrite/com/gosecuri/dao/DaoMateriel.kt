package teamfrite.com.gosecuri.dao

import android.content.ContentValues
import android.util.Log

class DaoMateriel: Dao() {

    init {
        collectionName = "materiel"
    }

    fun GetMateriel() : String
    {
        var res: String = ""
        db.collection(collectionName)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!.iterator()) {
                        Log.d("Test-Firebase", document.id + " => " + document.data)
                        res = document.toString()
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }
        return res
    }
}