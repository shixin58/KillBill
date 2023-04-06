package com.bride.thirdparty.bean

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(
    @Id
    var id: Long = 0,// can not be 0, null, -1
    var name: String? = null
)
