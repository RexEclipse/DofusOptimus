package fr.lewon.swf.tools.reader.types.traits

import fr.lewon.swf.tools.reader.types.Abc
import fr.lewon.swf.tools.reader.types.SwfMultiname

abstract class SwfTrait(
    var nameIndex: Int = 0,
    var kindType: Int = 0,
    var kindFlags: Int = 0,
    var metadata: ArrayList<Int> = ArrayList(),
    var fileOffset: Long = 0,
) {
    fun getMultiname(abc: Abc): SwfMultiname {
        return abc.constants.multinames[nameIndex]
    }

    fun getPackageName(abc: Abc): String =
        getMultiname(abc).getNamespace(abc).getName(abc).toString()

    fun getName(abc: Abc): String =
        getMultiname(abc).getName(abc)

    abstract fun getInstructions(abc: Abc): List<String>
}