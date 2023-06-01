package fr.lewon.swf.tools.reader.impl

import fr.lewon.swf.tools.reader.SwfReader
import fr.lewon.swf.tools.reader.types.Abc
import fr.lewon.swf.tools.reader.types.SwfNamespace
import fr.lewon.swf.tools.reader.types.SwfScriptInfo
import fr.lewon.swf.tools.reader.types.SwfTrait
import fr.lewon.swf.tools.reader.types.script.ClassPath
import fr.lewon.swf.tools.reader.types.script.ScriptPack
import fr.lewon.swf.tools.tags.SwfTagTypes
import java.io.File

object ScriptsReader {

    fun readScripts(swfFile: File) {
        val result = SwfReader.readTags(swfFile, listOf(SwfTagTypes.DO_ABC, SwfTagTypes.DO_ABC_2))
        val abcList = result.values.flatten().filterIsInstance<Abc>()
        val packs = ArrayList<ScriptPack>()
        for (abc in abcList) {
            for ((index, scriptInfo) in abc.scriptInfoList.withIndex()) {
                packs.addAll(getPacks(scriptInfo, abc, index, null, abcList))
            }
        }
    }

    fun getPacks(
        scriptInfo: SwfScriptInfo,
        abc: Abc,
        scriptIndex: Int,
        packagePrefix: String?,
        allAbcs: List<Abc>
    ): List<ScriptPack> {
        val ret = ArrayList<ScriptPack>()
        val otherTraits = ArrayList<SwfTrait>()
        for (trait in scriptInfo.traits) {
            val name = trait.getName(abc)
            val ns = name.getNamespace(abc)
            if (ns.kind != SwfNamespace.KIND_PACKAGE_INTERNAL && ns.kind != SwfNamespace.KIND_PACKAGE) {
                otherTraits.add(trait)
            }
        }
        for (trait in scriptInfo.traits) {
            val name = trait.getName(abc)
            val ns = name.getNamespace(abc)
            if (ns.kind == SwfNamespace.KIND_PACKAGE_INTERNAL || ns.kind == SwfNamespace.KIND_PACKAGE) {
                val packageName = ns.getName(abc)
                val objectName = name.getName(abc)
                val traits = ArrayList<SwfTrait>()
                traits.add(trait)
                if (otherTraits.isNotEmpty()) {
                    traits.addAll(otherTraits)
                    otherTraits.clear()
                }
                if (packagePrefix == null || packageName.toString().startsWith(packagePrefix)) {
                    val cp = ClassPath(packageName, objectName, "")
                    ret.add(ScriptPack(cp, abc, allAbcs, scriptIndex, traits))
                }
            }
        }
        if (ret.isEmpty() && otherTraits.isNotEmpty()) {
            for (trait in otherTraits) {
                val name = trait.getName(abc)
                val ns = name.getNamespace(abc)
                val packageName = ns.getName(abc)
                val objectName = name.getName(abc)
                val traits = ArrayList<SwfTrait>()
                traits.add(trait)
                val cp = ClassPath(packageName, objectName, "")
                ret.add(ScriptPack(cp, abc, allAbcs, scriptIndex, traits))
            }
        }
        return ret
    }

}