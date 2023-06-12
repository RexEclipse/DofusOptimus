package fr.lewon.swf.tools.reader.types.traits

import fr.lewon.swf.tools.reader.types.Abc

class SwfTraitClass(
    var slotId: Int = 0,
    var classInfo: Int = 0
) : SwfTrait() {

    override fun getInstructions(abc: Abc): List<String> {
        val swfClassInfo = abc.classInfoList[classInfo]
        val instructions = ArrayList<String>()
        instructions.addAll(swfClassInfo.staticTraits.flatMap { it.getInstructions(abc) })
        swfClassInfo.cinitIndex.takeIf { it > 0 }?.let {
            val methodInfo = abc.methodInfoList[it]
            val methodBody = abc.methodBodyByMethodInfo[methodInfo]
            println(methodBody?.readCodeInstructions(abc))
        }
        return instructions
    }
}