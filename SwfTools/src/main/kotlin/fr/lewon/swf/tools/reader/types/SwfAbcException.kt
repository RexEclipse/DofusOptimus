package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

class SwfAbcException(
    val minorVersion: Int,
    val majorVersion: Int,
    var start: Int = 0,
    var end: Int = 0,
    var target: Int = 0,
    var typeIndex: Int = 0,
    var nameIndex: Int = 0,
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        start = stream.readU30()
        end = stream.readU30()
        target = stream.readU30()
        typeIndex = stream.readU30()
        if (majorVersion > 46 || majorVersion == 46 && minorVersion >= 15) {
            nameIndex = stream.readU30()
        }
    }
}