package fr.lewon.swf.tools.reader.types.enums

enum class NamespaceKinds(val intValue: Int) {
    KIND_NAMESPACE(8),
    KIND_PRIVATE(5),
    KIND_PACKAGE(22),
    KIND_PACKAGE_INTERNAL(23),
    KIND_PROTECTED(24),
    KIND_EXPLICIT(25),
    KIND_STATIC_PROTECTED(26),
}