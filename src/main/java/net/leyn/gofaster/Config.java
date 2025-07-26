package net.leyn.gofaster;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Config {
    // Config values
    public int textColor;
    public int xOffset;
    public int yOffset;
    public int updateRate;
    public boolean showFallHeight;
    public int thresholdFD;
    public boolean checkForMace;
    public boolean showSpeed;
    public int thresholdS;
    public boolean alwaysShowFlying;

    Config(int TC, int XOff, int YOff, int URate, boolean showFH, int ThrFD, boolean checkMace, boolean showS, int ThrS, boolean showFly) {
        this.textColor = TC;
        this.xOffset = XOff;
        this.yOffset = YOff;
        this.updateRate = URate;
        this.showFallHeight = showFH;
        this.thresholdFD = ThrFD;
        this.checkForMace = checkMace;
        this.showSpeed = showS;
        this.thresholdS = ThrS;
        this.alwaysShowFlying = showFly;
    }

    // Assign defaults
    public static Config CURRENT = new Config(
            0xAAAAAA, 0, 0, 10, true, 5, true, true, 20, true
    );

    // Half stolen
    private static final Path configDir = FabricLoader.getInstance().getConfigDir();
    private static final File configFile = configDir.resolve("go-faster.json").toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(CURRENT, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Config data = GSON.fromJson(reader, Config.class);
                CURRENT.textColor = data.textColor;
                CURRENT.xOffset = data.xOffset;
                CURRENT.updateRate = data.updateRate;
                CURRENT.showFallHeight = data.showFallHeight;
                CURRENT.thresholdFD = data.thresholdFD;
                CURRENT.checkForMace = data.checkForMace;
                CURRENT.showSpeed = data.showSpeed;
                CURRENT.thresholdS = data.thresholdS;
                CURRENT.alwaysShowFlying = data.alwaysShowFlying;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
