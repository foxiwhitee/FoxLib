package foxiwhitee.FoxLib.client.render;

import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StaticRender {
    String modID();
    Class<? extends TileEntity> tile();
    String model();
    String texture();
    ItemRender itemRender() default @ItemRender();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface ItemRender {
        ItemRenderEntity entity() default @ItemRenderEntity();
        ItemRenderEquipped equipped() default @ItemRenderEquipped();
        ItemRenderEquippedFirstPerson equippedFirstPerson() default @ItemRenderEquippedFirstPerson();
        ItemRenderInventory inventory() default @ItemRenderInventory();
        ItemRenderFirstPersonMap firstPersonMap() default @ItemRenderFirstPersonMap();

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.ANNOTATION_TYPE)
        @interface ItemRenderEntity {
            Scale scale() default @Scale(x=1.35D, y=1.35D, z=1.35D);
            Position position() default @Position(x=0D, y=0D, z=0D);
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.ANNOTATION_TYPE)
        @interface ItemRenderEquipped {
            Scale scale() default @Scale(x=1D, y=1D, z=1D);
            Position position() default @Position(x=0.5D, y=0.5D, z=0.5D);
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.ANNOTATION_TYPE)
        @interface ItemRenderEquippedFirstPerson {
            Scale scale() default @Scale(x=1D, y=1D, z=1D);
            Position position() default @Position(x=0.5D, y=0.5D, z=0.5D);
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.ANNOTATION_TYPE)
        @interface ItemRenderInventory {
            Scale scale() default @Scale(x=1D, y=1D, z=1D);
            Position position() default @Position(x=0D, y=0D, z=0D);
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.ANNOTATION_TYPE)
        @interface ItemRenderFirstPersonMap {
            Scale scale() default @Scale(x=1D, y=1D, z=1D);
            Position position() default @Position(x=0D, y=0D, z=0D);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface Scale {
        double x();
        double y();
        double z();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface Position {
        double x();
        double y();
        double z();
    }
}
