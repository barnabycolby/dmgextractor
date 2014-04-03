package DMGExtractor

object InvalidDMGFileExceptionType extends Enumeration {
	type InvalidDMGFileExceptionType = Value
	val EmptyFilePath, TooShort, FileNotFound, NoKolyHeader = Value
}
import InvalidDMGFileExceptionType._

class InvalidDMGFileException(exceptionType: InvalidDMGFileExceptionType) extends Exception {
	override def getMessage: String = exceptionType match {
		case EmptyFilePath => "File path was empty"
		case TooShort			 => "File is too short to contain a DMG header"
		case FileNotFound  => "File path referred to a non-existant file"
		case NoKolyHeader  => "File did not contain a KOLY header"
	}

	/**
	 * Gets the type of the exception
	 *
	 * Get the type of the exception at a more detailed level than just that it was invalid
	 * @return The exceptions type
	 */
	def getType: InvalidDMGFileExceptionType = exceptionType
}
