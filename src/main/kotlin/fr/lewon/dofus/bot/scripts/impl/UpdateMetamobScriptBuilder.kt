package fr.lewon.dofus.bot.scripts.impl

import fr.lewon.dofus.bot.core.logs.LogItem
import fr.lewon.dofus.bot.core.ui.managers.DofusUIElement
import fr.lewon.dofus.bot.gui2.main.metamob.util.MetamobMonstersUpdater
import fr.lewon.dofus.bot.gui2.main.metamob.util.MetamobRequestProcessor
import fr.lewon.dofus.bot.model.characters.VldbScriptValues
import fr.lewon.dofus.bot.scripts.DofusBotScriptBuilder
import fr.lewon.dofus.bot.scripts.DofusBotScriptStat
import fr.lewon.dofus.bot.scripts.parameters.DofusBotParameter
import fr.lewon.dofus.bot.scripts.tasks.impl.transport.AccessHavenBagChestTask
import fr.lewon.dofus.bot.sniffer.model.messages.storage.StorageInventoryContentMessage
import fr.lewon.dofus.bot.util.io.MouseUtil
import fr.lewon.dofus.bot.util.io.WaitUtil
import fr.lewon.dofus.bot.util.network.info.GameInfo
import fr.lewon.dofus.bot.util.ui.UiUtil

object UpdateMetamobScriptBuilder : DofusBotScriptBuilder("Update Metamob") {

    override fun getParameters(): List<DofusBotParameter> {
        return listOf()
    }

    override fun getStats(): List<DofusBotScriptStat> {
        return emptyList()
    }

    override fun getDescription(): String {
        return "Updates your Metamob information using the archmonster stones in your haven bag chest"
    }

    override fun doExecuteScript(logItem: LogItem, gameInfo: GameInfo, scriptValues: VldbScriptValues) {
        if (!AccessHavenBagChestTask().run(logItem, gameInfo)) {
            error("Couldn't access haven bag chest")
        }
        val chestContent = gameInfo.eventStore.getLastEvent(StorageInventoryContentMessage::class.java)
            ?: error("A message should have arrived containing chest content")
        updateMetamobMonsters(chestContent, gameInfo, logItem)
        val closeButtonBounds = UiUtil.getContainerBounds(DofusUIElement.STORAGE, "btn_close")
        MouseUtil.leftClick(gameInfo, closeButtonBounds.getCenter())
        if (!WaitUtil.waitUntil({ !UiUtil.isWindowOpenedUsingCloseButton(gameInfo, DofusUIElement.STORAGE) })) {
            error("Couldn't close chest")
        }
    }

    private fun updateMetamobMonsters(
        chestContent: StorageInventoryContentMessage,
        gameInfo: GameInfo,
        logItem: LogItem
    ) {
        if (!MetamobRequestProcessor.checkParameters()) {
            error("Failed to access Metamob. It might be down but it is more likely that your settings are wrong")
        }
        val metamobUpdateLogItem = gameInfo.logger.addSubLog("Computing owned monsters ... ", logItem)
        MetamobMonstersUpdater.cleanAndUpdateMonsters(chestContent.objectItems)
        gameInfo.logger.closeLog("OK", metamobUpdateLogItem)
    }
}