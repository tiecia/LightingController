package devices.opc;

/**
 * Represents one strip of pixels, no larger than 64 pixels.
 */
public class PixelStrip {

	/** Link back to the Fadecandy device. */
	private final OpcDevice opcDevice;

	/** Number of pixels in this strip. */
	private final int pixelCount;

	/** Pin number on the Fadecandy device. In the range 0 to 7. */
	private final int pinNumber;

	/**
	 * Relative address within this {@link OpcClient}. In the range 0 to the
	 * total number of pixels connected to the server.
	 */
	private int firstOpcPixel = 0;

	/**
	 * Absolute address of the first pixel. For Fadecandy devices, output pixels
	 * are numbered from 0 through 511. Pin 1 begins at index 0, pin 2 begins at
	 * index 64, etc.
	 */
	private final int firstOutputPixel;

	/** Optional description for this strip. */
	private String description;

	/**
	 * {@link Animation} object attached to this strip. May be changed during
	 * execution. May be {@code null}.
	 */
	private Animation animation;
	private RunningAnimation currentRunningAnimation;

	private PixelStrip thisStrip = this;

	protected PixelStrip(OpcDevice device, int pin, int pixCount, int offset, String desc) {
		assert device != null;
		assert pixCount > 0 && pixCount <= 64;
		this.opcDevice = device;
		this.pixelCount = pixCount;
		this.pinNumber = pin;
		this.description = desc;
		this.firstOpcPixel = device.opcOffset + device.pixelCount;
		this.firstOutputPixel = this.pinNumber * 64 + offset;
		this.currentRunningAnimation = new RunningAnimation();
	}

	/**
	 * Execute an {@link Animation}, if one has been registered to this strip.
	 * 
	 * @return whether a {@code show} operation will be needed.
	 */
	public boolean animate() {
		System.out.println("Animation Started on Device: " + toString());
		animation.reset(this);
		if(!currentRunningAnimation.isAlive()) {
			this.currentRunningAnimation.start();
		}
		return false;
	}

	/**
	 * Reset all pixels on this strip to black.
	 */
	public void clear() {
		for (int i = 0; i < pixelCount; i++) {
			this.setPixelColor(i, OpcClient.BLACK);
		}
	}

	//My Method
	public void update(){

	}

	/**
	 * @return a JSON fragment representing the strip mapping for these pixels.
	 */
	protected String getConfig() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(opcDevice.opcClient.channel).append(", ");
		sb.append(this.firstOpcPixel).append(", ");
		sb.append(this.firstOutputPixel).append(", ");
		sb.append(this.pixelCount);
		sb.append(" ]");
		return sb.toString();
	}

	/**
	 * @return the number of pixels in this strip.
	 */
	public int getPixelCount() {
		return this.pixelCount;
	}

	/**
	 * Register a new Animation against this strip.
	 * 
	 * @param a new {@code Animation} object or {@code null}.
	 */
	public void setAnimation(Animation a) {
		this.animation = a;
		if (this.animation == null) {
			this.clear();
		} else {
			this.animation.reset(this);
		}
	}
	
	/**
	 * @return the current running Animation.  May be null.
	 */
	public Animation getAnimation() {
		return this.animation;
	}
	
	/**
	 * Push a number value to the currently running animation.  This only 
	 * applies if the Animation has been defined as reactive to the environment.
	 */
	public void setValue(double n) {
		if (this.animation != null) {
			this.animation.setValue(n);
		}
	}

	/**
	 * Set a pixel color within the global pixel map of this strip.
	 * 
	 * @param pixelNumber absolute number of the pixel within this strip.
	 * @param color color represented as an integer.
	 */
	public void setPixelColor(int pixelNumber, int color) {
		assert pixelNumber < this.pixelCount;
		int opcPixel = pixelNumber + this.firstOpcPixel;
		this.opcDevice.setPixelColor(opcPixel, color);
	}
	
	protected int getMaxOpcPixel() {
		return this.firstOpcPixel + this.pixelCount;
	}

	public void show(){
		opcDevice.show();
	}

	@Override
	public String toString() {
		String d = (description == null) ? "" : ",\"" + description + "\"";
		return "PixelStrip(" + this.pixelCount + d + ")";
	}

	private class RunningAnimation extends Thread{
		public void run() {
			if (animation != null) {
				animation.draw(thisStrip);
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			run();
		}
	}
}
