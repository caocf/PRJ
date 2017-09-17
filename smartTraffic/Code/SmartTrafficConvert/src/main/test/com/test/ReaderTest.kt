package com.test

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * Created by Will on 2017/6/5 15:47.
 */
fun main(args:Array<String>){
    val file = File("C:\\Users\\zoumy\\Desktop\\ddd.html")
    val fis = FileInputStream(file)
    val ipr = InputStreamReader(fis)
    val sb = StringBuilder()
    ipr.forEachLine {
        sb.append(it)
    }
    println("aaaa\n$sb")
    val sb2 = StringBuilder()
    sb2.append(2).append(3)
    println(sb2)
}
