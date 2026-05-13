package co.edu.uco.ucoparking.crossscutting.helper;

import java.util.UUID;

public final class UUIDHelper {

    private static final UUIDHelper INSTANCE = new UUIDHelper();
    private static final String UUID_DEFAULT_AS_STRING = "00000000-0000-0000-0000-000000000000";

    private UUIDHelper() {
    }

    public static UUIDHelper getUUIDHelper() {
        return INSTANCE;
    }

    public java.util.UUID getDefault() {
        return getFromString(UUID_DEFAULT_AS_STRING);
    }

    public  UUID getDefault(final UUID value) {
        return ObjectHelper.getDefault(value,getDefault());
    }

    public  UUID getFromString(final String uuidAsString) {
        return TextHelper.isNullOrWhiteSpace(uuidAsString) ? getDefault() : UUID.fromString(uuidAsString);
    }

    public UUID genetareNewUUID(){
        return UUID.randomUUID();
    }

    public boolean isDefaultUUID(final UUID value) {
        return getDefault().equals(getDefault(value));
    }
}