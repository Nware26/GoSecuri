package teamfrite.com.gosecuri.dao

import android.content.ContentValues.TAG
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import teamfrite.com.gosecuri.model.User


class Dao ()
{
    val db = FirebaseFirestore.getInstance()

    fun test() :String
    {
        var res: String = ""
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.d("Test-Firebase", document.id + " => " + document.data)
                        res = document.toString()
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
        return res
    }

    /*fun add()
    {
        val items = HashMap<String, Any>()
        items.put("test", User("Plassard","Arthur"))
        db.collection("users")
            .add(items)
            .addOnCompleteListener {
                Log.d("Test-Firebase", it.result.toString())
            }
    }*/
}

