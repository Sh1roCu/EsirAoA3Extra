package cn.sh1rocu.esiraoa3extra.container;

import cn.sh1rocu.esiraoa3extra.item.misc.AttributeExchangeStone;
import cn.sh1rocu.esiraoa3extra.registration.AoABlocks;
import cn.sh1rocu.esiraoa3extra.registration.AoAContainers;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class AmplifierTableContainer extends AbstractContainerMenu {
    public final SimpleContainer inputs;
    public final ResultContainer leftNewEquip;
    public final ResultContainer rightNewEquip;
    private final ContainerLevelAccess functionCaller;
    private final Player player;

    public AmplifierTableContainer(int screenId, Inventory plInventory, ContainerLevelAccess functionCaller) {
        super(AoAContainers.AMPLIFIER_TABLE.get(), screenId);
        this.player = plInventory.player;
        this.functionCaller = functionCaller;
        this.inputs = new SimpleContainer(3) {
            @Override
            public void setChanged() {
                super.setChanged();
                AmplifierTableContainer.this.slotsChanged(this);
            }
        };
        this.leftNewEquip = new ResultContainer();
        this.rightNewEquip = new ResultContainer();
        this.addSlot(this.initFirstEquipSlot());
        this.addSlot(this.initSecondEquipSlot());
        this.addSlot(this.initExchangeStoneSlot());
        this.addSlot(this.initLeftNewEquipSlot());
        this.addSlot(this.initRightNewEquipSlot());

        int raw;
        for (raw = 0; raw < 3; ++raw) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(plInventory, col + raw * 9 + 9, 10 + 8 + col * 18, 17 + 84 + raw * 18));
            }
        }

        for (raw = 0; raw < 9; ++raw) {
            this.addSlot(new Slot(plInventory, raw, 10 + 8 + raw * 18, 17 + 142));
        }
    }

    @Override
    public void slotsChanged(Container inventory) {
        this.functionCaller.execute((world, pos) -> this.updateOutput());
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        this.functionCaller.execute((world, pos) -> {
            this.clearContainer(playerIn, world, this.inputs);
            if (this.rightNewEquip.isEmpty() && !this.leftNewEquip.isEmpty())
                this.clearContainer(playerIn, world, this.leftNewEquip);
            if (this.leftNewEquip.isEmpty() && !this.rightNewEquip.isEmpty())
                this.clearContainer(playerIn, world, this.rightNewEquip);
        });
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            if (index < 5) {
                if (!this.moveItemStackTo(slotStack, 5, this.slots.size(), true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(slotStack, itemstack);
            } else if (!this.moveItemStackTo(slotStack, 0, 5, false)) {
                if (index < 5 + 27) {
                    if (!this.moveItemStackTo(slotStack, 5 + 27, this.slots.size(), true))
                        return ItemStack.EMPTY;
                } else {
                    if (!this.moveItemStackTo(slotStack, 5, 5 + 27, false))
                        return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0)
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
            if (slotStack.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;
            slot.onTake(playerIn, slotStack);
        }
        return itemstack;
    }

    protected Slot initFirstEquipSlot() {
        return new Slot(this.inputs, 0, 31, 46) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return EsirUtil.isEsirArmourOrWeapon(stack) && AmplifierTableContainer.this.leftNewEquip.isEmpty() && AmplifierTableContainer.this.rightNewEquip.isEmpty();
            }
        };
    }

    protected Slot initSecondEquipSlot() {
        return new Slot(this.inputs, 1, 65, 46) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return EsirUtil.isEsirArmourOrWeapon(stack) && AmplifierTableContainer.this.leftNewEquip.isEmpty() && AmplifierTableContainer.this.rightNewEquip.isEmpty();
            }
        };
    }

    protected Slot initExchangeStoneSlot() {
        return new Slot(this.inputs, 2, 48, 75) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof AttributeExchangeStone;
            }
        };
    }

    protected Slot initLeftNewEquipSlot() {
        return new Slot(this.leftNewEquip, 0, 113, 61) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return this.hasItem();
            }

            @Nonnull
            @Override
            public ItemStack onTake(Player thePlayer, ItemStack stack) {
                AmplifierTableContainer.this.inputs.setItem(0, ItemStack.EMPTY);
                AmplifierTableContainer.this.inputs.setItem(1, ItemStack.EMPTY);
                if (!AmplifierTableContainer.this.rightNewEquip.isEmpty())
                    AmplifierTableContainer.this.inputs.getItem(2).shrink(1);
                return stack;
            }
        };
    }

    protected Slot initRightNewEquipSlot() {
        return new Slot(this.rightNewEquip, 0, 146, 61) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return this.hasItem();
            }

            @Nonnull
            @Override
            public ItemStack onTake(Player thePlayer, ItemStack stack) {
                AmplifierTableContainer.this.inputs.setItem(0, ItemStack.EMPTY);
                AmplifierTableContainer.this.inputs.setItem(1, ItemStack.EMPTY);
                if (!AmplifierTableContainer.this.leftNewEquip.isEmpty())
                    AmplifierTableContainer.this.inputs.getItem(2).shrink(1);
                return stack;
            }
        };
    }

    public void updateOutput() {
        ItemStack leftOldEquip = this.inputs.getItem(0);
        ItemStack rightOldEquip = this.inputs.getItem(1);
        ItemStack exchangeStone = this.inputs.getItem(2);
        if ((leftOldEquip.isEmpty() || rightOldEquip.isEmpty() || exchangeStone.isEmpty())) {
            if (((!this.leftNewEquip.isEmpty() && this.rightNewEquip.isEmpty()) || (!this.rightNewEquip.isEmpty() && this.leftNewEquip.isEmpty())))
                return;
            resetOutputs();
            return;
        }
        float[] leftNewAttribute = EsirUtil.getAttribute(rightOldEquip);
        float[] rightNewAttribute = EsirUtil.getAttribute(leftOldEquip);
        if (leftNewAttribute == null || rightNewAttribute == null)
            return;
        if ((leftOldEquip.getItem() instanceof ArmorItem && rightOldEquip.getItem() instanceof ArmorItem)
                || (!(leftOldEquip.getItem() instanceof ArmorItem) && !(rightOldEquip.getItem() instanceof ArmorItem))) {
            ItemStack leftNewEquip = leftOldEquip.copy();
            ItemStack rightNewEquip = rightOldEquip.copy();
            this.leftNewEquip.setItem(0, EsirUtil.upgradeEquip(this.player, leftNewEquip, (int) leftNewAttribute[1], (int) leftNewAttribute[2]));
            this.rightNewEquip.setItem(0, EsirUtil.upgradeEquip(this.player, rightNewEquip, (int) rightNewAttribute[1], (int) rightNewAttribute[2]));
        } else
            this.resetOutputs();
    }

    private void resetOutputs() {
        this.leftNewEquip.setItem(0, ItemStack.EMPTY);
        this.rightNewEquip.setItem(0, ItemStack.EMPTY);
    }

    protected Block getBlock() {
        return AoABlocks.AMPLIFIER_TABLE.get();
    }

    public static void openContainer(ServerPlayer player, final BlockPos pos) {
        NetworkHooks.openGui(player, new MenuProvider() {
            @Nonnull
            @Override
            public Component getDisplayName() {
                return new TranslatableComponent("container.esiraoa3extra.amplifier_table");
            }

            @Override
            public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
                return new AmplifierTableContainer(windowId, inv, ContainerLevelAccess.create(player.level, pos));
            }
        }, pos);
    }

    @Override
    public boolean stillValid(Player Player) {
        return stillValid(this.functionCaller, Player, this.getBlock());
    }
}
