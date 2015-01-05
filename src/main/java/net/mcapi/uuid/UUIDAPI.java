package net.mcapi.uuid;

import java.util.*;

import net.mcapi.uuid.handlers.*;

import com.google.common.base.Preconditions;

/**
 * A UUID lookup API for Java and
 * Minecraft servers.
 *
 * <p>Useful to convert a username to
 * {@link UUID} and vice-versa.
 *
 * <br>By default the {@link JavaHandler}
 * is used, however other handlers can be
 * used via {@link #setHandler(UUIDHandler)}
 *
 * @author njb_said
 */
public class UUIDAPI {

    private static UUIDHandler handler;
    private final static List<UUIDHandler> handlers = new LinkedList<UUIDHandler>();
    private static ServerRegion region;

    /**
     * Get the current {@link ServerRegion} being used
     *
     * @return the server region we are using for queries
     */
    public static ServerRegion getRegion() {
        if(region == null) {
            region = ServerRegion.US;
        }
        return region;
    }

    public static void setRegion(ServerRegion region) {
        UUIDAPI.region = region;
    }

    public static List<UUIDHandler> getHandlers() {
        return handlers;
    }

    public static void setHandler(UUIDHandler newHandler) {
        Preconditions.checkNotNull(newHandler, "Handler cannot be null!");
        handler = newHandler;
    }

    /**
     * Get the current {@link UUIDHandler} being used
     * @return the {@link UUIDHandler handler} we are using to process requests
     */
    public static UUIDHandler getHandler() {
        Preconditions.checkArgument(handler != null, "Handler cannot be null!");
        return handler;
    }

    static {
        handlers.add(new JavaHandler());
        handlers.add(new BukkitHandler());
        handlers.add(new BungeeHandler());
        handler = new JavaHandler();
        region = ServerRegion.US;
    }

    /**
     * Find the UUID that matches a username.
     * <p>
     * Result is cached for one hour.
     *
     * @param username username to lookup
     * @return the {@link UUID}
     */
    public static UUID getUUID(String username) {
        return handler.getUUID(username);
    }

    /**
     * Find the UUID that matches a username.
     * <p>
     * Result is cached for one hour.
     *
     * @param username username to lookup
     * @return a string without dashes
     */
    public static String getUUIDString(String username) {
        return handler.getUUIDString(username);
    }

    /**
     * Reverse lookup a {@link UUID} to a username string.
     * <p>
     * Result is cached for one hour.
     *
     * @param uuid UUID to lookup
     * @return username or <code>NULL</code> if not found
     */
    public static String getUsername(UUID uuid) {
        return handler.getUsername(uuid);
    }

    /**
     * Reverse lookup a UUID string to a
     * username string, if the string is not
     * a valid UUID it will be converted to one.
     * <p>
     * Result is cached for one hour.
     *
     * @param uuid uuid (string) to lookup
     * @return username or <code>NULL</code> if not found
     */
    public static String getUsername(String uuid) {
        return handler.getUsername(uuid);
    }

}
