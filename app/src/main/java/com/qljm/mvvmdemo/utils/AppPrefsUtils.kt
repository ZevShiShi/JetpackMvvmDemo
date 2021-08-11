package com.qljm.mvvmdemo.utils

import android.content.Context
import android.content.SharedPreferences.Editor
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.qljm.mvvmdemo.app.BaseApplication
import com.qljm.mvvmdemo.common.BaseConstant
import java.util.*


/*
  SP工具类
 */
object AppPrefsUtils {

    private var systemCurrentLocal = Locale.CHINA

    private var sp =
        BaseApplication.context.getSharedPreferences(BaseConstant.TABLE_PREFS, Context.MODE_PRIVATE)
    private var ed: Editor

    init {
        ed = sp.edit()
    }

    /**
     * Boolean型数据
     */
    fun putBoolean(key: String, value: Boolean) {
        ed.putBoolean(key, value)
        ed.commit()
    }

    /**
     * 默认 false
     */
    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    /*
        String数据
     */
    fun putString(key: String, value: String) {
        ed.putString(key, value)
        ed.commit()
    }

    /*
        默认 ""
     */
    fun getString(key: String): String {
        return sp.getString(key, "").toString()
    }

    /*
        Int数据
     */
    fun putInt(key: String, value: Int) {
        ed.putInt(key, value)
        ed.commit()
    }

    /*
        默认 0
     */
    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    /*
        默认 0
     */
    fun getInt(key: String, def: Int): Int {
        return sp.getInt(key, def)
    }

    /*
        Long数据
     */
    fun putLong(key: String, value: Long) {
        ed.putLong(key, value)
        ed.commit()
    }

    /*
       Float
    */
    fun putfloat(key: String, value: Float) {
        ed.putFloat(key, value)
        ed.commit()
    }

    /*
        默认 0
     */
    fun getLong(key: String): Long {
        return sp.getLong(key, 0)
    }

    /**
     * Set数据
     */
    fun putStringSet(key: String, set: Set<String>) {
        val localSet = getStringSet(key).toMutableSet()
        localSet.addAll(set)
        ed.putStringSet(key, localSet)
        ed.commit()
    }

    /**
     * 默认空set
     */
    fun getStringSet(key: String): Set<String> {
        val set = setOf<String>()
        return sp.getStringSet(key, set)!!
    }

    /**
     * 删除key数据
     */
    fun remove(key: String) {
        ed.remove(key)
        ed.commit()
    }

//    fun saveUser(data: LoginBean) {
//        putString(KEY_SP_TOKEN, data.token)
//        putString(USER_NAME, data.user?.name + "")
//        putString(USER_PHONE, data.user?.phone + "")
//        putString(USER_HEADIMG, data.user?.headImg + "")
//        putString(USER_USERGRADEID, data.user?.grade.toString())
//        putString(USER_USERGRADENAME, data.user?.gradeName.toString())
//        putString(USER_ID, data.user?.id.toString())
//        //登录该条信息不保存
//        // putBoolean(USER_ISAGENTSALE,data.isAgentSale)
//    }
//
//    fun saveUserNoToken(data: LoginBean) {
//        putString(USER_NAME, data.user?.name + "")
//        //   putString(USER_PHONE,data.user?.phone+"")
//        putString(USER_HEADIMG, data.user?.headImg + "")
//        putString(USER_USERGRADEID, data.user?.grade.toString())
//        putString(USER_USERGRADENAME, data.user?.gradeName.toString())
//        putString(USER_ID, data.user?.id.toString())
//    }

    fun saveLanguage(select: Int) {
        ed.putInt(BaseConstant.TAG_LANGUAGE, select)
        ed.commit()
    }

    fun getSelectLanguage(): Int {
        return sp.getInt(BaseConstant.TAG_LANGUAGE, 0)
    }

    fun getSystemCurrentLocal(): Locale? {
        return systemCurrentLocal
    }

    fun setSystemCurrentLocal(local: Locale) {
        systemCurrentLocal = local
    }

}

