package amidst.fragment.colorprovider;

import amidst.map.FragmentGraphItem;
import amidst.minecraft.world.SlimeChunkOracle;

public class SlimeColorProvider implements ColorProvider {
	private static final int SLIME_CHUNK_COLOR = 0xA0FF00FF;
	private static final int NOT_SLIME_CHUNK_COLOR = 0x00000000;

	private final SlimeChunkOracle slimeChunkOracle;

	public SlimeColorProvider(SlimeChunkOracle slimeChunkOracle) {
		this.slimeChunkOracle = slimeChunkOracle;
	}

	@Override
	public int getColorAt(FragmentGraphItem fragment, long cornerX, long cornerY, int x,
			int y) {
		if (slimeChunkOracle.isSlimeChunk(cornerX + x, cornerY + y)) {
			return SLIME_CHUNK_COLOR;
		} else {
			return NOT_SLIME_CHUNK_COLOR;
		}
	}
}
