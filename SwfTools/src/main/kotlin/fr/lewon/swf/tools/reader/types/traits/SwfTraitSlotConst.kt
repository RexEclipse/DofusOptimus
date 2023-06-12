package fr.lewon.swf.tools.reader.types.traits

import com.fasterxml.jackson.databind.ObjectMapper
import fr.lewon.swf.tools.reader.types.Abc

class SwfTraitSlotConst(
    var slotId: Int = 0,
    var typeIndex: Int = 0,
    var valueIndex: Int = 0,
    var valueKind: Int = 0
) : SwfTrait() {
    override fun getInstructions(abc: Abc): List<String> {
        val metadataInfo = abc.metadataInfoList[typeIndex]
        println(ObjectMapper().writeValueAsString(metadataInfo))
        println(abc.constants.strings[metadataInfo.nameIndex])
        val name = getName(abc)
        val multiname = abc.constants.multinames[typeIndex]
        val typeName = multiname.getName(abc)
        return listOf("val $name: $typeName = " +ObjectMapper().writeValueAsString(this))
    }
}