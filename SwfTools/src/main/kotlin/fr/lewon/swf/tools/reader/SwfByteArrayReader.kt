package fr.lewon.swf.tools.reader

import fr.lewon.dofus.bot.core.io.stream.ByteArrayReader
import fr.lewon.swf.tools.reader.types.traits.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class SwfByteArrayReader(byteArray: ByteArray) : ByteArrayReader(byteArray) {

    fun readU30() = (readU32() and 0x3FFFFFFF).toInt()

    fun readU32(): Long {
        var i: Int
        var ret: Long = 0
        var bytePos = 0
        var byteCount = 0
        var nextByte: Boolean
        do {
            i = readUnsignedByte()
            nextByte = i shr 7 == 1
            i = i and 0x7f
            ret += i.toLong() shl bytePos
            byteCount++
            bytePos += 7
        } while (nextByte && byteCount < 5)
        return ret
    }

    fun readS32(): Long {
        var i: Int
        var ret: Long = 0
        var bytePos = 0
        var byteCount = 0
        var nextByte: Boolean
        do {
            i = readUnsignedByte()
            nextByte = i shr 7 == 1
            i = i and 0x7f
            ret += (i shl bytePos).toLong()
            byteCount++
            bytePos += 7
            if (bytePos == 35) {
                if (ret shr 31 == 1L) {
                    ret = -(ret and 0x7fffffffL)
                }
                break
            }
        } while (nextByte && byteCount < 5)
        return ret
    }

    fun readUI16() = readUnsignedByte() +
            (readUnsignedByte() shl 8)

    fun readUI32() = readUnsignedByte() +
            (readUnsignedByte() shl 8) +
            (readUnsignedByte() shl 16) +
            (readUnsignedByte() shl 24) and -0x1

    fun readUI30() = readUI32() and 0x3FFFFFFF

    fun readSI32(): Long {
        var uVal = (readUnsignedByte() +
                (readUnsignedByte() shl 8) +
                (readUnsignedByte() shl 16) +
                (readUnsignedByte() shl 24)).toLong()
        if (uVal >= -0x80000000) {
            uVal = -((uVal.inv() and 0xffffffffL) + 1)
        }
        return uVal
    }

    fun readSI16(): Int {
        var uVal = readUnsignedByte() +
                (readUnsignedByte() shl 8)
        if (uVal >= -0x8000) {
            uVal = -((uVal.inv() and 0xffff) + 1)
        }
        return uVal
    }

    fun readSwfFloat4(): List<Float> {
        return listOf(
            readSwfFloat(),
            readSwfFloat(),
            readSwfFloat(),
            readSwfFloat()
        )
    }

    fun readSwfFloat(): Float {
        return java.lang.Float.intBitsToFloat(readUI16())
    }

    fun readSwfDouble(): Double {
        return java.lang.Double.longBitsToDouble(readSwfLong())
    }

    fun readSwfLong(): Long {
        val readBuffer: ByteArray = readNBytes(8)
        return ((readBuffer[7].toLong() shl 56)
                + ((readBuffer[6].toInt() and 255).toLong() shl 48)
                + ((readBuffer[5].toInt() and 255).toLong() shl 40)
                + ((readBuffer[4].toInt() and 255).toLong() shl 32)
                + ((readBuffer[3].toInt() and 255).toLong() shl 24)
                + (readBuffer[2].toInt() and 255 shl 16)
                + (readBuffer[1].toInt() and 255 shl 8)
                + (readBuffer[0].toInt() and 255))
    }

    fun readSwfDecimal(): ByteArray {
        return readNBytes(16)
    }

    fun readSwfString(): String {
        val baos = ByteArrayOutputStream()
        var r: Int
        while (true) {
            r = this.readUnsignedByte()
            if (r == 0) {
                return String(baos.toByteArray(), Charsets.UTF_8)
            }
            baos.write(r)
        }
    }

    fun readSwfAbcString(): String {
        val length = readU30()
        val stringDataBuffer = readNBytes(length)
        return String(stringDataBuffer, 0, length, Charsets.UTF_8)
    }

    fun readSwfTraits(): ArrayList<SwfTrait> {
        val count: Int = readU30()
        val traits = ArrayList<SwfTrait>()
        for (i in 0 until count) {
            traits.add(readSwfTrait())
        }
        return traits
    }

    fun readSwfTrait(): SwfTrait {
        val pos = getPosition().toLong()
        val nameIndex: Int = readU30()
        val kind = read()
        val kindType = 0xf and kind
        val kindFlags = kind shr 4
        val trait: SwfTrait
        when (kindType) {
            0, 6 -> {
                val t1 = SwfTraitSlotConst()
                t1.slotId = readU30()
                t1.typeIndex = readU30()
                t1.valueIndex = readU30()
                if (t1.valueIndex != 0) {
                    t1.valueKind = read()
                }
                trait = t1
            }
            1, 2, 3 -> {
                val t2 = SwfTraitMethodGetterSetter()
                t2.dispId = readU30()
                t2.methodInfo = readU30()
                trait = t2
            }
            4 -> {
                val t3 = SwfTraitClass()
                t3.slotId = readU30()
                t3.classInfo = readU30()
                trait = t3
            }
            5 -> {
                val t4 = SwfTraitFunction()
                t4.slotId = readU30()
                t4.methodInfo = readU30()
                trait = t4
            }
            else -> throw IOException("Unknown trait kind:$kind")
        }
        trait.fileOffset = pos
        trait.kindType = kindType
        trait.kindFlags = kindFlags
        trait.nameIndex = nameIndex
        if (kindFlags and 4 != 0) {
            val metadataCount: Int = readU30()
            trait.metadata = ArrayList(metadataCount)
            for (i in 0 until metadataCount) {
                trait.metadata.add(readU30())
            }
        }
        return trait
    }

}