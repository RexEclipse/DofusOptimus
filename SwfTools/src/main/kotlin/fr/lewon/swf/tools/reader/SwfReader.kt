package fr.lewon.swf.tools.reader

import fr.lewon.swf.tools.tags.SwfHeader
import fr.lewon.swf.tools.tags.SwfTagTypes
import java.io.File
import java.io.FileInputStream
import kotlin.math.ceil

object SwfReader {

    fun readTags(swfFile: File, toReadTagTypes: List<SwfTagTypes>): Map<SwfTagTypes, List<Any>> {
        val rawStream = FileInputStream(swfFile).use { inputStream ->
            SwfByteArrayReader(inputStream.readAllBytes())
        }
        val header = SwfHeader().also { it.read(rawStream) }
        val contentStream = getContentStream(header, rawStream)
        val rectBits = contentStream.readUnsignedByte() shr 3
        val totalBits = rectBits * 4
        val rectBytes = ceil((totalBits - 3).toFloat() / 8f)
        contentStream.readNBytes(rectBytes.toInt())
        contentStream.readUnsignedShort() // frameRate
        contentStream.readUI16() // frameCount

        val result = HashMap<SwfTagTypes, ArrayList<Any>>()
        while (contentStream.available() > 0) {
            val tagCodeAndLength = contentStream.readUI16()
            val tagCode = tagCodeAndLength shr 6
            var tagLength = tagCodeAndLength and 0x3F
            if (tagLength >= 0x3F) {
                tagLength = contentStream.readSI32().toInt()
            }
            val tagStream = SwfByteArrayReader(contentStream.readNBytes(tagLength))

            toReadTagTypes.firstOrNull { it.id == tagCode }?.let { tagType ->
                val tagResults = result.computeIfAbsent(tagType) { ArrayList() }
                tagType.buildTag().read(tagStream)?.let { tagResults.add(it) }
            }
        }
        return result
    }

    private fun getContentStream(header: SwfHeader, stream: SwfByteArrayReader): SwfByteArrayReader {
        stream.setPosition(8)
        if (header.signature == "CWS") {
            return SwfByteArrayReader(stream.uncompress())
        }
        return stream
    }

}