package com.bride.thirdparty.bean

import io.objectbox.annotation.ConflictStrategy.FAIL
import io.objectbox.annotation.DatabaseType.DateNano
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.annotation.IndexType.HASH
import io.objectbox.annotation.NameInDb
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.annotation.Type
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToOne

@Entity
data class Order(
    @Id var id: Long = 0,
    @Index(type = HASH) @Unique(onConflict = FAIL) var uid: String? = null,
    @Transient var tempUsageCount: Int = 0,
    @NameInDb("price") var amount: Double = 0.0,
    @Type(DateNano) var timeInNanos: Long = 0,
) {
    @TargetIdProperty("customerId")
    lateinit var customer: ToOne<Customer>
}
