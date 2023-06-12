package fr.lewon.swf.tools.reader.impl

import fr.lewon.swf.tools.reader.SwfReader
import fr.lewon.swf.tools.reader.types.*
import fr.lewon.swf.tools.tags.SwfTagTypes
import java.io.File

object ScriptsReader {

    fun readScripts(swfFile: File) {
        val result = SwfReader.readTags(swfFile, listOf(SwfTagTypes.DO_ABC, SwfTagTypes.DO_ABC_2))
        val abcList = result.values.flatten().filterIsInstance<Abc>()
        for (abc in abcList) {
            val messageReceiverPack = abc.scriptInfoList.firstOrNull {
                it.traits.any { trait -> trait.getName(abc) == "MessageReceiver" }
            } ?: error("script not found")
            println("TRAITS :")
            val instructions = messageReceiverPack.traits.flatMap { it.getInstructions(abc) }
            instructions.forEach { println(it) }
        }
    }

}