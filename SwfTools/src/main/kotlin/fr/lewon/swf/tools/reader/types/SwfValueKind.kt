package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

data class SwfValueKind(
    var valueIndex: Int = 0,
    var valueKind: Int = 0
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        valueIndex = stream.readU30()
        valueKind = stream.readUnsignedByte()
    }
}