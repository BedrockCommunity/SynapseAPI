package org.itxtech.synapseapi.multiprotocol;

/**
 * @author CreeperFace
 */

public enum ProtocolGroup {
    PROTOCOL_11(113, 113),
    PROTOCOL_12(134, 160),
    PROTOCOL_1210(200, 200);

    private final int minProtocol;
    private final int maxProtocol;

    ProtocolGroup(int minProtocol, int maxProtocol) {
        this.minProtocol = minProtocol;
        this.maxProtocol = maxProtocol;
    }

    public static ProtocolGroup from(int protocol) {
        for(ProtocolGroup group : values()) {
            if(protocol >= group.minProtocol && protocol <= group.maxProtocol) {
                return group;
            }
        }

        return values()[values().length - 1];
    }
}