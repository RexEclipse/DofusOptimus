package fr.lewon.swf.tools.tags

import fr.lewon.swf.tools.reader.SwfByteArrayReader

class SwfHeader(
    var signature: String = "",
    var version: Int = 0,
    var fileSize: Int = 0
) {
    fun read(bar: SwfByteArrayReader) {
        signature = bar.readString(3)
        version = bar.readUnsignedByte()
        fileSize = bar.readUI32()
    }

}