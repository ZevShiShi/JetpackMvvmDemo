package com.qljm.mvvmdemo.api

import android.os.Build
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.google.gson.GsonBuilder
import com.lihui.base.util.AppUtil
import com.lihui.base.util.AppUuidUtil
import com.qljm.mvvmdemo.app.BaseApplication
import com.qljm.mvvmdemo.common.BaseConstant
import com.qljm.mvvmdemo.utils.AppPrefsUtils
import com.qljm.mvvmdemo.utils.MD5Utils
import com.qljm.mvvmdemo.utils.MultiLanguageUtils
import me.hgj.jetpackmvvm.network.BaseNetworkApi
import me.hgj.jetpackmvvm.network.CoroutineCallAdapterFactory
import me.hgj.jetpackmvvm.network.interceptor.logging.LogInterceptor
import okhttp3.*
import okio.Buffer
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.LinkedHashMap


//双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(ApiService::class.java, BaseConstant.BASE_URL)
}

class NetworkApi : BaseNetworkApi() {

    companion object {
        val INSTANCE: NetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi()
        }
    }


    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //示例：添加公共heads，可以存放token，公共参数等， 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            addInterceptor(encryption)
            addInterceptor(interceptor)
            // 日志拦截器
            addInterceptor(LogInterceptor())
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
        }
        return builder

    }

    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }
    }

    private val encryption = Interceptor { chain ->
        var request = chain.request()
        request = if ("GET" == request.method()) {
            addGetParams(request)
        } else {
            addPostParams(request)
        }
        return@Interceptor chain.proceed(request)
    }

    private fun addPostParams(request: Request): Request {
        var body = request.body()
        // 获取请求的数据,@MultipartBody 不处理
        if (body is MultipartBody) {
            return request
        }
        // form 表单
        if (body is FormBody) {
            val fieldSize = body?.size() ?: 0
            val keyNames = mutableListOf<String>()
            keyNames.add("sign:")
            for (i in 0 until fieldSize) {
                keyNames.add(body.name(i) + ":" + body.value(i))
            }
            Collections.sort(keyNames, String.CASE_INSENSITIVE_ORDER) // 参数根据首字母排序
            val sortMap = LinkedHashMap<String, String>()  // 首字母升序排序完成的map
            val strKeyBuilder = StringBuilder()
            for (content in keyNames) {
                val key = content.split(":")[0]
                val value = content.split(":")[1]
                sortMap[key] = value
                strKeyBuilder.append(value)
            }
            val tempSign = MD5Utils.encryptMD5ToString(BaseConstant.SIGN)
            strKeyBuilder.append(tempSign)
            val finalSign = MD5Utils.encryptMD5ToString(strKeyBuilder.toString())
            sortMap["sign"] = finalSign!!
            LogUtils.d("addPostParams POST values=$sortMap")
            LogUtils.d("addPostParams POST finalSign = $finalSign,url====${request.url()}")
//            builder.add("sign", finalSign)
            return request.newBuilder().addHeader("sign", finalSign).build()
        }
        val charset = Charset.forName("UTF-8")
        val buffer = Buffer()
        body?.writeTo(buffer)
        val requestData: String = URLDecoder.decode(buffer.readString(charset).trim(), "utf-8")
        if (ObjectUtils.isNotEmpty(requestData)) {
            // @Body 方式
            val jsonObj = JSONObject(requestData)
            jsonObj.put("sign", "") // 添加公共参数
            val keyNames = mutableListOf<String>()
            jsonObj.keys().forEach {
                keyNames.add(it)
            }
            Collections.sort(keyNames, String.CASE_INSENSITIVE_ORDER) // 参数根据首字母排序
            val sortMap = LinkedHashMap<String, String>()  // 首字母升序排序完成的map
            val strKeyBuilder = StringBuilder()
            for (key in keyNames) {
                sortMap[key] = jsonObj.optString(key)
                strKeyBuilder.append(jsonObj.optString(key))
            }
            val tempSign = MD5Utils.encryptMD5ToString(BaseConstant.SIGN)
            strKeyBuilder.append(tempSign)
            val finalSign = MD5Utils.encryptMD5ToString(strKeyBuilder.toString())
            sortMap["sign"] = finalSign!!
            LogUtils.d("addPostParams POST values=$sortMap")
            LogUtils.d("addPostParams POST finalSign = $finalSign,url====${request.url()}")
            jsonObj.put("sign", finalSign) // 添加公共参数
//            val body = jsonObj.toString().toRequestBody(formBody?.contentType())
            return request.newBuilder().addHeader("sign", finalSign).build()
        } else {
            // 无@Body方式
            LogUtils.d("addPostParams POST @Query")
            val jsonObj = JSONObject("{}")
            val tempSign = MD5Utils.encryptMD5ToString(BaseConstant.SIGN)
            val finalSign = MD5Utils.encryptMD5ToString(tempSign)
            val body = RequestBody.create(body?.contentType(), jsonObj.toString())
            return request.newBuilder().post(body).addHeader("sign", finalSign!!).build()
        }
    }

    private fun addGetParams(request: Request): Request {
        // @Query or @QueryMap方式
        var httpUrl = request.url().newBuilder()
            .addQueryParameter("sign", "")   // 参数占位
            .build()

        val params: Set<String> = httpUrl.queryParameterNames()
        val keyNames = mutableListOf<String>()
        keyNames.addAll(params)
        Collections.sort(keyNames, String.CASE_INSENSITIVE_ORDER) // 参数根据首字母排序
        val strKeyBuilder = StringBuilder()
        val sortMap = LinkedHashMap<String, String>()
        for (key in keyNames) {
            sortMap[key] = httpUrl.queryParameter(key)!!
            strKeyBuilder.append(httpUrl.queryParameter(key)!!)
        }
        val tempSign = MD5Utils.encryptMD5ToString(BaseConstant.SIGN)
        strKeyBuilder.append(tempSign)
        val finalSign = MD5Utils.encryptMD5ToString(strKeyBuilder.toString())
        sortMap["sign"] = finalSign!!
        LogUtils.d("addGetParams GET values=$sortMap")
        LogUtils.d("addGetParams GET finalSign = $finalSign,url====${request.url()}")
//        httpUrl = request.url.newBuilder()
//            .addQueryParameter("sign", finalSign)   // 赋值
//            .build()
        return request.newBuilder().addHeader("sign", finalSign).build()
    }


    private val interceptor = Interceptor { chain ->
        var country = ""
        var lang = if (AppPrefsUtils.getString(BaseConstant.LOCALE_LANGUAGE)
                .isNotEmpty()
            && AppPrefsUtils.getString(BaseConstant.LOCALE_COUNTRY)
                .isNotEmpty()
        ) {
            country = AppPrefsUtils.getString(BaseConstant.LOCALE_COUNTRY)
            AppPrefsUtils.getString(BaseConstant.LOCALE_LANGUAGE) + "_" + country
        } else {
            country = MultiLanguageUtils.getAppLocale(BaseApplication.context).country
            (MultiLanguageUtils.getAppLocale(BaseApplication.context).language) + "_" + country
        }
        lang = when (lang) {
            "zh_TW" -> {
                "tc_CN"
            }
            "en_US" -> {
                "en_US"
            }
            else -> {
                "zh_CN"
            }
        }
        val request = chain.request().newBuilder()
            .addHeader("charset", "UTF-8")
            .addHeader("Content-Type", "application/json")
            .addHeader("language", lang) //language,值：zh_CN:简体中文,tc_CN:繁体中文,en_US:英文
            .addHeader("token", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
            .addHeader("client_id", AppUuidUtil.uuid)
            .addHeader(
                "client_info",
                Build.BRAND + ";" + AppUtil.getVersionName(
                    BaseApplication.context
                ) + ";" + Build.MODEL + ";" + Build.VERSION.RELEASE + ";"
                        + lang + ";" + AppUtil.getAppChannel(
                    BaseApplication.context,
                    "UMENG_CHANNEL"
                )
            )
            .build()
        LogUtils.d("interceptor=============================================${request.url()}")
        return@Interceptor chain.proceed(request)
    }
}