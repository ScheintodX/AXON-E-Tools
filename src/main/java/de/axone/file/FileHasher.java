package de.axone.file;

import java.io.File;

public class FileHasher {

	/**
	 * check recursively if something has changed and
	 * compare to old checksum.
	 * 
	 * @param dir to check
	 * @param oldChecksum from last run
	 * @return <tt>true</tt> if changed
	 */
	public static boolean hasChanged( File dir, int oldChecksum ){

		int newSum = checksumDir( dir );

		if( newSum != oldChecksum ){

			return true;
		} else {
			return false;
		}
	}

	public static int checksumDir( File dir ){

		File [] subs = dir.listFiles();

		int sum = 0;

		for( File sub : subs ){

			if( sub.isDirectory() ){

				sum ^= checksumDir( sub );
			} else {
				sum ^= checksum( sub );
			}
		}

		return sum;
	}

	/**
	 * Generate a checksum over relevant file parameters.
	 *
	 * @param file
	 * @return the generated checksum
	 */
	public static long checksum( File file ){

		long lastModified = file.lastModified();

		String checkMe = file.getPath() + "#" + lastModified;

		return checkMe.hashCode();
	}

}
