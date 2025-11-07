package foxiwhitee.FoxLib.api.orientable;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public interface IOrientableBlock {
    IOrientable getOrientable(IBlockAccess var1, int var2, int var3, int var4);

    static ForgeDirection rotateAround(final ForgeDirection forward, final ForgeDirection axis) {
        if (axis == ForgeDirection.UNKNOWN || forward == ForgeDirection.UNKNOWN) {
            return forward;
        }

        switch (forward) {
            case DOWN:
                switch (axis) {
                    case DOWN, UP -> {
                        return forward;
                    }
                    case NORTH -> {
                        return ForgeDirection.EAST;
                    }
                    case SOUTH -> {
                        return ForgeDirection.WEST;
                    }
                    case EAST -> {
                        return ForgeDirection.NORTH;
                    }
                    case WEST -> {
                        return ForgeDirection.SOUTH;
                    }
                    default -> {}
                }
                break;
            case UP:
                switch (axis) {
                    case NORTH -> {
                        return ForgeDirection.WEST;
                    }
                    case SOUTH -> {
                        return ForgeDirection.EAST;
                    }
                    case EAST -> {
                        return ForgeDirection.SOUTH;
                    }
                    case WEST -> {
                        return ForgeDirection.NORTH;
                    }
                    default -> {}
                }
                break;
            case NORTH:
                switch (axis) {
                    case UP -> {
                        return ForgeDirection.WEST;
                    }
                    case DOWN -> {
                        return ForgeDirection.EAST;
                    }
                    case EAST -> {
                        return ForgeDirection.UP;
                    }
                    case WEST -> {
                        return ForgeDirection.DOWN;
                    }
                    default -> {}
                }
                break;
            case SOUTH:
                switch (axis) {
                    case UP -> {
                        return ForgeDirection.EAST;
                    }
                    case DOWN -> {
                        return ForgeDirection.WEST;
                    }
                    case EAST -> {
                        return ForgeDirection.DOWN;
                    }
                    case WEST -> {
                        return ForgeDirection.UP;
                    }
                    default -> {}
                }
                break;
            case EAST:
                switch (axis) {
                    case UP -> {
                        return ForgeDirection.NORTH;
                    }
                    case DOWN -> {
                        return ForgeDirection.SOUTH;
                    }
                    case NORTH -> {
                        return ForgeDirection.UP;
                    }
                    case SOUTH -> {
                        return ForgeDirection.DOWN;
                    }
                    default -> {}
                }
            case WEST:
                switch (axis) {
                    case UP -> {
                        return ForgeDirection.SOUTH;
                    }
                    case DOWN -> {
                        return ForgeDirection.NORTH;
                    }
                    case NORTH -> {
                        return ForgeDirection.DOWN;
                    }
                    case SOUTH -> {
                        return ForgeDirection.UP;
                    }
                    default -> {}
                }
            default:
                break;
        }
        return forward;
    }
}

