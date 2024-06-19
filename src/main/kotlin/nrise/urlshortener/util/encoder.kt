package nrise.urlshortener.util

import java.math.BigInteger
import java.security.MessageDigest

fun String.encodeToID(): String {
    // hash String with MD5
    val hashBytes = MessageDigest.getInstance("MD5").digest(this.toByteArray(Charsets.UTF_8))

    // human readable 한 MD5 스트링으로 변환
    val hashString = String.format("%032x", BigInteger(1, hashBytes))

    // truncate MD5 String
    val truncatedHashString = hashString.take(6)

    return truncatedHashString
}
