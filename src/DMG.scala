package DMGExtractor

// Used to store the DMG file object
import java.io.File

// Thrown when the specified file cannot be found
import java.io.FileNotFoundException

// Used to create the header
import java.io.RandomAccessFile

/**
 * Parses a DMG (Apple Disk Image) file and stores it's structure and data internally
 *
 * @param filePath The path of the dmg file
 * @throws IllegalArgumentException If given filename is null
 * @throws InvalidDMGSourceException If given filename is invalid, or file is too short to contain a valid DMG header
 *
 * @author Barnaby Colby
 */
class DMG(val filePath: String) {
	// Deal with the three possible cases for the filename
	filePath match {
		case null => throw new IllegalArgumentException("File path was null")
		case ""   => throw new InvalidDMGFileException(InvalidDMGFileExceptionType.EmptyFilePath)
		case _		=> this.file = this.retrieveFile(filePath)
	}

	// Check whether the file is too short to contain a header
	if (this.file.length < 512) {
		throw new InvalidDMGFileException(InvalidDMGFileExceptionType.TooShort)
	}

	// Parse the header
	this.header = new Header(this.getHeaderBytes)

	// Stores the object relating to the DMG file
	private var _file: RandomAccessFile = _

	// Stores the object relating to the header of the DMG file
	private var _header: Header = _

	/**
	 * Sets the file object, which stores information about the DMG file
	 * @param file The new file object
	 */
	def file_=(file: RandomAccessFile) {
		this._file = file
	}

	/**
	 * Gets the file object, which stores information about the DMG file
	 * @return The file object
	 */
	def file: RandomAccessFile = this._file

	/**
	 * Sets the header object, which stores information about the files DMG header
	 * @param header The new header object
	 */
	def header_=(header: Header) {
		this._header = header
	}

	/**
	 * Gets the header object, which stores information about the files DMG header
	 * @return The header object
	 */
	def header: Header = this._header

	/**
	 * Retrieves the file referred to by the filename
	 * @param filePath The path of the file
	 * @return A file object that refers to the file
	 * @throws InvalidDMGSourceException If the fileName refers to a non-existant file, or a directory
	 */
	def retrieveFile(filePath: String): RandomAccessFile = {
		val file = new File(filePath)
		// Check whether the file exists
		if (!file.exists) {
			throw new InvalidDMGFileException(InvalidDMGFileExceptionType.FileNotFound)
		}

		// Check whether the path referred to a directory
		if (file.isDirectory()) {
			throw new InvalidDMGFileException(InvalidDMGFileExceptionType.Directory)
		}

		return new RandomAccessFile(file, "r")
	}

	/**
	 * Gets the set of bytes where the header should be
	 * @return The set of bytes that should contain the header
	 */
	def getHeaderBytes: Array[Byte] = {
		// Work out the position that we should access the header from
		val sizeOfHeader = 512
		val seekPosition = this.file.length - sizeOfHeader

		// Read the header bytes
		var header = new Array[Byte](sizeOfHeader)
		this.file.seek(seekPosition)
		this.file.read(header, 0, 512)

		return header
	}

}
