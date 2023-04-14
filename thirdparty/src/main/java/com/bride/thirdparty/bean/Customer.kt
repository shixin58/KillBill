package com.bride.thirdparty.bean

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class Customer(
    @Id var id: Long = 0L,
    var name: String? = null,
) {
    @Backlink(to = "customer")
    lateinit var orders: ToMany<Order>// Java List interface
}
