package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

interface SwfType {
    fun read(stream: SwfByteArrayReader)
}