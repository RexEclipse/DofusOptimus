package fr.lewon.swf.tools.reader.types.traits

import fr.lewon.swf.tools.reader.types.Abc

class SwfTraitMethodGetterSetter(
    var dispId: Int = 0,
    var methodInfo: Int = 0
) : SwfTrait() {
    override fun getInstructions(abc: Abc): List<String> {
        return listOf("methodGetterSetter")
    }
}