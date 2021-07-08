package dev.vatuu.tesseract;

import dev.vatuu.tesseract.cmd.CreateTestWorldCommand;
import dev.vatuu.tesseract.cmd.RegenerateWorldCommand;
import dev.vatuu.tesseract.lil.LilTesseractBlockEntity;
import dev.vatuu.tesseract.network.NetworkHandler;
import dev.vatuu.tesseract.registry.TesseractManagement;
import dev.vatuu.tesseract.registry.TesseractRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

public class Tesseract implements ModInitializer {

    public static final String MOD_ID = "tesseract";

    public static Tesseract INSTANCE;

    public static BlockEntityType<LilTesseractBlockEntity> LIL_TESSERACT_BE;

    private NetworkHandler networkHandler;

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        INSTANCE = this;

        if(FabricLoader.getInstance().isDevelopmentEnvironment())
            CommandRegistrationCallback.EVENT.register((dis, b) ->  {
                CreateTestWorldCommand.register(dis);
                RegenerateWorldCommand.register(dis);
            });

        ServerLifecycleEvents.SERVER_STARTED.register(t -> {
            TesseractRegistry.setInstance(t.getNetworkIo().getServer());
            TesseractManagement.setInstance(t.getNetworkIo().getServer());
        });

        this.networkHandler = new NetworkHandler();

        LIL_TESSERACT_BE = LilTesseractBlockEntity.getBlockEntityType();
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
