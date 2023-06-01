package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.script.DottedChain

data class SwfNamespace(
    var kind: Int = -1,
    var nameIndex: Int = -1
) : SwfType {
    companion object {
        const val KIND_NAMESPACE = 8
        const val KIND_PRIVATE = 5
        const val KIND_PACKAGE = 22
        const val KIND_PACKAGE_INTERNAL = 23
        const val KIND_PROTECTED = 24
        const val KIND_EXPLICIT = 25
        const val KIND_STATIC_PROTECTED = 26

        private val nameSpaceKinds = intArrayOf(
            KIND_NAMESPACE,
            KIND_PRIVATE,
            KIND_PACKAGE,
            KIND_PACKAGE_INTERNAL,
            KIND_PROTECTED,
            KIND_EXPLICIT,
            KIND_STATIC_PROTECTED
        )
    }

    override fun read(stream: SwfByteArrayReader) {
        kind = stream.read()
        nameIndex = nameSpaceKinds.firstOrNull { it == kind }?.let {
            stream.readU30()
        } ?: 0
    }

    fun getName(abc: Abc): DottedChain {
        return if (nameIndex == 0 || nameIndex == -1) {
            DottedChain.EMPTY
        } else {
            val str = abc.constants.strings[nameIndex]
            DottedChain.parseWithSuffix(str)
        }
    }
}