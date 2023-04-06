package com.bride.thirdparty.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.lifecycleScope
import com.bride.baselib.toast
import com.bride.thirdparty.bean.User
import com.bride.thirdparty.bean.User_
import com.bride.thirdparty.databinding.ActivityObjectBoxBinding
import com.bride.thirdparty.util.ObjectBox
import com.bride.ui_lib.BaseActivity
import io.objectbox.Box
import io.objectbox.kotlin.awaitCallInTx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ObjectBoxActivity : BaseActivity(), OnClickListener {
    private lateinit var mBinding: ActivityObjectBoxBinding

    private val userBox: Box<User> = ObjectBox.store.boxFor(User::class.java)

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
    }

    override fun onClick(v: View?) {
        when(v) {
            mBinding.tvPut -> {
                val nameListStr = mBinding.etName.text.trim()
                if (nameListStr.isNotEmpty()) {
                    val nameList = nameListStr.split(" ")
                    if (nameList.size == 1) {
                        val user = User(name = nameList.single())
                        userBox.put(user)
                    } else {
                        val users: List<User> = nameList.map { User(name = it) }
                        userBox.put(users)
                    }
                    mBinding.etName.setText("")
                    toast("Success: $nameList")
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
                toast("Success")
            }
            mBinding.tvCount -> {
                val userCount = userBox.count()
                toast("userCount = $userCount")
            }
            mBinding.tvCallInTxAsync -> {
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
        }
    }
}