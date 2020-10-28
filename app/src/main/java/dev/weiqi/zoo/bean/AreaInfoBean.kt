package dev.weiqi.zoo.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AreaInfoBean(
    val imgUrl: String,
    val name: String,
    val desc: String,
    val openDayInfo: String,
    val openUrl: String
) : Parcelable {
    companion object {
        const val KEY_PIC_PATH = "E_Pic_URL"
        const val KEY_TITLE = "E_Name"
        const val KEY_SUBTITLE = "E_Info"
        const val KEY_OPEN_DAY_INFO = "E_Memo"
        const val KEY_OPEN_URL = "E_URL"
    }
}