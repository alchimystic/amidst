package amidst.fragment.loader;

import java.awt.image.BufferedImage;

import amidst.fragment.colorprovider.ColorProvider;
import amidst.fragment.layer.LayerDeclaration;
import amidst.map.FragmentGraphItem;
import amidst.minecraft.world.CoordinatesInWorld;
import amidst.minecraft.world.Resolution;

public class ImageLoader extends FragmentLoader {
	private final Resolution resolution;
	private final ColorProvider colorProvider;
	private final int size;
	private final int[] rgbArray;
	private BufferedImage bufferedImage;

	public ImageLoader(LayerDeclaration declaration, Resolution resolution,
			ColorProvider colorProvider) {
		super(declaration);
		this.resolution = resolution;
		this.colorProvider = colorProvider;
		this.size = resolution.getStepsPerFragment();
		this.rgbArray = new int[size * size];
		this.bufferedImage = createBufferedImage();
	}

	private BufferedImage createBufferedImage() {
		return new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void load(FragmentGraphItem fragment) {
		doLoad(fragment);
	}

	@Override
	public void reload(FragmentGraphItem fragment) {
		doLoad(fragment);
	}

	protected void doLoad(FragmentGraphItem fragment) {
		CoordinatesInWorld corner = fragment.getCorner();
		long cornerX = corner.getXAs(resolution);
		long cornerY = corner.getYAs(resolution);
		drawToCache(fragment, cornerX, cornerY);
		bufferedImage.setRGB(0, 0, size, size, rgbArray, 0, size);
		bufferedImage = fragment.getAndSetImage(declaration.getLayerId(),
				bufferedImage);
	}

	protected void drawToCache(FragmentGraphItem fragment, long cornerX, long cornerY) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				int index = getCacheIndex(x, y);
				rgbArray[index] = colorProvider.getColorAt(fragment, cornerX,
						cornerY, x, y);
			}
		}
	}

	private int getCacheIndex(int x, int y) {
		return x + y * size;
	}
}
