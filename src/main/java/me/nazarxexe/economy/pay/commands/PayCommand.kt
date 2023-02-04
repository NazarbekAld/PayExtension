package me.nazarxexe.economy.pay.commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.form.element.ElementInput
import cn.nukkit.form.window.FormWindowCustom
import cn.nukkit.utils.TextFormat
import me.nazarxexe.economy.pay.Pay
import me.nazarxexe.survival.core.tools.TextComponent


class PayCommand(plugin: Pay) : Command("pay", "Make transaction with player!") {

    private val plugin: Pay;

    init {
        this.plugin = plugin;
    }

    override fun execute(sender: CommandSender?, commandLabel: String?, args: Array<out String>?): Boolean {

        if (sender !is Player) return true

        var panel : FormWindowCustom = FormWindowCustom(
            TextComponent()
            .combine(TextFormat.BLUE)
            .combine("PAY")
            .text)

        panel.addElement(ElementInput("Enter player's name."))
        panel.addElement(ElementInput("Enter amount."))

        var p = sender as Player
        p.showFormWindow(panel)
        return true
    }


}