package DMGExtractor

/**
 * Extracts the files contained by a DMG (Apple Disk Image) file
 *
 * @author Barnaby Colby
 */
object DMGExtractor {
	def main(args: Array[String]) {
		// Check whether we have exactly one file name specified
		if (args.length != 1) {
			this.usage
		}

		// Create a DMG class to represent it
		val filename = args(0);
		new DMG(filename);
	}

  /** Prints usage information about the program */
	def usage = {
		println("Usage: DMGExtractor file.dmg");
	}
}
