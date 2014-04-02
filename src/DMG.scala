package DMGExtractor

// Used to store the DMG file object
import scala.io.Source

// Thrown when the specified file cannot be found
import java.io.FileNotFoundException

/**
 * Parses a DMG (Apple Disk Image) file and stores it's structure and data internally
 *
 * @throws IllegalArgumentException If given filename is null
 * @throws InvalidDMGSourceException If given filename is invalid
 *
 * @author Barnaby Colby
 */
class DMG(val filePath: String) {
	// Deal with the three possible cases for the filename
	filePath match {
		case null => throw new IllegalArgumentException("File path was null")
		case ""   => throw new InvalidDMGFileException("File path was empty")
		case _		=> this.file = this.retrieveSource(filePath)
	}

	// Stores the object relating to the DMG file
	private var _file: Source = _

	/**
	 * Sets the file object, which stores information about the DMG file
	 * @param file The new file object
	 */
	def file_=(file: Source) {
		this._file = file
	}

	/**
	 * Gets the file object, which stores information about the DMG file
	 * @return The file object
	 */
	def file: Source = this._file

	/**
	 * Retrieves the file referred to by the filename
	 * @param filePath The path of the file
	 * @return A file object that refers to the file
	 * @throws InvalidDMGSourceException If the fileName refers to a non-existant file
	 */
	def retrieveSource(filePath: String): Source = {
		try {
			Source.fromFile(filePath)
		}
		catch {
			case ex: FileNotFoundException => throw new InvalidDMGFileException("File path referred to a non-existant file")
		}
	}
}
