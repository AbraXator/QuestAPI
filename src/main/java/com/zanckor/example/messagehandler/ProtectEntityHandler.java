package com.zanckor.example.messagehandler;

import com.google.gson.Gson;
import com.zanckor.api.questregister.AbstractQuest;
import com.zanckor.mod.util.Timer;
import com.zanckor.mod.PlayerQuest;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.zanckor.mod.QuestApiMain.serverDirectory;

public class ProtectEntityHandler extends AbstractQuest {

    public void handler(Player player, Gson gson, File file, PlayerQuest playerQuest) throws IOException {

        UUID entityUUID = UUID.fromString(playerQuest.getQuest_target().get(0));
        Entity entity = player.getServer().overworld().getEntity(entityUUID);

        FileWriter protectEntityWriter = new FileWriter(file);

        if (Timer.canUseWithCooldown(player.getUUID(), "id_" + playerQuest.getId(), playerQuest.getTimeLimitInSeconds()) && entity.isAlive()) {
            gson.toJson(playerQuest.setProgress(playerQuest, 0, 1), protectEntityWriter);
        } else {
            playerQuest.setCompleted(true);

            gson.toJson(playerQuest, protectEntityWriter);
        }

        protectEntityWriter.flush();
        protectEntityWriter.close();


        if (!entity.isAlive()) {
            Path questapi = Paths.get(serverDirectory.toString(), "quest-api");
            Path playerdata = Paths.get(questapi.toString(), "player-data");
            Path userFolder = Paths.get(playerdata.toString(), player.getUUID().toString());
            Path uncompletedQuest = Paths.get(userFolder.toString(), "uncompleted-quests");

            Files.move(file.toPath(), Paths.get(uncompletedQuest.toString(), file.getName()));

        }

        CompleteQuest.completeQuest(player, gson, file);
    }
}