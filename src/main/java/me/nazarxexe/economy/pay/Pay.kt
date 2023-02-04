package me.nazarxexe.economy.pay

import cn.nukkit.plugin.PluginBase
import cn.nukkit.utils.TextFormat
import me.nazarxexe.economy.pay.commands.PayCommand
import me.nazarxexe.economy.pay.listener.PayListener
import me.nazarxexe.survival.core.tools.TerminalComponent
import me.nazarxexe.survival.core.tools.TextComponent
import me.nazarxexe.survival.econ.EconomyAPI

class Pay : PluginBase() {

    var api: EconomyAPI? = null


    override fun onEnable() {

        api = this.server.serviceManager.getProvider(/* p0 = */ EconomyAPI::class.java).provider

        if (api == null) {
            this.logger.error("PLEASE INCLUDE CORE AND ECO API's!")
        }

        TerminalComponent(
            this.logger, TextComponent()
                .combine(TextFormat.GREEN)
                .combine("CONNECTED TO ECONOMY API!")
                .combine("MADE BY NAZARBEKALD")
        ).info()

        this.server.pluginManager.registerEvents(PayListener(this), this)
        this.server.commandMap.register("pay", PayCommand(this))

    }
}