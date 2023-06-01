package fr.lewon.swf.tools.tags.impl

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.Abc2

class DoAbc2Tag : DoAbcTag() {
    override fun read(stream: SwfByteArrayReader): Abc2 {
        return Abc2().also { it.read(stream) }
    }
}