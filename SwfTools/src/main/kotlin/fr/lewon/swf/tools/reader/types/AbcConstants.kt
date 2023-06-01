package fr.lewon.swf.tools.reader.types

import fr.lewon.swf.tools.reader.SwfByteArrayReader

class AbcConstants(
    val minorVersion: Int,
    val majorVersion: Int,
    val ints: MutableList<Long> = ArrayList(),
    val uints: MutableList<Long> = ArrayList(),
    val doubles: MutableList<Double> = ArrayList(),
    val decimals: MutableList<ByteArray> = ArrayList(),
    val floats: MutableList<Float> = ArrayList(),
    val float4s: MutableList<List<Float>> = ArrayList(),
    val strings: MutableList<String> = ArrayList(),
    val namespaces: MutableList<SwfNamespace> = ArrayList(),
    val namespaceSets: MutableList<ArrayList<Int>> = ArrayList(),
    val multinames: MutableList<SwfMultiname> = ArrayList(),
) : SwfType {
    override fun read(stream: SwfByteArrayReader) {
        ints.add(-1)
        val constantIntPoolCount = stream.readU30()
        for (i in 1 until constantIntPoolCount) {
            ints.add(stream.readS32())
        }

        uints.add(-1)
        val constantUintPoolCount = stream.readU30()
        for (i in 1 until constantUintPoolCount) {
            uints.add(stream.readU32())
        }

        doubles.add(-1.0)
        val constantDoublePoolCount = stream.readU30()
        for (i in 1 until constantDoublePoolCount) {
            doubles.add(stream.readSwfDouble())
        }

        if (minorVersion >= 17) {
            decimals.add(ByteArray(0))
            val constantDecimalPoolCount = stream.readU30()
            for (i in 1 until constantDecimalPoolCount) {
                decimals.add(stream.readSwfDecimal())
            }
        }

        floats.add(0f)
        float4s.add(emptyList())
        if (majorVersion > 47 || majorVersion == 47 && minorVersion >= 16) {
            val constantFloatPoolCount = stream.readU30()
            for (i in 1 until constantFloatPoolCount) {
                floats.add(stream.readSwfFloat())
            }
            val constantFloat4PoolCount = stream.readU30()
            for (i in 1 until constantFloat4PoolCount) {
                float4s.add(stream.readSwfFloat4())
            }
        }

        strings.add("")
        val constantStringPoolCount = stream.readU30()
        for (i in 1 until constantStringPoolCount) {
            strings.add(stream.readSwfAbcString())
        }

        namespaces.add(SwfNamespace())
        val constantNamespacePoolCount = stream.readU30()
        for (i in 1 until constantNamespacePoolCount) { // index 0 not used. Values 1..n-1
            namespaces.add(SwfNamespace().also { it.read(stream) })
        }

        namespaceSets.add(ArrayList())
        val constantNamespaceSetPoolCount = stream.readU30()
        for (i in 1 until constantNamespaceSetPoolCount) {
            val namespaceSet = ArrayList<Int>()
            val namespaceCount = stream.readU30()
            for (j in 0 until namespaceCount) {
                namespaceSet.add(stream.readU30())
            }
            namespaceSets.add(namespaceSet)
        }

        multinames.add(SwfMultiname())
        val constantMultinamePoolCount = stream.readU30()
        for (i in 1 until constantMultinamePoolCount) {
            multinames.add(SwfMultiname().also { it.read(stream) })
        }
    }
}