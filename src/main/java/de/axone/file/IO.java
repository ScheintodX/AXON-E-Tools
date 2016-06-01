package de.axone.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import de.axone.data.Encoding;

public class IO {
	
	private static final Charset UTF8 = Encoding.utf8.charset(),
	                             ASCII = Encoding.ascii.charset()
	                             ;

	public static Reader fileReaderUTF8( File file ) throws FileNotFoundException {
		
		FileInputStream fIn = new FileInputStream( file );
		InputStreamReader rIn = new InputStreamReader( fIn, UTF8 );
		
		return rIn;
	}
	
	public static Reader fileReaderUTF8( Path path ) throws IOException {
		
		return Files.newBufferedReader( path, UTF8 );
	}
	
	public static BufferedReader bufferedFileReaderUTF8( File file ) throws FileNotFoundException {
		
		return new BufferedReader( fileReaderUTF8( file ) );
	}
	
	public static BufferedReader bufferedFileReaderUTF8( Path file ) throws IOException {
		
		return new BufferedReader( fileReaderUTF8( file ) );
	}
	
	public static Reader fileReaderASCII( File file ) throws FileNotFoundException {
		
		FileInputStream fIn = new FileInputStream( file );
		InputStreamReader rIn = new InputStreamReader( fIn, ASCII );
		
		return rIn;
	}
	
	public static BufferedReader bufferedFileReaderASCII( File file ) throws FileNotFoundException {
		
		return new BufferedReader( fileReaderASCII( file ) );
	}
	
	
	public static Writer fileWriterUTF8( File file ) throws FileNotFoundException {
		
		FileOutputStream fOut = new FileOutputStream( file );
		OutputStreamWriter wOut = new OutputStreamWriter( fOut, UTF8 );
		
		return wOut;
	}
	public static PrintWriter printFileWriterUTF8( File file ) throws FileNotFoundException {
		
		return new PrintWriter( fileWriterUTF8( file ) );
	}
	
	public static Writer fileWriterASCII( File file ) throws FileNotFoundException {
		
		FileOutputStream fOut = new FileOutputStream( file );
		OutputStreamWriter wOut = new OutputStreamWriter( fOut, ASCII );
		
		return wOut;
	}
	
	public static PrintWriter printFileWriterASCII( File file ) throws FileNotFoundException {
		
		return new PrintWriter( fileWriterASCII( file ) );
	}
	
	public static Writer writerUTF8( OutputStream outS ) {
		
		return new OutputStreamWriter( outS, UTF8 );
	}
	
	public static PrintWriter printWriterUTF8( OutputStream outS ) {
		
		return new PrintWriter( writerUTF8( outS ) );
	}
	
	public static Writer writerASCII( OutputStream outS ) {
		
		return new OutputStreamWriter( outS, ASCII );
	}
	
	public static PrintWriter printWriterASCII( OutputStream outS ) {
		
		return new PrintWriter( writerASCII( outS ) );
	}
	
	
	public static Reader readerUTF8( InputStream inS ) {
		
		return new InputStreamReader( inS, UTF8 );
	}
	
	public static BufferedReader bufferedReaderUTF8( InputStream inS ) {
		
		return new BufferedReader( readerUTF8( inS ) );
	}
	
	public static Reader readerASCII( InputStream inS ) {
		
		return new InputStreamReader( inS, ASCII );
	}
	
	public static BufferedReader bufferedReaderASCII( InputStream inS ) {
		
		return new BufferedReader( readerASCII( inS ) );
	}
	
}
