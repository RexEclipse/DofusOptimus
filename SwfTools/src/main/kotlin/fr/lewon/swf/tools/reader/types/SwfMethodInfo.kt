package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

data class SwfMethodInfo(
    var paramTypes: ArrayList<Int> = ArrayList(),
    var retType: Int = -1,
    var nameIndex: Int = -1,
    var flags: Int = -1,
    var optional: ArrayList<SwfValueKind> = ArrayList(),
    var paramNames: ArrayList<Int> = ArrayList()
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        val paramCount = stream.readU30()
        retType = stream.readU30()
        for (i in 0 until paramCount) {
            paramTypes.add(stream.readU30())
        }
        nameIndex = stream.readU30()
        flags = stream.read()

        if (flags and 8 == 8) {
            val optionalCount = stream.readU30()
            for (i in 0 until optionalCount) {
                optional.add(SwfValueKind().also { it.read(stream) })
            }
        }

        if (flags and 128 == 128) {
            for (i in 0 until paramCount) {
                paramNames.add(stream.readU30())
            }
        }
    }
}