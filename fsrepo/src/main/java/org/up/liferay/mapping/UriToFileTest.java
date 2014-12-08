package org.up.liferay.mapping;

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

public class UriToFileTest {

	@Test
	public void test() throws MalformedURLException, URISyntaxException {
		String uriTest = "https://boxup.uni-potsdam.de/remote.php/webdav/test.jpg";
		String uriTestFolder2 = "https://boxup.uni-potsdam.de/remote.php/webdav/test/";
				
		File uriTestFile = new File(new URL(uriTest).getPath());		
		System.out.println(uriTestFile.getPath());
		System.out.println(uriTestFile.getParent());
		assertTrue(!uriTestFile.isDirectory());
		
		
		File uriTestFolder = new File(new URL(uriTestFolder2).getPath());		
		System.out.println(uriTestFolder.getPath());
		System.out.println(uriTestFolder.getParent());
		assertTrue(uriTestFolder.isDirectory());
		
		
		
	}

}
