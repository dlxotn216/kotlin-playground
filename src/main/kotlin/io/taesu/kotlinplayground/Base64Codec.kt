package io.taesu.kotlinplayground

import org.slf4j.LoggerFactory
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.*

/**
 * Created by itaesu on 2024. 8. 20..
 *
 * @author Lee Tae Su
 * @version kotlin-playground
 * @since kotlin-playground
 */
class Base64Codec {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getBase64File(
        encodedFile: Path,
        inputStream: InputStream,
        printWriter: PrintWriter,
    ): Path {
        val encoder = Base64.getEncoder()
        val bufferSize = 1024 * 3
        val buffer = ByteArray(bufferSize)
        var readLength: Int
        while ((inputStream.read(buffer).also { readLength = it }) > 0) {
            val tmp = buffer.copyOf(readLength)
            log.info("Read length: ${readLength}, buffer size: ${buffer.size}, tmp size: ${tmp.size}")
            printWriter.println(String(encoder.encode(tmp), StandardCharsets.UTF_8))
        }
        return encodedFile
    }

    fun getBase64File(
        encodedFile: Path,
        inputStream: BufferedInputStream,
        printWriter: PrintWriter,
    ): Path {
        val encoder = Base64.getEncoder()
        val bufferSize = 1024 * 3
        val buffer = ByteArray(bufferSize)
        var readLength: Int
        while ((inputStream.read(buffer).also { readLength = it }) > 0) {
            // 반드시 copy가 필요함. readLength가 bufferSize 만큼 채우지 못했다면 이전 단계에서 읽은 디에터가 남아있음
            val tmp = buffer.copyOf(readLength)
            log.info("Read length: ${readLength}, buffer size: ${buffer.size}, tmp size: ${tmp.size}")
            printWriter.println(String(encoder.encode(tmp)))
        }
        return encodedFile
    }

    fun decode(value: String): ByteArray {
        val decoder = Base64.getDecoder()
        val decoded = decoder.decode(value)
        return decoded
    }
}
