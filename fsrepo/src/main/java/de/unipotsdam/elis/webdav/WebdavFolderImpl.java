package de.unipotsdam.elis.webdav;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.Properties;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;

import de.unipotsdam.elis.chemistry.opencmis.inmemory.storedobj.api.DocumentVersion;
import de.unipotsdam.elis.chemistry.opencmis.inmemory.storedobj.api.VersionedDocument;
import de.unipotsdam.elis.chemistry.opencmis.inmemory.storedobj.impl.FolderImpl;

public class WebdavFolderImpl extends FolderImpl implements VersionedDocument,
		DocumentVersion {
	private String decodedId;
	
	private static Log log = LogFactoryUtil
			.getLog(WebdavFolderImpl.class);

	public WebdavFolderImpl(DavResource davResource) {
		this.decodedId = WebdavIdDecoderAndEncoder
				.webdavToIdEncoded(davResource);
		setDefault();
		setNameProperties();
	}

	public WebdavFolderImpl(String encodedId) {
		this.decodedId = encodedId;
		setDefault();
		setNameProperties();
	}
	

	private String setNameProperties() {
		String name = WebdavIdDecoderAndEncoder.encodedIdToName(decodedId);
		String parentID = WebdavIdDecoderAndEncoder
				.decodedIdToParent(WebdavIdDecoderAndEncoder.decode(decodedId));
		this.setName(name);
		setParentId(parentID);
		this.setId(decodedId);
		return parentID;
	}

	private void setDefault() {
		this.setTypeId("cmis:folder");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(System.currentTimeMillis());
		this.setCreatedAt(cal);
		this.setModifiedAt(cal);
	}

	@Override
	public DocumentVersion addVersion(VersioningState verState, String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteVersion(DocumentVersion version) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCheckedOut() {
		return true; // liferay soll denken, editieren zu können
	}

	@Override
	public void cancelCheckOut(String user) {
		// we don't have checkout
	}

	@Override
	public DocumentVersion checkOut(String user) {
		// we don't do anything and only have one Version
		return this;
	}

	@Override
	public void checkIn(boolean isMajor, Properties properties,
			ContentStream content, String checkinComment,
			List<String> policyIds, String user) {
		// we don't do anything
	}

	@Override
	public List<DocumentVersion> getAllVersions() {
		// we don't do anything and only have one Version
		DocumentVersion documentVersionImpl = checkOut(null);
		return Collections.singletonList(documentVersionImpl);
	}

	@Override
	public DocumentVersion getLatestVersion(boolean major) {
		// we don't do anything and only have one Version
		return checkOut(null);
	}

	@Override
	public String getCheckedOutBy() {
//		return InMemoryServiceContext.getCallContext().getUsername();
		
		return null;
	}

	@Override
	public DocumentVersion getPwc() {
		return checkOut(null);
	}

	@Override
	public boolean isMajor() {
		return true;
	}

	@Override
	public boolean isPwc() {
		return true;
	}

	@Override
	public void commit(boolean isMajor) {
		// we don't do anything

	}

	@Override
	public void setCheckinComment(String comment) {
		// we don't do anything
	}

	@Override
	public String getCheckinComment() {
		return "we don't check in";
	}

	@Override
	public String getVersionLabel() {
		return "1.0";
	}

	@Override
	public VersionedDocument getParentDocument() {
		return computerParentDocument(decodedId);
	}
	
	public static VersionedDocument computerParentDocument(String decodedId) {
		if (decodedId == null || decodedId.equals("/")
				|| WebdavIdDecoderAndEncoder.LIFERAYROOTID.equals(decodedId)) {
			return WebdavObjectStore.createRootFolderResult();
		}
		try {
			String parentIdEncoded = WebdavIdDecoderAndEncoder
					.decodedIdToParentEncoded(decodedId);			
			WebdavFolderImpl documentImpl = new WebdavFolderImpl(
					parentIdEncoded);
			return documentImpl;
		} catch (NullPointerException e) {
			return null;
		}		
	}

	@Override
	public boolean hasContent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ContentStream getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContent(ContentStream content) {
		// TODO Auto-generated method stub

	}

	public String getAbsolutePath() {
		return this.decodedId;
	}

	public boolean canWrite() {
		return true;
	}
	
	@Override
	public String getParentId() {
		return WebdavIdDecoderAndEncoder.decodedIdToParentEncoded(decodedId);
	}
	
	@Override
	public String getName() {
		return WebdavIdDecoderAndEncoder.encodedIdToName(this.decodedId);
	}
	
	public Boolean exists(WebdavObjectStore objectStore) {
		return objectStore.exists(this);
	}

	public boolean canRead() {
		return true;
	}

	public void delete(WebdavObjectStore webdavObjectStore) {
		webdavObjectStore.deleteDirectory(this.getId());
		
	}

}
