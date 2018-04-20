package com.victor.demon.repository

import java.util.ArrayList

/**
 * <p>Created by shixin on 2018/4/16.
 */
class RecyclerViewRepository {
    companion object {

        fun getCountryList():List<String> {
            val countryList = ArrayList<String>()
            countryList.add("America")
            countryList.add("Canada")
            countryList.add("Australia")
            countryList.add("Britain")
            return countryList
        }

        fun getColorList():List<String> {
            val colorList = ArrayList<String>()
            colorList.add("Orange")
            colorList.add("Yellow")
            colorList.add("Brown")
            colorList.add("Black")
            colorList.add("Red")
            return colorList
        }
    }
}