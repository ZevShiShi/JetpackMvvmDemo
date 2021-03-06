package com.lihui.base.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.blankj.utilcode.util.*
import com.qljm.mvvmdemo.app.BaseApplication
import java.io.*
import java.text.DecimalFormat
import kotlin.math.roundToInt


/**
 *   Created by ruirui on 2019/12/19
 */
object AppUtil {

    const val splitChar = "@"

    fun writeString(content: String) {
        if (ObjectUtils.isEmpty(content)) return
        val dir = File(getFileDirPath())
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, getFileName())
        if (!file.exists()) {
            file.createNewFile()
        }
        FileIOUtils.writeFileFromString(file, content)
        LogUtils.d("writeString====$content")
    }

    private fun readString(): String {
        val dir = File(getFileDirPath())
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, getFileName())
        if (file.exists()) {
            val str: String? = FileIOUtils.readFile2String(file)
            if (ObjectUtils.isEmpty(str)) {
                return ""
            }
            return str!!
        }
        return ""
    }

    fun getServer(): MutableList<String> {
        val list: MutableList<String> = mutableListOf()
        val readString: String? = readString()
        if (ObjectUtils.isEmpty(readString)) {
            LogUtils.d("getServer=======$list")
            return list
        }
        val stringArray = readString?.split(splitChar)
        if (ObjectUtils.isNotEmpty(stringArray)) {
            for (content in stringArray!!) {
                list.add(content)
            }
        }
        LogUtils.d("getServer=======$list")
        return list
    }

    /**
     * ?????????
     */
    fun getAppChannel(
        context: Context?,
        key: String?
    ): String? {
        if (context == null || ObjectUtils.isEmpty(key)) {
            return null
        }
        var channelNumber: String? = null
        try {
            val packageManager = context.packageManager
            if (packageManager != null) {
                val applicationInfo = packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                )
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key)
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return channelNumber
    }

    fun getServerType(): Int {
        if (ObjectUtils.isEmpty(getServer()) || getServer().size < 3) {
            return 0
        }
        return getServer()[2].toInt()
    }

//    fun delServerFile() {
//        val serverFile = File(File(getFileDirPath()), getFileName())
//        if (serverFile.exists()) {
//            serverFile.delete()
//        }
//    }

    private fun getFileDirPath(): String {
        return BaseApplication.context.filesDir.absolutePath
    }

    private fun getFileName(): String {
        return "world_server.txt"
    }


    /**
     * ??????????????????
     *
     */
    fun getVersionName(context: Context): String? {
        //??????????????????
        val pm = context.packageManager
        //???????????????
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            //???????????????
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null

    }


    /**
     * ????????????????????????
     */
    fun checkInstallWx(): Boolean {
        val packageManager = BaseApplication.context.packageManager // ??????packagemanager
        val pinfo =
            packageManager.getInstalledPackages(0) // ???????????????????????????????????????
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mm") {
                    return true
                }
            }
        }
        return false
    }

    /**
     * ????????????????????????
     *
     * @num ??????
     * @endNum ??????????????????
     * @unit ?????????????????????????????????
     */
    fun formatBigNum(num: String?, endNum: String?, unit: String?): String? {
        var number = "0"
        if (ObjectUtils.isEmpty(num)) {
            return number
        }
        try {
            val numberLong = num!!.toLong()
            var tempLong = 0f
            if (numberLong >= 10000) {
                val format = DecimalFormat(endNum)   // .00
                tempLong = numberLong * 1.0f / 10000
                number = format.format(tempLong) + unit
            } else {
                number = num
            }
        } catch (e: Exception) {
            LogUtils.e("formatBigNum====$e")
        }
        return number
    }

    /**
     * ????????????????????????
     *
     * @num ?????????
     * @len ??????????????????????????????
     * @endNum ??????????????????????????????????????????????????????0, ???.00????????????????????????????????????0??????
     * @unit ???????????????????????????
     */
    fun formatBigNum(num: String?, len: Int, endNum: String?, unit: String?): String? {
        var number = "0"
        if (ObjectUtils.isEmpty(num)) {
            return number
        }
        try {
            val numberLong = num!!.toLong()
            var tempLong = 0f
            if (numberLong >= len) {
                val format = DecimalFormat(endNum)   // .00
                tempLong = numberLong * 1.0f / len
                number = format.format(tempLong) + unit
            } else {
                number = num
            }
        } catch (e: Exception) {
            LogUtils.e("formatBigNum====$e")
        }
        return number
    }

    /**
     * ???????????????
     *
     */
//    fun getVersionCode(context: Context): Int {
//
//        //??????????????????
//        val pm = context.packageManager
//        //???????????????
//        try {
//            val packageInfo = pm.getPackageInfo(context.packageName, 0)
//            //???????????????
//            return packageInfo.versionCode
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//
//        return 0
//
//    }
//
//    fun checkSHA1(context: Context): String? {
//        try {
//            val info = context.packageManager.getPackageInfo(
//                context.packageName, PackageManager.GET_SIGNATURES
//            )
//            val cert = info.signatures[0].toByteArray()
//            val md = MessageDigest.getInstance("SHA1")
//            val publicKey = md.digest(cert)
//            val hexString = StringBuffer()
//            for (i in publicKey.indices) {
//                val appendString =
//                    Integer.toHexString(0xFF and publicKey[i].toInt())
//                        .toUpperCase(Locale.US)
//                if (appendString.length == 1) hexString.append("0")
//                hexString.append(appendString)
//                hexString.append(":")
//            }
//            val result = hexString.toString()
//            return result.substring(0, result.length - 1)
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        } catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//        }
//        return null
//    }

//    fun checkNetwork(context: Context): Boolean {
//        val connectivity =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//                ?: return false
//        @SuppressLint("MissingPermission") val info =
//            connectivity.activeNetworkInfo
//        return info != null && info.isConnected
//    }

    /**
     * @param context
     * @return WebView ???????????????
     */
    fun getCachePath(context: Context): String? {
        return context.cacheDir
            .absolutePath + File.separator + "agentweb-cache"
    }


    /**
     * ???????????????????????????
     */
    fun saveImage(context: Activity): File {
        val storageDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(
            storageDir,
            "worldhds_" + System.currentTimeMillis() + ".jpg"
        )
    }

    /**
     * ???????????????gif????????????
     */
    fun saveGif(context: Context): File {
        val storageDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(
            storageDir,
            "worldhds_" + System.currentTimeMillis() + ".gif"
        )
    }

    /**
     * ??????Android Q??????????????? ??????????????????????????????
     */
    fun insertImage(context: Context, file: File?) {
        if (file == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DESCRIPTION, "This is an news image")
            values.put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.TITLE, "Image.jpg")
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")

            val external: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val resolver: ContentResolver = context.contentResolver
            val insertUri: Uri? = resolver.insert(external, values)
            var inputStream: BufferedInputStream? = null
            var os: OutputStream? = null
            try {
                inputStream = BufferedInputStream(FileInputStream(file))
                if (insertUri != null) {
                    os = resolver.openOutputStream(insertUri)
                }
                if (os != null) {
                    val buffer = ByteArray(1024 * 4)
                    var len: Int
                    while (inputStream.read(buffer).also { len = it } != -1) {
                        os.write(buffer, 0, len)
                    }
                    os.flush()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                CloseUtils.closeIO(os, inputStream)
            }
        } else {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            val uri: Uri = context.contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = uri
            context.sendBroadcast(intent)
        }
    }


    /**
     * ??????????????????
     *
     * @param context
     * @param file
     */
    fun refreshPicture(context: Context, file: File) {
        LogUtils.e("?????????????????????" + file.absolutePath)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(
                context,
                arrayOf(file.absolutePath),
                arrayOf("video/mp4")
            ) { path: String, uri: Uri ->
                LogUtils.d("refreshPicture======$path==========$uri")
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                mediaScanIntent.data = uri
                context.sendBroadcast(mediaScanIntent)
            }
        } else {
            val relationDir = file.parent
            val file1 = File(relationDir)
            context.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.fromFile(file1.absoluteFile)
                )
            )
        }
    }


    /**
     * file?????????????????????context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
     * ?????? context.getExternalFilesDir(Environment.DIRECTORY_DCIM);??????????????????
     *
     *
     * ??????Android R(11)???????????????
     * ??????????????????????????????
     */
    fun insertVideo(context: Context, file: File?) {
        if (file == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {   // Build.VERSION_CODES.R
            val values = ContentValues()
            values.put(MediaStore.Video.Media.DISPLAY_NAME, file.name)
            values.put(MediaStore.Video.Media.IS_PENDING, 1)
            val external =
                MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val resolver = context.contentResolver
            val insertUri = resolver.insert(external, values)
            LogUtils.e("insertVideo===========insertUri==$insertUri")
            try {
                val pfd = resolver.openFileDescriptor(insertUri!!, "w")
                val inputStream =
                    BufferedInputStream(FileInputStream(file))
                val os =
                    FileOutputStream(pfd!!.fileDescriptor)
                if (os != null) {
                    val buffer = ByteArray(1024 * 4)
                    var len: Int
                    while (inputStream.read(buffer).also { len = it } != -1) os.write(
                        buffer,
                        0,
                        len
                    )
                    os.flush()
                    inputStream.close()
                    os.close()
                }
                values.clear()
                values.put(MediaStore.Video.Media.IS_PENDING, 0)
                resolver.update(insertUri, values, null, null)
                pfd.close()
            } catch (e: java.lang.Exception) {
                LogUtils.e("insertVideo=============$e")
            }
        } else {
            refreshPicture(context, file)
        }
    }

    fun timeParse(duration: Long): String? {
        var time: String? = ""
        val minute = duration / 60000
        val seconds = duration % 60000
        val second = (seconds.toFloat() / 1000).roundToInt().toLong()
        if (minute < 10) {
            time += "0"
        }
        time += "$minute:"
        if (second < 10) {
            time += "0"
        }
        time += second
        return time
    }



    /**
     * ??????Activity??????Destroy
     * @param mActivity
     * @return true:?????????
     */
    @SuppressLint("ObsoleteSdkInt")
    fun isDestroy(mActivity: Activity?): Boolean {
        return mActivity == null ||
                mActivity.isFinishing ||
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed
    }


    fun isHour24(time: Long): Boolean {
        val currTime = TimeUtils.getNowMills()
        val hour24 = 1000 * 60 * 60 * 24L
        if ((currTime - time) >= hour24) {
            return true
        }
        return false
    }
}
