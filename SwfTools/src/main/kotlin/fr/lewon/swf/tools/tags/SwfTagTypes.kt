package fr.lewon.swf.tools.tags

import fr.lewon.swf.tools.tags.impl.DoAbc2Tag
import fr.lewon.swf.tools.tags.impl.DoAbcTag

enum class SwfTagTypes(val id: Int, val tagName: String, val buildTag: () -> ISwfTag<*>) {
    DO_ABC(72, "DoAbc", { DoAbcTag() }),
    DO_ABC_2(82, "DoAbc2", { DoAbc2Tag() }),
}