package com.yue.czcontrol.object;

public class Leave {
    /**
     * Leave id.
     */
    private final String id;
    /**
     * Leave Member name.
     */
    private final String name;
    /**
     * Leave Time.
     */
    private final String time;
    /**
     * Handler.
     */
    private final String handler;
    /**
     * Reason.
     */
    private final String reason;

    /**
     * Leave Object.
     * @param id id
     * @param name name
     * @param time time
     * @param reason reason
     * @param handler handler
     */
    public Leave(final String id, final String name,
                 final String time, final String reason,
                 final String handler) {
        this.id = id;
        this.name = name;
        this.handler = handler;
        this.time = time;
        this.reason = reason;
    }

    /**
     * get id.
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * get name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get time.
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * get handler.
     * @return handler
     */
    public String getHandler() {
        return handler;
    }

    /**
     * get reason.
     * @return reason
     */
    public String getReason() {
        return reason;
    }
}
