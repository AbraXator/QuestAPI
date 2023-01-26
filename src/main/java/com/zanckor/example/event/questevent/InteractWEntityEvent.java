package com.zanckor.example.event.questevent;

import com.zanckor.mod.QuestApiMain;
import com.zanckor.mod.network.SendQuestPacket;
import com.zanckor.mod.network.message.quest.QuestDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.zanckor.api.quest.enumquest.EnumQuestType.INTERACT_ENTITY;

@Mod.EventBusSubscriber(modid = QuestApiMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InteractWEntityEvent {


    //TODO Fixear, se ejecuta demasiadas veces

    @SubscribeEvent
    public static void interactWithNPC(PlayerInteractEvent.EntityInteract e) {
        if (e.getHand() == InteractionHand.OFF_HAND) return;

        SendQuestPacket.TO_SERVER(new QuestDataPacket(INTERACT_ENTITY));
    }
}