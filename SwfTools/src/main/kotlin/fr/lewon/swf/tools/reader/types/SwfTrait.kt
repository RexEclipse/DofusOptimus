package fr.lewon.swf.tools.reader.types

open class SwfTrait(
    var nameIndex: Int = 0,
    var kindType: Int = 0,
    var kindFlags: Int = 0,
    var metadata: ArrayList<Int> = ArrayList(),
    var fileOffset: Long = 0,
) {
    fun getName(abc: Abc): SwfMultiname {
        return abc.constants.multinames[nameIndex]
    }
}