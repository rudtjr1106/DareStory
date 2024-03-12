package com.darestory.data

import com.darestory.domain.model.vo.UserVo

object DataUserInfo {

    var info: UserVo = UserVo()
        private set

    fun updateInfo(info: UserVo) {
        this.info = info
    }
}