package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.enums.MultinameKinds

data class SwfMultiname(
    var kind: MultinameKinds = MultinameKinds.QNAME,
    var nameIndex: Int = 0,
    var namespaceIndex: Int = 0,
    var namespaceSetIndex: Int = 0,
    var qnameIndex: Int = 0,
    var params: ArrayList<Int> = ArrayList()
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        val kindIntValue = stream.readUnsignedByte()
        kind = MultinameKinds.values().firstOrNull { it.intValue == kindIntValue }
            ?: error("Unknown kind : $kindIntValue")
        when (kind) {
            MultinameKinds.QNAME, MultinameKinds.QNAMEA -> {
                namespaceIndex = stream.readU30()
                nameIndex = stream.readU30()
            }

            MultinameKinds.RTQNAME, MultinameKinds.RTQNAMEA -> {
                nameIndex = stream.readU30()
            }

            MultinameKinds.RTQNAMEL, MultinameKinds.RTQNAMELA -> {
                // Nothing
            }

            MultinameKinds.MULTINAME, MultinameKinds.MULTINAMEA -> {
                nameIndex = stream.readU30()
                namespaceSetIndex = stream.readU30()
            }

            MultinameKinds.MULTINAMEL, MultinameKinds.MULTINAMELA -> {
                namespaceSetIndex = stream.readU30()
            }

            MultinameKinds.TYPENAME -> {
                qnameIndex = stream.readU30()
                val paramsLength = stream.readU30()
                for (i in 0 until paramsLength) {
                    params.add(stream.readU30())
                }
            }
        }
    }

    fun getNamespace(abc: Abc): SwfNamespace {
        return abc.constants.namespaces[namespaceIndex]
    }

    fun getName(abc: Abc): String {
        if (kind == MultinameKinds.TYPENAME) {
            return typeNameToStr(abc)
        }
        if (nameIndex == -1) {
            return ""
        }
        val suffix = if (nameIndex == 0) "*" else abc.constants.strings[nameIndex]
        val prefix = if (kind.isAttribute) "@" else ""
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

}