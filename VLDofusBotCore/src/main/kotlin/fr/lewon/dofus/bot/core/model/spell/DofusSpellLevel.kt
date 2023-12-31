package fr.lewon.dofus.bot.core.model.spell

import fr.lewon.dofus.bot.core.criterion.DofusCriterion

data class DofusSpellLevel(
    var id: Int,
    var spellId: Int,
    var criticalHitProbability: Int,
    var needFreeCell: Boolean,
    var needTakenCell: Boolean,
    var maxRange: Int,
    var minRange: Int,
    var castInLine: Boolean,
    var rangeCanBeBoosted: Boolean,
    var apCost: Int,
    var castInDiagonal: Boolean,
    var initialCooldown: Int,
    var castTestLos: Boolean,
    var minCastInterval: Int,
    var maxStack: Int,
    var grade: Int,
    var minPlayerLevel: Int,
    var maxCastPerTarget: Int,
    var maxCastPerTurn: Int,
    var forClientOnly: Boolean,
    var effects: List<DofusSpellEffect>,
    var criticalEffects: List<DofusSpellEffect>,
    val isParsedCompletely: Boolean,
    val statesCriterion: DofusCriterion,
)