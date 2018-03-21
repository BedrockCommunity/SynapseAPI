package org.itxtech.synapseapi.multiprotocol.protocol11.protocol;

import cn.nukkit.network.protocol.DataPacket;
import org.itxtech.synapseapi.utils.ClassUtils;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class UpdateBlockPacket extends Packet11 {
    public static final byte NETWORK_ID = ProtocolInfo.UPDATE_BLOCK_PACKET;

    public static final int FLAG_NONE = 0b0000;
    public static final int FLAG_NEIGHBORS = 0b0001;
    public static final int FLAG_NETWORK = 0b0010;
    public static final int FLAG_NOGRAPHIC = 0b0100;
    public static final int FLAG_PRIORITY = 0b1000;

    public static final int FLAG_ALL = (FLAG_NEIGHBORS | FLAG_NETWORK);
    public static final int FLAG_ALL_PRIORITY = (FLAG_ALL | FLAG_PRIORITY);

    public int x;
    public int z;
    public int y;
    public int blockId;
    public int blockData;
    public int flags;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putBlockVector3(x, y, z);
        this.putUnsignedVarInt(blockId);
        this.putUnsignedVarInt((0xb << 4) | blockData & 0xf);
    }

    public static class Entry {
        public final int x;
        public final int z;
        public final int y;
        public final int blockId;
        public final int blockData;
        public final int flags;

        public Entry(int x, int z, int y, int blockId, int blockData, int flags) {
            this.x = x;
            this.z = z;
            this.y = y;
            this.blockId = blockId;
            this.blockData = blockData;
            this.flags = flags;
        }
    }

    @Override
    public DataPacket fromDefault(DataPacket pk) {
        ClassUtils.requireInstance(pk, cn.nukkit.network.protocol.UpdateBlockPacket.class);

        cn.nukkit.network.protocol.UpdateBlockPacket packet = (cn.nukkit.network.protocol.UpdateBlockPacket) pk;

        this.blockData = packet.blockData;
        this.blockId = packet.blockId;
        this.flags = packet.flags;
        this.x = packet.x;
        this.y = packet.y;
        this.z = packet.z;

        return this;
    }

    @Override
    public DataPacket toDefault() {
        cn.nukkit.network.protocol.UpdateBlockPacket pk = new cn.nukkit.network.protocol.UpdateBlockPacket();
        pk.blockData = this.blockData;
        pk.flags = this.flags;
        pk.blockId = this.blockId;
        pk.x = this.x;
        pk.y = this.y;
        pk.z = this.z;

        return pk;
    }

    public static Class<? extends DataPacket> getDefaultPacket() {
        return cn.nukkit.network.protocol.UpdateBlockPacket.class;
    }
}
