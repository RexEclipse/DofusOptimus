package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

class SwfMetadataInfo(
    var nameIndex: Int = -1,
    var keys: ArrayList<Int> = ArrayList(),
    var values: ArrayList<Int> = ArrayList()
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        nameIndex = stream.readU30()
        val valuesCount = stream.readU30()
        for (v in 0 until valuesCount) {
            keys.add(stream.readU30())
        }
        for (v in 0 until valuesCount) {
            values.add(stream.readU30())
        }
    }
}