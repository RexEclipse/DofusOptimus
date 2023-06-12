package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.traits.SwfTrait

class SwfInstanceInfo(
    var nameIndex: Int = 0,
    var superIndex: Int = 0,
    var flags: Int = 0,
    var protectedNS: Int = 0,
    var interfaces: ArrayList<Int> = ArrayList(),
    var iinitIndex: Int = 0,
    var instanceTraits: ArrayList<SwfTrait> = ArrayList()
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        nameIndex = stream.readU30()
        superIndex = stream.readU30()
        flags = stream.readUnsignedByte()
        if (flags and 8 != 0) {
            protectedNS = stream.readU30()
        }
        val interfacesCount = stream.readU30()
        interfaces = ArrayList()
        for (i in 0 until interfacesCount) {
            interfaces.add(stream.readU30())
        }
        iinitIndex = stream.readU30()
        instanceTraits = stream.readSwfTraits()
    }
}