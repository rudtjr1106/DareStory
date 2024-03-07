package com.example.data

import com.example.domain.model.vo.UserVo

object DataUserInfo {

    var info: UserVo = UserVo()
        private set

    fun updateInfo(info: UserVo) {
        this.info = info
    }
}