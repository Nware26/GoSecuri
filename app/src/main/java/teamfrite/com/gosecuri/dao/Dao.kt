package teamfrite.com.gosecuri.dao

import com.google.firebase.firestore.FirebaseFirestore

open class Dao
{
    protected val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    protected var collectionName: String = "";

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

