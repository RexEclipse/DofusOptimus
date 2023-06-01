package fr.lewon.swf.tools.reader.types.script

class DottedChain(val parts: List<String>, val namespaceSuffix: String, val isNull: Boolean = false) {

    companion object {

        val EMPTY = DottedChain(emptyList(), "", true)
        val TOPLEVEL = DottedChain(emptyList(), "")

        fun parseWithSuffix(name: String?): DottedChain {
            return if (name == null) {
                EMPTY
            } else if (name.isEmpty()) {
                TOPLEVEL
            } else {
                var nameNoSuffix = name
                var namespaceSuffix = ""
                if (name.matches(Regex(".*#\\d+$"))) {
                    nameNoSuffix = name.substring(0, name.lastIndexOf("#"))
                    namespaceSuffix = name.substring(name.lastIndexOf("#"))
                }
                DottedChain(nameNoSuffix.split(Regex("\\.")), namespaceSuffix)
            }
        }
    }

    override fun toString(): String {
        if (isNull) {
            return ""
        }
        if (parts.isEmpty()) {
            return ""
        }
        val ret = StringBuilder()
        for (i in parts.indices) {
            if (i > 0) {
                ret.append(".")
            }
            val part = parts[i]
            ret.append(part)
        }
        return parts.joinToString(".") + namespaceSuffix
    }

}