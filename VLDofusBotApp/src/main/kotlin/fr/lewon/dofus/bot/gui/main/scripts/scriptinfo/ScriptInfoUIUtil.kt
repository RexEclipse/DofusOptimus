package fr.lewon.dofus.bot.gui.main.scripts.scriptinfo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import fr.lewon.dofus.bot.gui.ComposeUIUtil
import fr.lewon.dofus.bot.gui.main.characters.CharactersUIUtil
import fr.lewon.dofus.bot.util.FormatUtil

object ScriptInfoUIUtil : ComposeUIUtil() {

    private val scriptInfoUIStateByCharacterName = HashMap<String, MutableState<ScriptInfoUIState>>()

    fun getScriptInfoUIState(characterName: String): MutableState<ScriptInfoUIState> {
        return scriptInfoUIStateByCharacterName.computeIfAbsent(characterName) { mutableStateOf(ScriptInfoUIState()) }
    }

    fun removeScriptInfoUIState(characterName: String) {
        scriptInfoUIStateByCharacterName.remove(characterName)
    }

    fun updateState(characterName: String) {
        val runningScript = CharactersUIUtil.getCharacterUIState(characterName).value.runningScript
        val suffix = if (runningScript != null) {
            val durationStr = FormatUtil.durationToStr(System.currentTimeMillis() - runningScript.startTime)
            "${runningScript.scriptBuilder.name} - $durationStr"
        } else "Ready"
        val text = "$characterName : $suffix"
        val scriptInfoUIState = getScriptInfoUIState(characterName)
        scriptInfoUIState.value = scriptInfoUIState.value.copy(
            runningScript = runningScript,
            text = text,
        )
    }

}