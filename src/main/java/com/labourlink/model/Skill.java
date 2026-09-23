package com.labourlink.model;

public enum Skill {
    MASON("Mason", "🧱"),
    ELECTRICIAN("Electrician", "⚡"),
    PAINTER("Painter", "🎨"),
    LABOURER("Labourer", "💪"),
    PLUMBER("Plumber", "🔧"),
    CARPENTER("Carpenter", "🪚"),
    WELDER("Welder", "🔥"),
    TILE_FITTER("Tile Fitter", "🔲"),
    ROD_BINDER("Rod Binder", "🏗️"),
    HELPER("Helper", "🤝");

    private final String displayName;
    private final String icon;

    Skill(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }
}
