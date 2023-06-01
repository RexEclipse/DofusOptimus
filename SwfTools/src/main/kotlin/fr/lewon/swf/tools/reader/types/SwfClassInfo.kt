package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

class SwfClassInfo(
    var cinitIndex: Int = 0,
    var staticTraits: ArrayList<SwfTrait> = ArrayList()
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        cinitIndex = stream.readU30()
        staticTraits = stream.readSwfTraits()
    }
}