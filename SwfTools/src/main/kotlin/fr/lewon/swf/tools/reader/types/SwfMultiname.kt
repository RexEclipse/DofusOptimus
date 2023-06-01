package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

data class SwfMultiname(
    var kind: Int = 0,
    var nameIndex: Int = 0,
    var namespaceIndex: Int = 0,
    var namespaceSetIndex: Int = 0,
    var qnameIndex: Int = 0,
    var params: ArrayList<Int> = ArrayList()
) : SwfType {
    companion object {
        const val QNAME = 7
        const val QNAMEA = 0x0d
        const val RTQNAME = 0x0f
        const val RTQNAMEA = 0x10
        const val RTQNAMEL = 0x11
        const val RTQNAMELA = 0x12
        const val MULTINAME = 0x09
        const val MULTINAMEA = 0x0e
        const val MULTINAMEL = 0x1b
        const val MULTINAMELA = 0x1c
        const val TYPENAME = 0x1d
    }

    override fun read(stream: SwfByteArrayReader) {
        kind = stream.readUnsignedByte()
        when (kind) {
            QNAME, QNAMEA -> {
                namespaceIndex = stream.readU30()
                nameIndex = stream.readU30()
            }
            RTQNAME, RTQNAMEA -> {
                nameIndex = stream.readU30()
            }
            RTQNAMEL, RTQNAMELA -> {
                // Nothing
            }
            MULTINAME, MULTINAMEA -> {
                nameIndex = stream.readU30()
                namespaceSetIndex = stream.readU30()
            }
            MULTINAMEL, MULTINAMELA -> {
                namespaceSetIndex = stream.readU30()
            }
            TYPENAME -> {
                qnameIndex = stream.readU30()
                val paramsLength = stream.readU30()
                for (i in 0 until paramsLength) {
                    params.add(stream.readU30())
                }
            }
            else -> error("Unknown kind of Multiname:0x" + Integer.toHexString(kind))
        }
    }

    fun getNamespace(abc: Abc): SwfNamespace {
        return abc.constants.namespaces[namespaceIndex]
    }

    fun getName(abc: Abc): String {
        if (kind == TYPENAME) {
            return typeNameToStr(abc)
        }
        if (nameIndex == -1) {
            return ""
        }
        val suffix = if (nameIndex == 0) "*" else abc.constants.strings[nameIndex]
        val prefix = if (isAttribute()) "@" else ""
        return "$prefix$suffix"
    }

    private fun typeNameToStr(abc: Abc): String {
        if (abc.constants.multinames[qnameIndex].nameIndex == nameIndex) {
            return "ambiguousTypeName"
        }
        val typeNameStr = StringBuilder()
        typeNameStr.append(abc.constants.multinames[qnameIndex].getName(abc))
        if (params.isNotEmpty()) {
            typeNameStr.append(".<")
            for ((index, param) in params.withIndex()) {
                if (index > 0) {
                    typeNameStr.append(",")
                }
                if (param == 0) {
                    typeNameStr.append("*")
                } else {
                    typeNameStr.append(abc.constants.multinames[param].getName(abc))
                }
            }
            typeNameStr.append(">")
        }
        return typeNameStr.toString()
    }

    private fun isAttribute() = listOf(QNAMEA, MULTINAMEA, RTQNAMEA, RTQNAMELA, MULTINAMELA).contains(kind)
}