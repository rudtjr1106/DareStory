package com.example.darestory.util

import com.example.domain.model.vo.UserVo

object UserInfo {

    var info: UserVo = UserVo()
        private set

    fun updateInfo(info: UserVo) {
        this.info = info
    }
}