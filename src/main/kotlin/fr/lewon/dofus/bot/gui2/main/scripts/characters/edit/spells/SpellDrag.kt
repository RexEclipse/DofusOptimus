package fr.lewon.dofus.bot.gui2.main.scripts.characters.edit.spells

import fr.lewon.dofus.bot.core.model.spell.DofusSpell

data class SpellDrag(
    val spell: DofusSpell,
    val fromSpellKey: SpellKey? = null
)