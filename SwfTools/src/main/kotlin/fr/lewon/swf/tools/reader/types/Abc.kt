package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

open class Abc(
    var constants: AbcConstants = AbcConstants(-1, -1),
    var methodInfoList: ArrayList<SwfMethodInfo> = ArrayList(),
    var metadataInfoList: ArrayList<SwfMetadataInfo> = ArrayList(),
    var instanceInfoList: ArrayList<SwfInstanceInfo> = ArrayList(),
    var classInfoList: ArrayList<SwfClassInfo> = ArrayList(),
    var scriptInfoList: ArrayList<SwfScriptInfo> = ArrayList(),
    var bodies: ArrayList<SwfMethodBody> = ArrayList()
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        val minorVersion = stream.readUI16()
        val majorVersion = stream.readUI16()
        constants = AbcConstants(minorVersion, majorVersion).also { it.read(stream) }

        val methodsCount = stream.readU30()
        for (i in 0 until methodsCount) {
            methodInfoList.add(SwfMethodInfo().also { it.read(stream) })
        }

        val metadataCount = stream.readU30()
        for (i in 0 until metadataCount) {
            metadataInfoList.add(SwfMetadataInfo().also { it.read(stream) })
        }

        val classCount = stream.readU30()
        for (i in 0 until classCount) {
            instanceInfoList.add(SwfInstanceInfo().also { it.read(stream) })
        }
        for (i in 0 until classCount) {
            classInfoList.add(SwfClassInfo().also { it.read(stream) })
        }

        val scriptCount = stream.readU30()
        for (i in 0 until scriptCount) {
            scriptInfoList.add(SwfScriptInfo().also { it.read(stream) })
        }

        val bodiesCount = stream.readU30()
        for (i in 0 until bodiesCount) {
            bodies.add(SwfMethodBody(minorVersion, majorVersion).also { it.read(stream) })
        }
    }

}