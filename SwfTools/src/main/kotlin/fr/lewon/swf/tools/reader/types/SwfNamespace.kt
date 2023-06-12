package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.enums.NamespaceKinds

data class SwfNamespace(
    var kind: Int = -1,
    var nameIndex: Int = -1
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        kind = stream.read()
        nameIndex = NamespaceKinds.values().firstOrNull { it.intValue == kind }?.let {
            stream.readU30()
        } ?: 0
    }

    fun getName(abc: Abc): String =
        abc.constants.strings[nameIndex]
}