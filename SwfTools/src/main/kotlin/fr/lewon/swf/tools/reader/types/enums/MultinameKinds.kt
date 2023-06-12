package fr.lewon.swf.tools.reader.types.enums

enum class MultinameKinds(val intValue: Int, val isAttribute: Boolean = false) {
    QNAME(7),
    QNAMEA(0x0d, true),
    RTQNAME(0x0f),
    RTQNAMEA(0x10, true),
    RTQNAMEL(0x11),
    RTQNAMELA(0x12, true),
    MULTINAME(0x09),
    MULTINAMEA(0x0e, true),
    MULTINAMEL(0x1b),
    MULTINAMELA(0x1c, true),
    TYPENAME(0x1d),
}