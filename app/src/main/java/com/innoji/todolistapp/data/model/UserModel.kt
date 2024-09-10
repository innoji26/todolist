package com.innoji.todolistapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
class UserModel (
    val username: String,
    val isLogin: Boolean,
    val accessToken: String,
    ): Parcelable