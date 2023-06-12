package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.traits.SwfTrait

class SwfScriptInfo(
    var initIndex: Int = 0,
    var traits: ArrayList<SwfTrait> = ArrayList(),
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        initIndex = stream.readU30()
        traits = stream.readSwfTraits()
    }
}