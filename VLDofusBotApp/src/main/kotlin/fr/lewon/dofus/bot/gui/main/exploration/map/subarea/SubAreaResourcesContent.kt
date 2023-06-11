package fr.lewon.dofus.bot.gui.main.exploration.map.subarea

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.lewon.dofus.bot.core.model.maps.DofusSubArea
import fr.lewon.dofus.bot.gui.custom.CommonText
import fr.lewon.dofus.bot.gui.custom.HorizontalSeparator
import fr.lewon.dofus.bot.gui.custom.VerticalGrid
import fr.lewon.dofus.bot.gui.custom.darkGrayBoxStyle
import fr.lewon.dofus.bot.gui.main.TooltipTarget
import fr.lewon.dofus.bot.gui.main.exploration.ExplorationUIUtil
import fr.lewon.dofus.bot.gui.util.AppColors
import fr.lewon.dofus.bot.gui.util.toPainter
import fr.lewon.dofus.bot.model.jobs.HarvestJobs
import fr.lewon.dofus.bot.scripts.impl.ExploreAreaScriptBuilder
import fr.lewon.dofus.bot.util.filemanagers.impl.HarvestableSetsManager

@Composable
fun SubAreaResourcesContent(subArea: DofusSubArea) {
    Box(Modifier.fillMaxSize().darkGrayBoxStyle()) {
        val state = rememberScrollState()
        Column(Modifier.fillMaxSize().padding(5.dp).padding(end = 8.dp).verticalScroll(state)) {
            if (subArea.harvestables.isEmpty()) {
                CommonText(
                    "No harvestable resource in this sub area",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp),
                )
            } else {
                HarvestJobs.values().forEach { ResourcesListContent(it, subArea) }
            }
        }
        VerticalScrollbar(
            modifier = Modifier.fillMaxHeight().width(8.dp).align(Alignment.CenterEnd)
                .background(AppColors.backgroundColor),
            adapter = rememberScrollbarAdapter(state),
        )
    }
}

@Composable
private fun ResourcesListContent(job: HarvestJobs, subArea: DofusSubArea) {
    val toHarvestItemIds =
        ExplorationUIUtil.explorerUIState.value.explorationParameterValuesByParameter[ExploreAreaScriptBuilder.harvestParameter]
            ?.let { HarvestableSetsManager.getItemsToHarvest(it) }
            ?: emptyList()
    val harvestables = job.items.filter { subArea.harvestables.contains(it) }.distinct()
    if (harvestables.isNotEmpty()) {
        HorizontalSeparator(job.jobName, Modifier.padding(vertical = 10.dp))
        VerticalGrid(
            columns = 5,
            modifier = Modifier.padding(end = 8.dp),
            itemCount = harvestables.size
        ) {
            val harvestable = harvestables[it]
            val color = if (toHarvestItemIds.contains(harvestable.id)) {
                AppColors.primaryLightColor
            } else {
                Color.Red
            }
            TooltipTarget(
                text = "${harvestable.name} (${harvestable.level})",
                modifier = Modifier.padding(3.dp).border(BorderStroke(1.dp, color)).padding(3.dp)
            ) {
                Surface(color = color.copy(alpha = 0.5f)) {
                    Image(harvestable.cachedIcon.toPainter(), "")
                }
            }
        }
    }
}