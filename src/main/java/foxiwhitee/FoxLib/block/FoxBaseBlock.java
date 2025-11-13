package foxiwhitee.FoxLib.block;

import appeng.tile.AEBaseTile;
import foxiwhitee.FoxLib.api.orientable.IOrientable;
import foxiwhitee.FoxLib.api.orientable.IOrientableBlock;
import foxiwhitee.FoxLib.integration.crafttweaker.DynamicIntegration;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.tile.FoxBaseTile;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FoxBaseBlock extends Block implements IOrientableBlock, ITileEntityProvider {
    protected final String name;
    private Class<? extends TileEntity> tileEntityType;

    public FoxBaseBlock(String modID, String name) {
        super(Material.rock);
        this.name = name;
        this.setBlockName(name);
        this.setBlockTextureName(modID + ":" + name);
        this.lightOpacity = 1;
    }

    public final boolean rotateBlock(final World w, final int x, final int y, final int z, final ForgeDirection axis) {
        final IOrientable rotatable = this.getOrientable(w, x, y, z);

        if (rotatable != null && rotatable.canBeRotated()) {
            ForgeDirection forward = rotatable.getForward();

            for (int rs = 0; rs < 4; rs++) {
                forward = IOrientableBlock.rotateAround(forward, axis);

                if (this.isValidOrientation(w, x, y, z, forward)) {
                    rotatable.setOrientation(forward);
                    return true;
                }
            }
        }

        return super.rotateBlock(w, x, y, z, axis);
    }

    public boolean isValidOrientation(final World w, final int x, final int y, final int z,
                                      final ForgeDirection forward) {
        return true;
    }

    public IOrientable getOrientable(final IBlockAccess w, final int x, final int y, final int z) {
        return this.getTileEntity(w, x, y, z);
    }

    public <T extends FoxBaseTile> T getTileEntity(final IBlockAccess w, final int x, final int y, final int z) {
        if (!this.hasBlockTileEntity()) {
            return null;
        }

        final TileEntity te = w.getTileEntity(x, y, z);
        if (this.tileEntityType.isInstance(te)) {
            return (T) te;
        }

        return null;
    }

    private boolean hasBlockTileEntity() {
        return this.tileEntityType != null;
    }

    public ForgeDirection mapRotation(final IOrientable ori, final ForgeDirection dir) {
        final ForgeDirection forward = ori.getForward();

        if (forward == null) {
            return dir;
        }

        ForgeDirection west = getForgeDirection(forward);

        if (dir == forward) {
            return ForgeDirection.SOUTH;
        }
        if (dir == forward.getOpposite()) {
            return ForgeDirection.NORTH;
        }

        if (dir == west) {
            return ForgeDirection.WEST;
        }
        if (dir == west.getOpposite()) {
            return ForgeDirection.EAST;
        }

        return ForgeDirection.UNKNOWN;
    }

    private ForgeDirection getForgeDirection(ForgeDirection forward) {
        final int west_x = forward.offsetY - forward.offsetZ;
        final int west_y = forward.offsetZ - forward.offsetX;
        final int west_z = forward.offsetX - forward.offsetY;

        ForgeDirection west = ForgeDirection.UNKNOWN;
        for (final ForgeDirection dx : ForgeDirection.VALID_DIRECTIONS) {
            if (dx.offsetX == west_x && dx.offsetY == west_y && dx.offsetZ == west_z) {
                west = dx;
            }
        }
        return west;
    }

    public void setTileEntityType(Class<? extends TileEntity> tileEntityType) {
        this.tileEntityType = tileEntityType;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        Constructor<? extends TileEntity> constructor = null;
        try {
            constructor = tileEntityType.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        constructor.setAccessible(true);
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
