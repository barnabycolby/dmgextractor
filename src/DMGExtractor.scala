object DMGExtractor {
	def main(args: Array[String]) {
		if (args.length != 1) {
			this.usage
		}
	}

	def usage = {
		println("Usage: DMGExtractor file.dmg");
	}
}
