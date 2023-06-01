package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

class SwfScriptInfo(
    var initIndex: Int = 0,
    var traits: ArrayList<SwfTrait> = ArrayList(),
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        initIndex = stream.readU30()
        traits = stream.readSwfTraits()
    }
}