package fr.lewon.swf.tools.reader.types.script

import fr.lewon.swf.tools.reader.types.Abc
import fr.lewon.swf.tools.reader.types.SwfTrait

data class ScriptPack(
    val path: ClassPath,
    val abc: Abc,
    var allAbcs: List<Abc>,
    val scriptIndex: Int,
    val traits: ArrayList<SwfTrait>,
)