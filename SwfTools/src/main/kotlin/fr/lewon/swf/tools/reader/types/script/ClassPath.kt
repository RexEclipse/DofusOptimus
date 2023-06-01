package fr.lewon.swf.tools.reader.types.script

data class ClassPath(
    val packageStr: DottedChain,
    val className: String,
    val namespaceSuffix: String
)