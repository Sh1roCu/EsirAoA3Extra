package cn.sh1rocu.esiraoa3extra.container;

import cn.sh1rocu.esiraoa3extra.item.misc.AttributeExchangeStone;
import cn.sh1rocu.esiraoa3extra.registration.AoABlocks;
import cn.sh1rocu.esiraoa3extra.registration.AoAContainers;
import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class AmplifierTableContainer extends Container {
    public final Inventory inputs;
    public final CraftResultInventory leftNewEquip;
    public final CraftResultInventory rightNewEquip;
    private final IWorldPosCallable functionCaller;
    private final PlayerEntity player;

    public AmplifierTableContainer(int screenId, PlayerInventory plInventory, IWorldPosCallable functionCaller) {
        super(AoAContainers.AMPLIFIER_TABLE.get(), screenId);
        this.player = plInventory.player;
        this.functionCaller = functionCaller;
        this.inputs = new Inventory(3) {
            @Override
            public void setChanged() {
                super.setChanged();
                AmplifierTableContainer.this.slotsChanged(this);
            }
        };
        this.leftNewEquip = new CraftResultInventory();
        this.rightNewEquip = new CraftResultInventory();
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
    public void slotsChanged(IInventory inventory) {
        this.functionCaller.execute((world, pos) -> this.updateOutput());
    }

    @Override
    public void removed(PlayerEntity playerIn) {
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
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
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
            public boolean mayPickup(PlayerEntity playerIn) {
                return this.hasItem();
            }

            @Nonnull
            @Override
            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
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
            public boolean mayPickup(PlayerEntity playerIn) {
                return this.hasItem();
            }

            @Nonnull
            @Override
            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
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

    public static void openContainer(ServerPlayerEntity player, final BlockPos pos) {
        NetworkHooks.openGui(player, new INamedContainerProvider() {
            @Nonnull
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("container.esiraoa3extra.amplifier_table");
            }

            @Override
            public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player) {
                return new AmplifierTableContainer(windowId, inv, IWorldPosCallable.create(player.level, pos));
            }
        }, pos);
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return stillValid(this.functionCaller, playerEntity, this.getBlock());
    }
}
