package fr.lewon.dofus.bot.core.model.spell

enum class DofusSpellEffectType(val globalType: DofusSpellEffectGlobalType, private val id: Int) {
    SWITCH_POSITIONS(DofusSpellEffectGlobalType.MOVE, 8),
    WATER_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 91),
    EARTH_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 92),
    AIR_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 93),
    FIRE_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 94),
    NEUTRAL_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 95),
    WATER_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 96),
    EARTH_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 97),
    AIR_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 98),
    FIRE_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 99),
    NEUTRAL_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 100),
    MP_DECREASED_EARTH_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 1016),
    BEST_ELEMENT_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 2822),
    WORST_ELEMENT_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 2832),
    HEAL(DofusSpellEffectGlobalType.HEAL, 108),
    HEAL_PERCENT_RECEIVED_DAMAGE(DofusSpellEffectGlobalType.HEAL, 786),
    TELEPORT(DofusSpellEffectGlobalType.MOVE, 4),
    PUSH(DofusSpellEffectGlobalType.MOVE, 5),
    PULL(DofusSpellEffectGlobalType.MOVE, 6),
    DASH(DofusSpellEffectGlobalType.MOVE, 1042),
    AP_BUFF(DofusSpellEffectGlobalType.BUFF, 111),
    DAMAGE_BUFF(DofusSpellEffectGlobalType.BUFF, 112),
    CRITICAL_BUFF(DofusSpellEffectGlobalType.BUFF, 115),
    MP_BUFF(DofusSpellEffectGlobalType.BUFF, 128),
    POWER_BUFF(DofusSpellEffectGlobalType.BUFF, 138),
    POWER_SPELL_BUFF(DofusSpellEffectGlobalType.BUFF, 1054),
    FINAL_DAMAGE_PERCENT_BUFF(DofusSpellEffectGlobalType.BUFF, 1171),
    VITALITY_PERCENT_BUFF(DofusSpellEffectGlobalType.BUFF, 1078),
    TACLE_DEBUFF(DofusSpellEffectGlobalType.DEBUFF, 755),
    AP_DEBUFF(DofusSpellEffectGlobalType.DEBUFF, 1079),
    MP_DEBUFF(DofusSpellEffectGlobalType.DEBUFF, 1080),
    STATE_CHANGE(DofusSpellEffectGlobalType.STATE_CHANGE, 950),
    CUSTOM_3(DofusSpellEffectGlobalType.CUSTOM, 1019),
    SIZE_UPGRADE(DofusSpellEffectGlobalType.CUSTOM, 1060),
    CUSTOM_4(DofusSpellEffectGlobalType.CUSTOM, 1160),
    CUSTOM(DofusSpellEffectGlobalType.CUSTOM, 2160),
    CUSTOM_2(DofusSpellEffectGlobalType.CUSTOM, 2794),
    ;

    companion object {
        fun fromEffectId(id: Int): DofusSpellEffectType? {
            return values().firstOrNull { it.id == id }
        }
    }
}