package fr.lewon.dofus.bot.scripts

import fr.lewon.dofus.bot.core.logs.LogItem
import fr.lewon.dofus.bot.model.characters.parameters.ParameterValues
import fr.lewon.dofus.bot.scripts.parameters.DofusBotParameter
import fr.lewon.dofus.bot.util.network.info.GameInfo

abstract class DofusBotScriptBuilder(val name: String, val isDev: Boolean = false) {

    abstract fun getParameters(): List<DofusBotParameter<*>>

    abstract fun getDefaultStats(): List<DofusBotScriptStat>

    abstract fun getDescription(): String

    fun buildScript(): DofusBotScript {
        return object : DofusBotScript() {
            override fun execute(
                logItem: LogItem,
                gameInfo: GameInfo,
                parameterValues: ParameterValues,
                statValues: HashMap<DofusBotScriptStat, String>,
            ) {
                statValues.putAll(getDefaultStats().associateWith { it.defaultValue })
                doExecuteScript(logItem, gameInfo, parameterValues, statValues)
            }
        }
    }

    protected abstract fun doExecuteScript(
        logItem: LogItem,
        gameInfo: GameInfo,
        parameterValues: ParameterValues,
        statValues: HashMap<DofusBotScriptStat, String>,
    )

}