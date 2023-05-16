package com.bride.thirdparty.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.lifecycleScope
import com.bride.baselib.toast
import com.bride.thirdparty.bean.Customer
import com.bride.thirdparty.bean.Customer_
import com.bride.thirdparty.bean.Order
import com.bride.thirdparty.bean.Order_
import com.bride.thirdparty.bean.User
import com.bride.thirdparty.bean.User_
import com.bride.thirdparty.databinding.ActivityObjectBoxBinding
import com.bride.thirdparty.util.ObjectBox
import com.bride.ui_lib.BaseActivity
import io.objectbox.Box
import io.objectbox.kotlin.*
import io.objectbox.query.QueryBuilder.StringOrder.CASE_SENSITIVE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ObjectBoxActivity : BaseActivity(), OnClickListener {
    private lateinit var mBinding: ActivityObjectBoxBinding

    private val userBox: Box<User> = ObjectBox.store.boxFor(User::class.java)
    private val orderBox: Box<Order> = ObjectBox.store.boxFor()
    private val customerBox: Box<Customer> = ObjectBox.store.boxFor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityObjectBoxBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        mBinding.tvPut.setOnClickListener(this)
        mBinding.tvGetAll.setOnClickListener(this)
        mBinding.tvQuery.setOnClickListener(this)
        mBinding.tvRemoveAll.setOnClickListener(this)
        mBinding.tvCount.setOnClickListener(this)
        mBinding.tvCallInTxAsync.setOnClickListener(this)
        mBinding.tvAwaitCallInTx.setOnClickListener(this)
        mBinding.tvRunInTx.setOnClickListener(this)
        mBinding.tvParcelize.setOnClickListener(this)
        mBinding.tvInsertOrder.setOnClickListener(this)
        mBinding.tvQueryOrder.setOnClickListener(this)
        mBinding.tvInFilter.setOnClickListener(this)
        mBinding.tvNewQueryApi.setOnClickListener(this)
        mBinding.tvReusingQueries.setOnClickListener(this)
        mBinding.tvRelationRemove.setOnClickListener(this)
        mBinding.tvOrder.setOnClickListener(this)

        lifecycleScope.launch {
            userBox.query()
                .build()
                .subscribe()
                .toFlow()
                .flowOn(Dispatchers.IO)
                .collect { users ->
                    toast("User List: $users")
                }
        }
        lifecycleScope.launch {
            ObjectBox.store.subscribe(Order::class.java)
                .toFlow()
                .collect {
                    val orderList = orderBox.query()
                        .`in`(Order_.uid, arrayOf("tree", "cake"), CASE_SENSITIVE)
                        .build()
                        .use { it.find() }
                    toast("Order List: $orderList")
                }
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            mBinding.tvPut -> {
                val introListStr = mBinding.etName.text.trim()
                if (introListStr.isNotEmpty()) {
                    val introList = introListStr.split(";")
                    if (introList.size == 1) {
                        val intro = introList.single().split(",")
                        val user = User(name = intro[0], yearOfBirth = intro.getOrNull(1)?.toInt()?:0, height = intro.getOrNull(2)?.toInt()?:0)
                        userBox.put(user)
                    } else {
                        val users: List<User> = introList.map {
                            val intro = it.split(",")
                            User(name = intro[0], yearOfBirth = intro.getOrNull(1)?.toInt()?:0, height = intro.getOrNull(2)?.toInt()?:0)
                        }
                        userBox.put(users)
                    }
                    mBinding.etName.setText("")
                }
            }
            mBinding.tvGetAll -> {
                val users = userBox.all
                toast(users)
            }
            mBinding.tvQuery -> {
                val query = userBox.query(User_.name.equal("Max"))
                    .order(User_.name)
                    .build()
                val users = query.find()
                query.close()
                toast(users)
            }
            mBinding.tvRemoveAll -> {
                userBox.removeAll()
                orderBox.removeAll()
                customerBox.removeAll()
            }
            mBinding.tvCount -> {
                val userCount = userBox.count()
                toast("userCount = $userCount")
            }
            mBinding.tvCallInTxAsync -> {
                // 切换线程异步执行数据库操作
                val idListStr = mBinding.etId.text.trim()
                if (idListStr.isNotEmpty()) {
                    val idArr = idListStr.split(" ")
                        .map { it.toLong() }
                        .toLongArray()
                    ObjectBox.store.callInTxAsync({
                        val nameList = userBox.get(idArr).map { it.name }
                        userBox.remove(*idArr)
                        nameList
                    }, { result, error ->
                        runOnUiThread {
                            if (error != null) {
                                toast("Failed to remove user with ids $idArr")
                            } else {
                                toast("Removed users with names: $result")
                            }
                        }
                    })
                }
            }
            mBinding.tvAwaitCallInTx -> {
                // 切换协程异步执行数据库操作
                val idListStr = mBinding.etId.text.trim()
                if (idListStr.isNotEmpty()) {
                    val userId = idListStr.split(" ")
                        .map { it.toLong() }[0]
                    lifecycleScope.launch {
                        try {
                            val name = ObjectBox.store.awaitCallInTx {
                                val name = userBox[userId].name
                                userBox.remove(userId)
                                name
                            }
                            toast("Removed user with name $name")
                        } catch (e: Exception) {
                            toast("Failed to remove user with id $userId")
                        }
                    }
                }
            }
            mBinding.tvRunInTx -> {
                // 批量数据库操作用事务Transaction
                lifecycleScope.launch {
                    val nameListStr = mBinding.etName.text.trim()
                    if (nameListStr.isNotEmpty()) {
                        val nameList = nameListStr.split(" ")
                        withContext(Dispatchers.IO) {
                            ObjectBox.store.runInTx {
                                for (name in nameList) {
                                    userBox.put(User(name = name))
                                }
                            }
                        }
                        toast("Success: $nameList")
                    }
                }
            }
            mBinding.tvParcelize -> {
                val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("user", User::class.java)
                } else {
                    intent.getParcelableExtra("user")
                }
                toast(user)
            }
            mBinding.tvInsertOrder -> {
                ObjectBox.store.runInTx {
                    val customer = Customer(name = "Max")
                    val orderTree = Order(uid = "tree", tempUsageCount = 1, amount = 3.5, timeInNanos = System.nanoTime())
                    customer.orders.add(orderTree)
                    val orderCake = Order(uid = "cake", tempUsageCount = 2, amount = 7.4, timeInNanos = System.nanoTime())
                    customer.orders.add(orderCake)
                    customerBox.put(customer)
                }
            }
            mBinding.tvQueryOrder -> {
                val orderTree = orderBox.query {
                    equal(Order_.uid, "tree", CASE_SENSITIVE)
                }.use { it.findUnique() }
                toast(orderTree)
            }
            mBinding.tvInFilter -> {
                val orderList = orderBox.query()
                    .inValues(Order_.uid, arrayOf("tree", "cake"), CASE_SENSITIVE)
                    .build()
                    .use { it.find() }
                toast(orderList.map { it.customer.target })
            }
            mBinding.tvNewQueryApi -> {
                // infix fun, LazyList
                // >=160 && <=170
                val heightCondition = ((User_.height greater 160) or (User_.height equal 160)) and ((User_.height less 170) or (User_.height equal 160))
                val lazyList = userBox.query(
                    User_.name notEqual "Max"
                            and ((User_.yearOfBirth less  1988) or heightCondition)
                )
                    .build()
                    .use { it.findLazyCached() }
                toast(lazyList.toList())
            }
            mBinding.tvReusingQueries -> {
                // 复用Query。已构建完的Query通过alias和setParameter改参数。
                val query = customerBox.query(Customer_.name.equal("").alias("name"))
                    .build()
                val list = query.setParameter("name", "Max")
                    .find()
                toast(list.map { it.orders.toList() })
            }
            mBinding.tvRelationRemove -> {
                val customer = customerBox.query {
                    equal(Customer_.name, "Max", CASE_SENSITIVE)
                }.use { it.findFirst() }?:return
                // One-to-Many双向删除
                customer.orders.removeAt(0)
                customer.orders.applyChangesToDb()
            }
            mBinding.tvOrder -> {
                val user0 = User(name = "Max", yearOfBirth = 1988, height = 173)
                val user1 = User(name = "Jane", yearOfBirth = 1992, height = 165)
                val user2 = User(name = "Apple", yearOfBirth = 1970, height = 5)
                val user3 = User(name = "Apple", yearOfBirth = 1900, height = 8)
                val user4 = User(name = "Banana", yearOfBirth = 1980, height = 10)
                val user5 = User(name = "Cat", yearOfBirth = 1980, height = 30)
                userBox.removeAll()
                userBox.put(user0, user1, user2, user3, user4, user5)
                val list = userBox.query()
                    .orderDesc(User_.name)
                    .order(User_.yearOfBirth)
                    .build()
                    .find()
                Timber.i(list.toString())
            }
        }
    }
}