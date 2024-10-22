package ar.edu.unicen.seminario

import com.google.gson.annotations.SerializedName

data class Genre (
    @SerializedName("id")
    val id: Int,
            @SerializedName("name")
            val name:String){

}