package fr.lewon.swf.tools.reader.types.enums

import fr.lewon.swf.tools.reader.SwfByteArrayReader

enum class OperandTypes(val read: (SwfByteArrayReader) -> Any) {
    OPT_U30({ it.readU30() }),
    OPT_U8({ it.readUnsignedByte() }),
    OPT_S24({ it.readS24() }),
    OPT_CASE_OFFSETS({ TODO("") }),
    OPT_S8({ it.readUnsignedByte() }),
    OPT_S16({ it.readU30() }),
}