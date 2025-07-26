package net.leyn.gofaster.integration;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.leyn.gofaster.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClothConfig {
    protected static Screen getConfigScreen(Screen parent) {
        Config config = Config.CURRENT;

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(MinecraftClient.getInstance().currentScreen)
                .setTitle(Text.literal("Go Faster Config"));

        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startColorField(Text.literal("Text Color"), config.textColor)
                .setDefaultValue(0xAAAAAA)
                        .setSaveConsumer(value -> config.textColor = value)
                .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Horizontal Offset"), config.xOffset)
                .setDefaultValue(0)
                .setSaveConsumer(value -> config.xOffset = value)
                .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Vertical Offset"), config.yOffset)
                .setDefaultValue(0)
                .setSaveConsumer(value -> config.yOffset = value)
                .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Refresh Delay"), config.updateRate)
                .setDefaultValue(10)
                .setTooltip(Text.literal("Shown values will be updated each how many ticks"))
                .setMin(1)
                .setSaveConsumer(value -> config.updateRate = value)
                .build());

        SubCategoryBuilder fallHeight = entryBuilder.startSubCategory(Text.literal("Fall Distance"));

        fallHeight.add(entryBuilder.startBooleanToggle(Text.literal("Show"), config.showFallHeight)
                .setDefaultValue(true)
                .setSaveConsumer(value -> config.showFallHeight = value)
                .build());

        fallHeight.add(entryBuilder.startIntField(Text.literal("Minimal Value"), config.thresholdFD)
                .setDefaultValue(5)
                .setTooltip(Text.literal("Any value below this will not be shown"))
                .setMin(1)
                .setSaveConsumer(value -> config.thresholdFD = value)
                .build());

        fallHeight.add(entryBuilder.startBooleanToggle(Text.literal("Check For Mace"), config.checkForMace)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Show fall distance only when mace is in inventory"))
                .setSaveConsumer(value -> config.checkForMace = value)
                .build());

        general.addEntry(fallHeight.setExpanded(true).build());

        SubCategoryBuilder speed = entryBuilder.startSubCategory(Text.literal("Speed"));

        speed.add(entryBuilder.startBooleanToggle(Text.literal("Show"), config.showSpeed)
                .setDefaultValue(true)
                .setSaveConsumer(value -> config.showSpeed = value)
                .build());

        speed.add(entryBuilder.startIntField(Text.literal("Minimal Value"), config.thresholdS)
                .setDefaultValue(20)
                .setTooltip(Text.literal("Any value equal or more than this will be always shown"))
                .setMin(1)
                .setSaveConsumer(value -> config.thresholdS = value)
                .build());

        speed.add(entryBuilder.startBooleanToggle(Text.literal("Show In Flight"), config.alwaysShowFlying)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Show fall distance only when mace is in inventory"))
                .setSaveConsumer(value -> config.alwaysShowFlying = value)
                .build());

        general.addEntry(speed.setExpanded(true).build());

        return builder.build();
    }
}
