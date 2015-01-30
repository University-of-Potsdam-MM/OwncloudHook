package de.unipotsdam.elis.mensa7.logic;

import javax.portlet.PortletSession;

import org.apache.log4j.Logger;

import de.unipotsdam.elis.provider.mensa.CampusTyp;
import de.unipotsdam.elis.util.date.TruncatedDate;

public class PortletSessionManager implements UIPersistanceLayer {
	private PortletSession portalSession;
	private Logger logger;	

	

	/**
	 * returns selected date or now
	 * 
	 * @param session
	 * @return
	 */
	public TruncatedDate getSelectedDate(PortletSession session) {
		try {
			return (TruncatedDate) session.getAttribute("selectedDate",
					PortletSession.APPLICATION_SCOPE);
		} catch (NullPointerException e) {
			return new TruncatedDate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unipotsdam.elis.util.portal.session.UIPersistanceLayer#getSelectedDate
	 * ()
	 */
	public TruncatedDate getSelectedDate() {
		TruncatedDate date = getSelectedDate(portalSession);
		logger.debug("requested date " + date);
		return date;
	}

	/**
	 * sets selected date
	 * 
	 * @param session
	 * @param date
	 */
	public void setSelectedDate(PortletSession session, TruncatedDate date) {
		logger.debug("persisted date " + date);
		session.setAttribute("selectedDate", date,
				PortletSession.APPLICATION_SCOPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unipotsdam.elis.util.portal.session.UIPersistanceLayer#setSelectedDate
	 * (de.unipotsdam.elis.util.date.TruncatedDate)
	 */
	public void setSelectedDate(TruncatedDate date) {
		setSelectedDate(portalSession, date);
	}

	public void setSelectedTab(PortletSession session, CampusTyp caption) {
		logger.debug("persisted caption " + caption);
		session.setAttribute("selectedTab", caption,
				PortletSession.APPLICATION_SCOPE);
	}

	@Override
	public void setSelectedTab(CampusTyp caption) {
		logger.debug("requested caption " + caption);
		setSelectedTab(portalSession, caption);
	}

	@Override
	public CampusTyp getSelectedTab() {
		return (CampusTyp) portalSession.getAttribute("selectedTab");

	}
}
