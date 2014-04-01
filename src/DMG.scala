package DMGExtractor

/**
 * Parses a DMG (Apple Disk Image) file and stores it's structure and data internally
 *
 * @author Barnaby Colby
 */
class DMG(fileName: String) {
	fileName match {
		case null => throw new IllegalArgumentException("Filename was null")
		case ""   => throw new InvalidDMGFileException("Filename was empty")
	}
}
