package com.perpetualmaze.generator;

import java.io.ByteArrayOutputStream;
import java.util.BitSet;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Walls {
    private final short width;
    private final short height;
    private final BitSet wallBits;

    public Walls(int width, int height) {
        this.width = (short) width;
        this.height = (short) height;
        // every cell has a unique top wall  (width * height number of walls)
        // every cell has a unique left wall  (width * height number of walls)
        // only the bottom row has bottom walls  (width number of walls)
        // only the right-most row has right walls  (height number of walls)
        wallBits = new BitSet(2 * (width * height) + width + height);
    }

    public Walls(int width, int height, BitSet wallBits) {
        this.width = (short) width;
        this.height = (short) height;
        this.wallBits = wallBits;
    }

    public short getWidth() {
        return width;
    }

    public short getHeight() {
        return height;
    }

    public BitSet getWallBits() {
        return wallBits;
    }

    private int getTopWallBitPosition(Coord coord) {
        return (coord.getRow() * width) + coord.getCol();
    }

    public boolean hasTopWall(Coord coord) {
        return wallBits.get(getTopWallBitPosition(coord));
    }

    public void setTopWall(Coord coord) {
        wallBits.set(getTopWallBitPosition(coord));
    }

    public void clearTopWall(Coord coord) {
        wallBits.clear(getTopWallBitPosition(coord));
    }

    private int getBottomWallBitPosition(Coord coord) {
        // only the bottom row has a bottom wall, otherwise we get the top wall
        // bit position for the coordinate immediately below the one specified.
        if (coord.getRow() == height - 1) {
            // the bottom row bottom wall
            return
                    2 * (width * height) + // skip past the top walls and left walls
                            coord.getCol();
        } else {
            // updating the top wall for the coordinate immediately below.
            return getTopWallBitPosition(new Coord(coord.getRow() + 1, coord.getCol()));
        }
    }

    public boolean hasBottomWall(Coord coord) {
        return wallBits.get(getBottomWallBitPosition(coord));
    }

    public void setBottomWall(Coord coord) {
        wallBits.set(getBottomWallBitPosition(coord));
    }

    public void clearBottomWall(Coord coord) {
        wallBits.clear(getBottomWallBitPosition(coord));
    }

    private int getLeftWallBitPosition(Coord coord) {
        return (width * height) + // skip past the top walls
                (coord.getRow() * width) + coord.getCol();
    }

    public boolean hasLeftWall(Coord coord) {
        return wallBits.get(getLeftWallBitPosition(coord));
    }

    public void setLeftWall(Coord coord) {
        wallBits.set(getLeftWallBitPosition(coord));
    }

    public void clearLeftWall(Coord coord) {
        wallBits.clear(getLeftWallBitPosition(coord));
    }

    private int getRightWallBitPosition(Coord coord) {
        // only the right-most column has a right wall, otherwise we return the bit position of the left wall
        // for the coordinate immediately to the right of the one specified.
        if (coord.getCol() == width - 1) {
            // retrieve the right-most row right wall
            return 2 * (width * height) + width + // skip past the top walls, left walls, and bottom walls
                    coord.getRow();
        } else {
            // return the left wall for the coordinate immediately to the right.
            return getLeftWallBitPosition(new Coord(coord.getRow(), coord.getCol() + 1));
        }
    }

    public boolean hasRightWall(Coord coord) {
        return wallBits.get(getRightWallBitPosition(coord));
    }

    public void setRightWall(Coord coord) {
        wallBits.set(getRightWallBitPosition(coord));
    }

    public void clearRightWall(Coord coord) {
        wallBits.clear(getRightWallBitPosition(coord));
    }

    public void setAllWalls(Coord coord) {
        setTopWall(coord);
        setBottomWall(coord);
        setLeftWall(coord);
        setRightWall(coord);
    }

    public void clearAllWalls(Coord coord) {
        clearTopWall(coord);
        clearBottomWall(coord);
        clearLeftWall(coord);
        clearRightWall(coord);
    }

    public String serialize() {
        Deflater deflater = new Deflater();
        deflater.setInput(wallBits.toByteArray());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        byte[] compressed = outputStream.toByteArray();

        String sizes = String.format("%d:%d:", width, height);
        StringBuilder serialized = new StringBuilder(sizes.length() + compressed.length * 2);
        serialized.append(sizes);
        for (byte b : compressed) {
            serialized.append(String.format("%02x", b));
        }
        return serialized.toString();
    }

    public static Walls deserialize(String serialized) {
        String[] parts = serialized.split(":");
        assert (parts.length == 3);
        int width = Integer.parseInt(parts[0]);
        int height = Integer.parseInt(parts[1]);
        int len = parts[2].length();
        byte[] compressed = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            compressed[i / 2] = (byte) ((Character.digit(parts[2].charAt(i), 16) << 4)
                    + Character.digit(parts[2].charAt(i + 1), 16));
        }
        Inflater inflater = new Inflater();
        inflater.setInput(compressed);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
        } catch (DataFormatException badData) {
            throw new RuntimeException("Unexpected bad data: " + badData.getMessage(), badData);
        }
        Walls walls = new Walls(width, height);
        walls.getWallBits().or(BitSet.valueOf(outputStream.toByteArray()));
        return walls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Walls)) return false;
        Walls walls = (Walls) o;
        return getWidth() == walls.getWidth() &&
                getHeight() == walls.getHeight() &&
                Objects.equals(wallBits, walls.wallBits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWidth(), getHeight(), wallBits);
    }

    @Override
    public String toString() {
        return serialize();
    }
}
