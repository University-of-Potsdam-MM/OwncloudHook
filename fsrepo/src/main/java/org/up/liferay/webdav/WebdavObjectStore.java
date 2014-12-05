package org.up.liferay.webdav;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sardine.DavResource;

public class WebdavObjectStore  {

	private static final Logger log = LoggerFactory
			.getLogger(WebdavObjectStore.class.getName());
	private WebdavEndpoint endpoint;	
	
	
	public WebdavObjectStore(WebdavEndpoint endpoint) {
		this.endpoint = endpoint;
	}
	

	public String createFile(String documentNameDecoded,
			String parentIdEncoded, InputStream inputStream) {		

		ByteArrayInputStream buffer = null;
		try {			
			buffer = new ByteArrayInputStream(IOUtils.toByteArray(inputStream));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String parentIdDecoded = WebdavIdDecoderAndEncoder
				.decode(parentIdEncoded);
		String path = (parentIdDecoded + documentNameDecoded);
		String completePath = endpoint.getEndpoint() + path;
		try {
			completePath = solveDuplicateFiles(endpoint, completePath);
//			if (endpoint.getSardine().exists(completePath)) {
//				endpoint.getSardine().delete(completePath);
//			}
			endpoint.getSardine().put(completePath, (InputStream) buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return WebdavIdDecoderAndEncoder.encode(path);
	}

	private String solveDuplicateFiles(WebdavEndpoint endpoint,
			String completePath) throws IOException {
		int i = 1;
		while (endpoint.getSardine().exists(completePath)) {
			completePath+="("+i+")";
			i++;
		}
		return completePath;
	}

	public String createFolder(String folderName, String parentIdEncoded) {		

		String parentIdDecoded = WebdavIdDecoderAndEncoder
				.decode(parentIdEncoded);
		String path = parentIdDecoded + folderName;
		String webdavpath = endpoint.getEndpoint() + path;
		try {
			if (!endpoint.getSardine().exists(webdavpath)) {
				endpoint.getSardine().createDirectory(webdavpath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return WebdavIdDecoderAndEncoder.encode(path);
	}

	public String createRootFolder(String folderName) {
		
		String liferayRootPath = endpoint.getEndpoint() + folderName;
		try {
			if (!endpoint.getSardine().exists(liferayRootPath)) {
				endpoint.getSardine().createDirectory(liferayRootPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return WebdavIdDecoderAndEncoder.encode(folderName);
	}

	public Boolean exists(String parentNameDecoded, String folderName) {
		 // should be in constructor but is not
										// called!!

		String path = parentNameDecoded + folderName;
		try {
			return endpoint.getSardine().exists(endpoint.getEndpoint() + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	public String getFolderPath(String folderId) {
		return WebdavIdDecoderAndEncoder.decode(folderId);
	}


//	private List<DavResource> getResourcesForID(String path,
//			boolean getDirectory) throws IOException, ExecutionException {
//		final CallContext callContext = InMemoryServiceContext.getCallContext();		
//		
//		String encodedPath = WebdavIdDecoderAndEncoder.encode(path);
//		WebdavResourceKey key = new WebdavResourceKey(encodedPath, getDirectory,
//				callContext.getUsername());
//		List<DavResource> result = InMemoryServiceContext.CACHE.get(key,
//				new WebdavCacheLoader(this, key, callContext));
////		if (!key.getGetDirectory()) {
////			InMemoryServiceContext.CACHE.invalidate(key);
////		}
//		for (DavResource davResource : result) {
//			final String encodedId = WebdavIdDecoderAndEncoder
//					.webdavToIdEncoded(davResource);
//			final WebdavResourceKey webdavResourceKey = new WebdavResourceKey(
//					encodedId, davResource.isDirectory(),
//					callContext.getUsername());
//			final WebdavObjectStore webdavObjectStore = this;
//			Thread t = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						InMemoryServiceContext.CACHE.get(webdavResourceKey,
//								new WebdavCacheLoader(webdavObjectStore,
//										webdavResourceKey, callContext));
//					} catch (ExecutionException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			});
//			t.start();
//		}
//		return result;
//	}

	public List<DavResource> getResourcesForIDintern(String encodedId,
			Boolean getDirectory) throws IOException {		
		
		String listedPath = WebdavIdDecoderAndEncoder
				.encodedIdToWebdav(encodedId);
		long before = System.currentTimeMillis();
		log.debug("showing resources for: " + listedPath);

		List<DavResource> resources = endpoint.getSardine().list(listedPath);
		// the first element is always the directory itself
		if (resources.get(0).isDirectory() && !getDirectory) {
			resources.remove(0);
		}
		long now = System.currentTimeMillis();
		log.warn("getting resource listing took: " + (now - before));
		//endpoint.getSardine().shutdown();
		return resources;
	}

	private void handleStartUpErrors(WebdavEndpoint endpoint, IOException e) {
		if (endpoint.isValidCredentialinDebug()) {
			log.error("problems with webdav authentication at owncloud", e);
		} else {
			log.debug("the user credentials are not valid");
		}
	}
		


	public void deleteDirectory(String objectIdEncoded) {
		

		String objectIdDecoded = WebdavIdDecoderAndEncoder
				.decode(objectIdEncoded);
		try {
			String finalPath = endpoint.getEndpoint() + objectIdDecoded;
			// endpoint.getSardine().exists(finalPath);
			endpoint.getSardine().delete(finalPath);
//			InMemoryServiceContext.CACHE.invalidate(new WebdavResourceKey(
//					objectIdEncoded, true, endpoint.getUser()));
			//endpoint.getSardine().shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteObject(String encodedObjectId, Boolean allVersions,
			String user) {
		deleteDirectory(encodedObjectId);
	}

	public void rename(String oldName, String newName) {		
		String oldNameUrl = endpoint.getEndpoint()
				+ WebdavIdDecoderAndEncoder.decode(oldName);
		String newNameUrl = endpoint.getEndpoint() + "/" + newName;
		try {
			if (endpoint.getSardine().exists(oldNameUrl)) {
				endpoint.getSardine().move(oldNameUrl, newNameUrl);						
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public List<String> getParentIds(String id) {
		return Collections.singletonList(WebdavIdDecoderAndEncoder.decodedIdToParentEncoded(WebdavIdDecoderAndEncoder.decode(id)));
	}

}
