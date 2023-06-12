package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader
import fr.lewon.swf.tools.reader.types.traits.SwfTrait

class SwfMethodBody(
    val minorVersion: Int,
    val majorVersion: Int,
    var methodInfo: Int = 0,
    var maxStack: Int = 0,
    var maxRegs: Int = 0,
    var initScopeDepth: Int = 0,
    var maxScopeDepth: Int = 0,
    var codeBytes: ByteArray = ByteArray(0),
    var exceptions: ArrayList<SwfAbcException> = ArrayList(),
    var traits: ArrayList<SwfTrait> = ArrayList()
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        methodInfo = stream.readU30()
        maxStack = stream.readU30()
        maxRegs = stream.readU30()
        initScopeDepth = stream.readU30()
        maxScopeDepth = stream.readU30()
        val codeLength = stream.readU30()
        codeBytes = stream.readNBytes(codeLength)
        val exCount = stream.readU30()
        for (j in 0 until exCount) {
            exceptions.add(SwfAbcException(minorVersion, majorVersion).also { it.read(stream) })
        }
        traits = stream.readSwfTraits()
    }

    fun readCodeInstructions(abc: Abc): List<String> {
        val instructions = ArrayList<String>()
        val bar = SwfByteArrayReader(codeBytes)
        while (bar.available() > 0) {
            val instructionId = bar.readUnsignedByte()
            println(instructionId)
        }
        return instructions
    }

}