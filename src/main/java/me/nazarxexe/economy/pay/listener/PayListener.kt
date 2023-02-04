package me.nazarxexe.economy.pay.listener

import cn.nukkit.Player
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerFormRespondedEvent
import cn.nukkit.form.window.FormWindowCustom
import cn.nukkit.form.window.FormWindowSimple
import cn.nukkit.utils.TextFormat
import me.nazarxexe.economy.pay.Pay
import me.nazarxexe.survival.core.tools.TextComponent
import me.nazarxexe.survival.econ.EconomyAPI

class PayListener(plugin: Pay) : Listener {

    val plugin: Pay
    val api : EconomyAPI

    init{
        this.plugin = plugin
        this.api = plugin.api!!
    }


    @EventHandler
    fun onRespond(e: PlayerFormRespondedEvent) {
        if (e.window.wasClosed()) return
        if (e.window !is FormWindowCustom) return

        var panel: FormWindowCustom = e.window as FormWindowCustom

        if (panel.title != TextComponent()
                .combine(TextFormat.BLUE)
                .combine("PAY")
                .text
        ) return

        var payee_: Player? = plugin.server.getPlayer(panel.response.getInputResponse(0))
        var amount: Long = try{
            panel.response.getInputResponse(1).toLong()
        } catch (err : NumberFormatException){ -2020L }

        if(payee_?.name == e.player.name){
            err("You can't pay yourself!", e.player)
            return
        }

        if (amount == -2020L){
            err("Failed to make <<amount>> input as number!", e.player)
            return
        }
        if (amount < 0) {
            err("Send more than 0!", e.player)
            return
        }
        if (api.get(e.player.uniqueId) < amount){
            err("Not enough balance", e.player)
            return
        }
        if (payee_ == null){
            err("Player is offline!", e.player)
            return
        }

        var payer = api.decrement(e.player.uniqueId, amount)

        if (!(payer)){
            err("System failed transactions please try again!", e.player)
            return
        }
        var payee = api.add(payee_.uniqueId, amount)
        if (!(payee)){
            err("System failed transactions please try again!", e.player)
            return
        }

        e.player.sendMessage(TextComponent()
            .combine(TextFormat.GREEN)
            .combine("Sent ")
            .combine(amount.toString())
            .combine(" to ")
            .combine(payee_.name).text)


    }

    private fun err(msg: String, p: Player){
        var m = FormWindowSimple(TextComponent()
            .combine(TextFormat.RED)
            .combine("PAY ERROR!")
            .text, msg)
        p.showFormWindow(m)
    }

}