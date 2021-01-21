package com.example.selfdicwithjetpack.display

/**
 * Created by TpOut on 2020/10/12.<br>
 * Email address: 416756910@qq.com<br>
 */

sealed class DisplayUIModel {
    class DisplayHeaderModel() : DisplayUIModel() {
        override fun hashCode(): Int {
            return super.hashCode()
        }

        override fun toString(): String {
            return "DisplayHeaderModel"
        }

        override fun equals(other: Any?): Boolean {
            if (other !is DisplayUIModel) {
                return false
            }
            return true
        }
    }

    class DisplayItemModel(val src: String, val dst: String, val sentence: String) : DisplayUIModel() {

        override fun hashCode(): Int {
            return super.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if(other !is DisplayItemModel){
                return false
            }
            return src == other.src
        }

        override fun toString(): String {
            return "{$src}"
        }
    }
}
