package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.traits.SwfTrait

class SwfClassInfo(
    var cinitIndex: Int = 0,
    var staticTraits: ArrayList<SwfTrait> = ArrayList()
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        cinitIndex = stream.readU30()
        staticTraits = stream.readSwfTraits()
    }
}