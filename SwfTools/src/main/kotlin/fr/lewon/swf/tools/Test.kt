package fr.lewon.swf.tools

import fr.lewon.dofus.bot.core.io.gamefiles.VldbFilesUtil
import fr.lewon.swf.tools.reader.impl.ScriptsReader
import java.io.File

fun main() {
    //val swfFile = "C:/Dev/test.swf" // Replace with the actual path to your SWF file
    val swfFile = "${VldbFilesUtil.getDofusDirectory()}\\DofusInvoker.swf"
    ScriptsReader.readScripts(File(swfFile))
}