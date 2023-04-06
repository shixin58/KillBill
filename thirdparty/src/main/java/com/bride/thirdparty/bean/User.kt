package com.bride.thirdparty.bean

import android.os.Parcel
import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @Id
    var id: Long = 0,// can not be 0, null, -1
    var name: String? = null
) : Parcelable {
    private companion object : Parceler<User> {
        override fun create(parcel: Parcel): User {
            return User(parcel.readLong(), parcel.readString())
        }
        override fun User.write(parcel: Parcel, flags: Int) {
            parcel.writeLong(id)
            parcel.writeString(name)
        }
    }
}