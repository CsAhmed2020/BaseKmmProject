package com.csahmed2020.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform