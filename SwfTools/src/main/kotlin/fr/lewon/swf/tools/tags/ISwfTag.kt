package fr.lewon.swf.tools.tags

import fr.lewon.swf.tools.reader.SwfByteArrayReader

interface ISwfTag<T> {
    fun read(stream: SwfByteArrayReader): T
}