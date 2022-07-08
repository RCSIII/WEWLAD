package me.wewlad.Blocks.ExplosiveBlocks;

import net.minecraft.world.level.block.Block;

public class TestTNTBlock extends BaseExplosiveBlock{

    public TestTNTBlock(Properties properties){
        super(properties, ExplosiveType.TEST_TNT, Block.box(6, 0, 6, 10, 4, 10));
    }
}
