package fr.lewon.swf.tools.reader.types.enums

enum class Instructions(val id: Int, val operands: List<OperandTypes> = emptyList()) {
    ADD(0XA0),
    ADD_INTEGER(0XC5),
    AS_TYPE(0X86),
    AS_TYPE_RUNTIME(0X87),
    BITWISE_AND(0XA8),
    BITWISE_NOT(0X97),
    BITWISE_OR(0XA9),
    BITWISE_XOR(0XAA),
    CALL_CLOSURE(0X41),
    CALL_METHOD(0X43),
    CALL_PROPERTY(0X46),
    CALL_PROPERTY_NO_SELF(0X4C),
    CALL_PROPERTY_VOID(0X4F),
    CALL_STATIC(0X44),
    CALL_SUPER(0X45),
    CALL_SUPER_VOID(0X4E),
    CHECK_FILTER(0X78),
    COERCE_TO_TYPE(0X80),
    COERCE_TO_ANY(0X82),
    COERCE_TO_STRING(0X85),
    CONSTRUCT(0X42),
    CONSTRUCT_PROP(0X4A),
    CONSTRUCT_SUPER(0X49),
    CONVERT_BOOLEAN(0X76),
    CONVERT_INTEGER(0X73),
    CONVERT_DOUBLE(0X75),
    CONVERT_OBJECT(0X77),
    CONVERT_UNSIGNED(0X74),
    CONVERT_STRING(0X70),
    DEBUG(0XEF),
    DEBUG_FILE(0XF1),
    DEBUG_LINE(0XF0),
    DECREMENT_REGISTER(0X94),
    DECREMENT_REGISTER_INTEGER(0XC3),
    DECREMENT(0X93),
    DECREMENT_INTEGER(0XC1),
    DELETE_PROPERTY(0X6A),
    DIVIDE(0XA3),
    DUPLICATE_STACK_HEAD(0X2A),
    DEFAULT_XML_NAMESPACE(0X06),
    DEFAULT_XML_NAMESPACE_RUNTIME(0X07),
    EQUALS(0XAB),
    ESCAPE_XML_ATTRIBUTE(0X72),
    ESCAPE_XML_ELEMENT(0X71),
    FIND_PROPERTY(0X5E),
    FIND_PROPERTY_OR_EXCEPTION(0X5D),
    GET_DESCENDANTS(0X59),
    GET_GLOBAL_SCOPE(0X64),
    GET_GLOBAL_SLOT(0X6E),
    FIND_AND_GET_PROPERTY(0X60),
    GET_REGISTER(0X62),
    GET_REGISTER_0(0XD0),
    GET_REGISTER_1(0XD1),
    GET_REGISTER_2(0XD2),
    GET_REGISTER_3(0XD3),
    GET_PROPERTY(0X66),
    GET_SCOPE_OBJECT(0X65),
    GET_SLOT(0X6C),
    GET_PROP_FROM_SUPER(0X04),
    GREATER_THAN_EQUALS(0XAF),
    GREATER_THAN(0XB0),
    HAS_MORE_PROPERTIES(0X1F),
    HAS_MORE_PROPERTIES_BY_REF(0X32),
    BRANCH_EQUALS(0X13),
    BRANCH_FALSE(0X12),
    BRANCH_GREATER_EQUAL(0X18),
    BRANCH_GREATER(0X17),
    BRANCH_LESS_EQUAL(0X16),
    BRANCH_LESS(0X15),
    BRANCH_NOT_GREATER_EQUAL(0X0F),
    BRANCH_NOT_GREATER(0X0E),
    BRANCH_NOT_LESS_EQUAL(0X0D),
    BRANCH_NOT_LESS(0X0C),
    BRANCH_NOT_EQUAL(0X14),
    BRANCH_STRICT_EQUAL(0X19),
    BRANCH_STRICT_NOT_EQUAL(0X1A),
    BRANCH_TRUE(0X11),
    IN(0XB4),
    INCREMENT_REGISTER(0X92),
    INCREMENT_REGISTER_INTEGER(0XC2),
    INCREMENT(0X91),
    INCREMENT_INTEGER(0XC0),
    INITIALIZE_PROPERTY(0X68),
    INSTANCE_OF(0XB1),
    IS_TYPE(0XB2),
    IS_TYPE_RUNTIME(0XB3),
    JUMP(0X10),
    KILL_REGISTER(0X08),
    LABEL(0X09),
    LESS_THAN_EQUAL(0XAE),
    LESS_THAN(0XAD),
    SWITCH(0X1B),
    BITWISE_SHIFT_LEFT(0XA5),
    MODULO(0XA4),
    MULTIPLY(0XA2),
    MULTIPLY_INTEGER(0XC7),
    NEGATE(0X90),
    NEGATE_INTEGER(0XC4),
    NEW_ACTIVATION(0X57),
    NEW_ARRAY(0X56),
    NEW_CATCH_SCOPE(0X5A),
    NEW_CLASS(0X58),
    NEW_FUNCTION(0X40),
    NEW_OBJECT(0X55),
    NEXT_PROPERTY_NAME(0X1E),
    NEXT_PROPERTY_VALUE(0X23),
    NOP(0X02),
    BOOLEAN_NOT(0X96),
    POP_VALUE(0X29),
    POP_SCOPE(0X1D),
    PUSH_BYTE(0X24),
    PUSH_DOUBLE(0X2F),
    PUSH_FALSE(0X27),
    PUSH_INTEGER(0X2D),
    PUSH_NAMESPACE(0X31),
    PUSH_NAN(0X28),
    PUSH_NULL(0X20),
    PUSH_SCOPE(0X30),
    PUSH_SHORT(0X25),
    PUSH_STRING(0X2C),
    PUSH_TRUE(0X26),
    PUSH_UNSIGNED_INTEGER(0X2E),
    PUSH_UNDEFINED(0X21),
    PUSH_WITH(0X1C),
    RETURN_VALUE(0X48),
    RETURN_VOID(0X47),
    BITWISE_SHIFT_RIGHT(0XA6),
    SET_REGISTER(0X63),
    SET_REGISTER_0(0XD4),
    SET_REGISTER_1(0XD5),
    SET_REGISTER_2(0XD6),
    SET_REGISTER_3(0XD7),
    SET_GLOBAL_SLOT(0X6F),
    SET_PROPERTY(0X61),
    SET_SLOT(0X6D),
    SET_SUPER(0X05),
    EQUALS_STRICT(0XAC),
    SUBTRACT(0XA1),
    SUBTRACT_INTEGER(0XC6),
    SWAP(0X2B),
    THROW_EXCEPTION(0X03),
    TYPENAME(0X95),
    UNSIGNED_BITWISE_SHIFT_RIGHT(0XA7),
    APPLY_TYPE(0X53),
    FIND_DEFINITION(0X5F),
    LOAD_INTEGER_8(0X35),
    LOAD_INTEGER_16(0X36),
    LOAD_INTEGER_32(0X37),
    LOAD_FLOAT_32(0X38),
    LOAD_FLOAT_64(0X39),
    STORE_INTEGER_8(0X3A),
    STORE_INTEGER_16(0X3B),
    STORE_INTEGER_32(0X3C),
    STORE_FLOAT_32(0X3D),
    STORE_FLOAT_64(0X3E),
    SIGN_EXTEND_1(0X50),
    SIGN_EXTEND_8(0X51),
    SIGN_EXTEND_16(0X52),
    BREAKPOINT(0X01),
    PUSH_FLOAT(0X22),
    PUSH_DECIMAL(0X33),
    PUSH_DECIMAL_NAN(0X34),
    CALL_INTERFACE(0X4D),
}