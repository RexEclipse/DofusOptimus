package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

class Abc2(var flags: Int = 0, var name: String = "") : Abc() {
    override fun read(stream: SwfByteArrayReader) {
        flags = stream.readUI32()
        name = stream.readSwfString()
        return super.read(stream)
    }
}