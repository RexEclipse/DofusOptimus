package fr.lewon.swf.tools.tags.impl

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.Abc
import fr.lewon.swf.tools.tags.ISwfTag

open class DoAbcTag : ISwfTag<Abc> {
    override fun read(stream: SwfByteArrayReader): Abc {
        return Abc().also { it.read(stream) }
    }
}