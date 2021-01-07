package me.elsiff.morefish.util

/**
 * Created by elsiff on 2019-01-02.
 */
object NumberUtils {
    fun ordinalSuffixOf(): String {
        return "位"
    }

    fun ordinalOf(number: Int): String = "$number${ordinalSuffixOf()}"
}