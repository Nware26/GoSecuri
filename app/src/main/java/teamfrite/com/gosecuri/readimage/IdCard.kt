package teamfrite.com.gosecuri.readimage

import android.util.Log
import android.util.SparseArray
import com.google.android.gms.vision.text.TextBlock
import java.text.MessageFormat


open class IdCard {
        var reg1 = "IDFRA[A-Z]+[<K ]+[0-9]{6}"
        var reg2 = "[0-9]{13}([A-Z]+[ <K]{2})+[ <K]+[0-9]{7}(M|F)[0-9]"

        var numero : String = ""
        var nom : String = ""
        var prenom : String = ""
        var sexe : String = ""
        var dateNaissance : String = ""
        var departement : String = ""
        var adresse : String = ""
        var dateValidite : String = ""
        var delivreDate : String = ""
        var delivrePar : String = ""
        var ligne1 : String = ""
        var  ligne2 : String = ""

        fun CompleteValues (Values : SparseArray<TextBlock>){
        for (i in 0 until Values.size()){
        if (Values[i] != null){
        SetValue(Values[i].value )
        }
        }
        }

        fun SetValue (Value : String) : Boolean{
        //if (Value.contains("<")) Log.d("Mael", Regex(reg2).find(Value)!!.value)
        if ( Value.contains(Regex(reg1)) && Regex(reg1).find(Value)!!.value.length > 20 && Regex(reg1).find(Value)!!.value.length < 39 ){
        Log.d("Mael", "reg1 " + Value)
        var nom = Regex("IDFRA[A-Z]+").find(Value)!!.value.substring(5)
        while (nom.endsWith("K")) { nom = nom.substring(0, nom.length - 1 ) }
        this.nom = nom
        this.departement = Regex("[0-9]{6}").find(Value)!!.value.substring(0, 3)
        Log.d("Mael", this.nom + this.departement)
        }
        if ( Value.contains(Regex(reg2)) && Regex(reg2).find(Value)!!.value.length > 20 && Regex(reg2).find(Value)!!.value.length < 39 ){
        Log.d("Mael", "reg2 " + Value)
        val valid = Regex("[0-9]{13}[A-Z]+").find(Value)!!.value.substring(0, 4)
        this.dateValidite = valid.substring(0, 2) + "/" + valid.substring(2, 4)
        var prenom = Regex("([A-Z]+[ <K]{2})+").find(Regex("[0-9]+([A-Z]+[ <K]{2})+").find(Value)!!.value)!!.value
        prenom = prenom.substring(0, prenom.length-2).replace(Regex("[ <K]{2}"), " ")
        while (prenom.endsWith("K")) { prenom = prenom.substring(0, prenom.length - 1 ) }
        this.prenom = prenom
        val dateNaissance= Regex("[0-9]{7}").find(Regex("[ <K]+[0-9]{7}").find(Value)!!.value)!!.value.substring(0,6)
        this.dateNaissance = dateNaissance.substring(4,6) + dateNaissance.substring(2,4) + dateNaissance.substring(0, 2)
        this.sexe = Regex("(M|F)[0-9]").find(Value)!!.value.substring(0, 1)
        Log.d("Mael", this.dateValidite + this.prenom + this.dateNaissance + this.sexe)
        }
        /*
        if (this.numero == "" && Value.contains(Regex("[0-9]{12}"))){
            this.numero = Regex("[0-9]{12}").find(Value)!!.value
        }
        Log.d("Mael", Compare(Value, "Nom : ").toString() + Value)
        if (Compare(Value, "Nom : ") == 100){
            this.nom = Value.split("Nom : ")[1].split(" ")[0]
        }
        if (Compare(Value, "Prenom : ") == 100){
            this.prenom = Value.split("Prenom : ")[1].split(" ")[0]
        }
        if (Value.contains(Regex("[MF]")) && Compare(Value, "Sexe : ") > 10){
            this.sexe = Regex("[MF]").find(Value)!!.value
        }
        if (Value.contains(Regex("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}"))){
            this.dateNaissance = Regex("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}").find(Value)!!.value
        }
        */
        return false
        }

        override fun toString(): String {
        var message = ""

        if (this.numero != "") message += MessageFormat.format("\ncarte numéro {0}", this.numero)
        if (this.nom != "") message += MessageFormat.format("\nMr {0}", this.nom)
        if (this.prenom != "") message += MessageFormat.format( (if (this.nom != "") "" else "\n")  + "{ 0}", this.prenom)
        if (this.sexe != "") message += MessageFormat.format("\n de sexe {0}", if (this.sexe == "M") "masculin" else "feminin")
        if (this.dateNaissance != "") message += MessageFormat.format("\nNé{0} le {1}", (if (this.sexe == "F") "e" else ""), this.dateNaissance)
        return message
        }

private fun Compare(s1 : String, s2 : String) : Int{
        if (s1.contains(Regex(".*" + s2 + ".*")) || s2.contains(Regex(".*" + s1 + ".*"))) return 100
        /*if (s1.length > 1 && s2.length > 1) {
            var ascii1: Array<Array<Int>> = emptyArray()
            var ascii2: Array<Array<Int>> = emptyArray()
            var next1: Array<Array<Int>> = emptyArray()
            var next2: Array<Array<Int>> = emptyArray()
            val total = (s1.length + s2.length) * 3 - 4
            var diff = 0
            var obj: Char
            for (i in 0 until s1.length) {
                obj = s1[i]
                if (ascii1.any { it[0] == obj.toInt() }) {
                    ascii1.first { it[0] == obj.toInt() }[1]++
                } else {
                    ascii1 = ascii1.plus(arrayOf(obj.toInt(), 1))
                }

                if (i < s1.length - 1) {
                    if (next1.any { it[0] == obj.toInt() && it[1] == s1[i + 1].toInt() }) {
                        next1.first { it[0] == obj.toInt() && it[1] == s1[i + 1].toInt() }[2]++
                    } else {
                        next1 = next1.plus(arrayOf(obj.toInt(), s1[i + 1].toInt(), 1))
                    }
                }
            }
            for (i in 0 until s2.length) {
                obj = s2[i]
                if (ascii2.any { it[0] == obj.toInt() }) {
                    ascii2.first { it[0] == obj.toInt() }[1]++
                } else {
                    ascii2 = ascii2.plus(arrayOf(obj.toInt(), 1))
                }

                if (i < s2.length - 1) {
                    if (next2.any { it[0] == obj.toInt() && it[1] == s2[i + 1].toInt() }) {
                        next2.first { it[0] == obj.toInt() && it[1] == s2[i + 1].toInt() }[2]++
                    } else {
                        next2 = next2.plus(arrayOf(obj.toInt(), s2[i + 1].toInt(), 1))
                    }
                }
            }
            for (i in ascii1) {
                diff += if (ascii2.any { it[0] == i[0] }) {
                    (i[1] - ascii2.first { it[0] == i[0] }[1]).sign * (i[1] - ascii2.first { it[0] == i[0] }[1])
                } else {
                    i[1]
                }
            }
            for (i in ascii2) {
                if (!ascii1.any { it[0] == i[0] }) {
                    diff += i[1]
                }
            }

            for (i in next1) {
                diff += if (next2.any { it[0] == i[0] && it[1] == i[1] }) {
                    (i[2] - next2.first { it[0] == i[0] && it[1] == i[1] }[2]).sign * (i[2] - next2.first { it[0] == i[0] && it[1] == i[1] }[2]) * 2
                } else {
                    i[2] * 2
                }
            }
            for (i in next2) {
                if (!next1.any { it[0] == i[0] && it[1] == i[1] }) {
                    diff += i[2] * 2
                }
            }
            //Log.d("Mael", s1 + " = " + s2 + " [ " +( (total - diff) * 100 / total )+" ]" );
            return (total - diff) * 100 / total
        }*/
        return 0
        }

        fun findEzValue(value : String, search : String) : String{
        if (Compare(value, search + " : ") == 100) return value.replace("\\s".toRegex(), "").split(search + ":")[1]
        var n = 4 * value.length
        if (n > 35) n = 35
        if (Compare(value, search + " : ") > n) {
        val memList: Array<String>
            var c: Int
                    memList = value.replace("\\s".toRegex(), "").split(":").toTypedArray()
                    c = 0
                    while (Compare(memList[c], search) > n) {
                    c++
                    }
                    if (c + 1 < memList.size) return memList[c+1]
        }
        return ""
        }
        }