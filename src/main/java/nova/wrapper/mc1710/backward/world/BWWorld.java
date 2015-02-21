package nova.wrapper.mc1710.backward.world;

import net.minecraft.init.Blocks;
import nova.core.block.Block;
import nova.core.util.transform.Vector3i;
import nova.core.world.World;
import nova.wrapper.mc1710.forward.block.BlockWrapperRegistry;

import java.util.Optional;

/**
 * @author Calclavia
 */
public class BWWorld extends World {
	private final BWBlockAccess blockAccess;
	private final net.minecraft.world.World world;

	public BWWorld(net.minecraft.world.World world) {
		this.world = world;
		this.blockAccess = new BWBlockAccess(world);
	}

	@Override
	public void markStaticRender(Vector3i position) {
		world.markBlockForUpdate(position.x, position.y, position.z);
	}

	@Override
	public void markChange(Vector3i position) {
		world.notifyBlockChange(position.x, position.y, position.z, world.getBlock(position.x, position.y, position.z));
	}

	@Override
	public Optional<Block> getBlock(Vector3i position) {
		return blockAccess.getBlock(position);
	}

	@Override
	public boolean setBlock(Vector3i position, Block block) {
		net.minecraft.block.Block mcBlock = BlockWrapperRegistry.instance.getMCBlock(block);
		return world.setBlock(position.x, position.y, position.z, mcBlock != null ? mcBlock : Blocks.air);
	}

	@Override
	public boolean removeBlock(Vector3i position) {
		return world.setBlockToAir(position.x, position.y, position.z);
	}

	@Override
	public String getID() {
		return world.provider.getDimensionName();
	}
}
