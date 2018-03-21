package org.itxtech.synapseapi.multiprotocol.protocol11.protocol;

import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.utils.Utils;
import org.itxtech.synapseapi.utils.ClassUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by CreeperFace on 5.3.2017.
 */
public class ClientboundMapItemDataPacket extends Packet11 {

    public int[] eids = new int[0];

    public long mapId;
    public int update;
    public byte scale;
    public int width;
    public int height;
    public int offsetX;
    public int offsetZ;

    public ClientboundMapItemDataPacket fromDefault(DataPacket pkk) {
        ClassUtils.requireInstance(pkk, cn.nukkit.network.protocol.ClientboundMapItemDataPacket.class);

        cn.nukkit.network.protocol.ClientboundMapItemDataPacket pk = (cn.nukkit.network.protocol.ClientboundMapItemDataPacket) pkk;
        this.eids = pk.eids;
        this.mapId = pk.mapId;
        this.update = pk.update;
        this.scale = pk.scale;
        this.width = pk.width;
        this.height = pk.height;
        this.offsetX = pk.offsetX;
        this.offsetZ = pk.offsetZ;
        this.image = pk.image;

        return this;
    }

    public cn.nukkit.network.protocol.ClientboundMapItemDataPacket toDefault() {
        cn.nukkit.network.protocol.ClientboundMapItemDataPacket pk = new cn.nukkit.network.protocol.ClientboundMapItemDataPacket();
        pk.eids = eids;
        pk.mapId = mapId;
        pk.update = update;
        pk.scale = scale;
        pk.width = width;
        pk.height = height;
        pk.offsetX = offsetX;
        pk.offsetZ = offsetZ;
        pk.image = image;

        return pk;
    }

    public MapDecorator[] decorators = new MapDecorator[0];
    public int[] colors = new int[0];
    public BufferedImage image = null;

    //update
    public static final int TEXTURE_UPDATE = 2;
    public static final int DECORATIONS_UPDATE = 4;
    public static final int ENTITIES_UPDATE = 8;

    @Override
    public byte pid() {
        return ProtocolInfo.CLIENTBOUND_MAP_ITEM_DATA_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putVarLong(mapId);

        int update = 0;
        if (eids.length > 0) {
            update |= 0x08;
        }
        if (decorators.length > 0) {
            update |= DECORATIONS_UPDATE;
        }
        if (image != null || colors.length > 0) {
            update |= TEXTURE_UPDATE;
        }
        this.putUnsignedVarInt(update);

        if ((update & 0x08) != 0) { //TODO: find out what these are for
            this.putUnsignedVarInt(eids.length);
            for (int eid : eids) {
                this.putVarInt(eid);
            }
        }
        if ((update & (TEXTURE_UPDATE | DECORATIONS_UPDATE)) != 0) {
            this.putByte(this.scale);
        }

        if ((update & DECORATIONS_UPDATE) != 0) {
            this.putUnsignedVarInt(decorators.length);

            for (MapDecorator decorator : decorators) {
                this.putVarInt((decorator.rotation & 0x0f) | (decorator.icon << 4));
                this.putByte(decorator.offsetX);
                this.putByte(decorator.offsetZ);
                this.putString(decorator.label);
                this.putLInt(decorator.color.getRGB());
            }
        }

        if ((update & TEXTURE_UPDATE) != 0) {
            this.putVarInt(width);
            this.putVarInt(height);
            this.putVarInt(offsetX);
            this.putVarInt(offsetZ);

            if (image != null) {
                for (int y = 0; y < width; y++) {
                    for (int x = 0; x < height; x++) {
                        Color color = new Color(image.getRGB(x, y), true);
                        byte red = (byte) color.getRed();
                        byte green = (byte) color.getGreen();
                        byte blue = (byte) color.getBlue();

                        putUnsignedVarInt(Utils.toRGB(red, green, blue, (byte) 0xff));
                    }
                }

                image.flush();
            } else if (colors.length > 0) {
                for (int color : colors) {
                    putUnsignedVarInt(color);
                }
            }
        }
    }

    public class MapDecorator {
        public byte rotation;
        public byte icon;
        public byte offsetX;
        public byte offsetZ;
        public String label;
        public Color color;
    }

    public static Class<? extends DataPacket> getDefaultPacket() {
        return cn.nukkit.network.protocol.ClientboundMapItemDataPacket.class;
    }
}
