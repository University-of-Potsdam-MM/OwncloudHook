package org.up.liferay.webdav;

import java.util.List;

import com.github.sardine.DavResource;
import com.google.common.cache.CacheLoader;

import cz.topolik.fsrepo.LocalFileSystemRepository;

public class WebdavCacheLoader extends CacheLoader<WebdavResourceKey, List<DavResource>> {
		


	@Override
	public List<DavResource> load(WebdavResourceKey key) throws Exception {
		return LocalFileSystemRepository.getWebdavRepository().getResourcesForIDintern(key.getEncodedId(), key.getGetDirectory());
	}

}
