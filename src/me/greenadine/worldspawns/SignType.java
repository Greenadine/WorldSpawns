package me.greenadine.worldspawns;

public enum SignType {

    SPAWN("spawn", "Spawn"), FSPAWN("fspawn", "Fspawn"), HUB("hub", "Hub"), FHUB("fhub", "Fhub");

    private String internalName;
    private String messageName;

    SignType(String internalName, String messageName) {
        this.internalName = internalName;
        this.messageName = messageName;
    }

    public String toString() {
        return messageName;
    }

    public SignType getType() {
        return this;
    }

    public String getInternalName() {
        return internalName;
    }
}
