package io.github.amanbutnot.moosic

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform