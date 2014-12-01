/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.up.liferay.hooks.documentsandmedia;

import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.repository.cmis.CMISRepository;


/**
 * CMIS does not provide vendor neutral support for workflow, metadata, tags,
 * categories, etc. They will be ignored in this implementation.
 *
 * @author Alexander Chow
 * @see <a href="http://wiki.oasis-open.org/cmis/Candidate%20v2%20topics">
 *      Candidate v2 topics</a>
 * @see <a href="http://wiki.oasis-open.org/cmis/Mixin_Proposal">Mixin / Aspect
 *      Support</a>
 * @see <a
 *      href="http://www.oasis-open.org/committees/document.php?document_id=39631">
 *      CMIS Type Mutability proposal</a>
 */
public class OwncloudCMISRepository extends CMISRepository {

	static OwncloudCMISAtomPubRepository endpoint = new OwncloudCMISAtomPubRepository();
	
//	public OwncloudCMISRepository() {
//		super(endpoint);			
//		endpoint.setCmisRepository(this);
//	}
	
	public OwncloudCMISRepository(CMISRepositoryHandler cmisRepositoryHandler) {
		super(cmisRepositoryHandler);
	}
}