package cn.sh1rocu.esiraoa3extra.block.blockentity;

import cn.sh1rocu.esiraoa3extra.registration.AoATileEntities;
import net.minecraft.tileentity.TileEntity;
import software.bernie.aoa3.geckolib3.core.IAnimatable;
import software.bernie.aoa3.geckolib3.core.PlayState;
import software.bernie.aoa3.geckolib3.core.builder.AnimationBuilder;
import software.bernie.aoa3.geckolib3.core.controller.AnimationController;
import software.bernie.aoa3.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.aoa3.geckolib3.core.manager.AnimationData;
import software.bernie.aoa3.geckolib3.core.manager.AnimationFactory;
import software.bernie.aoa3.geckolib3.util.GeckoLibUtil;

public class AmplifierTableTileEntity extends TileEntity implements IAnimatable {

    private final AnimationFactory manager = GeckoLibUtil.createFactory(this);

    public AmplifierTableTileEntity() {
        super(AoATileEntities.AMPLIFIER_TABLE.get());
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.amplifier_table"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }
}
