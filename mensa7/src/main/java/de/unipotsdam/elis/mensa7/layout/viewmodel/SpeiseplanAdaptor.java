package de.unipotsdam.elis.mensa7.layout.viewmodel;

import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

import de.unipotsdam.elis.mensa7.MensaPortletUI;
import de.unipotsdam.elis.mensa7.events.NextDayListener;
import de.unipotsdam.elis.mensa7.events.PreviousDayListener;
import de.unipotsdam.elis.mensa7.layout.SpeiseplanLayouter;
import de.unipotsdam.elis.mensa7.layout.components.GerichtComposite;
import de.unipotsdam.elis.mensa7.logic.UserMessages;
import de.unipotsdam.elis.mensa7.provider.mensaParser.Gericht;
import de.unipotsdam.elis.mensa7.provider.mensaParser.Speiseplan;
import de.unipotsdam.elis.util.date.DateUtil;
import de.unipotsdam.elis.util.date.TruncatedDate;

/**
 * wrapped das SpeiseplanGUIElement mit dynamischem Code
 * 
 * @author Julian
 * 
 */
public class SpeiseplanAdaptor {

	private PopupDateField dateChooser;
	private Button nextDay;
	private AbsoluteLayout zusatzstoffe;
	private VerticalLayout speisePlanContainer;
	private Label titelSpeisePlan;
	private Button previousDay;
	private HorizontalLayout horizontalDateContainer;
	public Logger logger = RootLogger.getLogger(SpeiseplanLayouter.class);
	private ObjectProperty<Date> dateDateSource;
	private Label statusLabel;
	private Property.ValueChangeListener valueChangeListener;

	public SpeiseplanAdaptor(PopupDateField dateChooser, Button nextDay,
			Button previousDay, VerticalLayout speisePlanContainer,
			HorizontalLayout horizontalDateContainer) {
		// initializing dateChooser
		Date now = new Date();
		this.dateDateSource = new ObjectProperty<Date>(now);

		this.dateChooser = dateChooser;
		this.nextDay = nextDay;
		this.previousDay = previousDay;
		this.speisePlanContainer = speisePlanContainer;
		this.horizontalDateContainer = horizontalDateContainer;
		
		this.statusLabel = new Label();
		
		/**
		 * adapt datecontainer
		 */

		this.nextDay.addListener(new NextDayListener());
		this.previousDay.addListener(new PreviousDayListener());
		this.horizontalDateContainer.setStyleName("horizontalDateContainer");
		configureDateChooser();

	}

	public void doyourjob(Speiseplan speiseplan, TruncatedDate truncatedDate) {
		speisePlanContainer.removeAllComponents();

		/**
		 * add gerichte
		 */
		SortedSet<Gericht> gerichts = speiseplan.getGerichteHashMap().get(
				truncatedDate);
		if (gerichts == null) {
			showError(UserMessages.NO_INFORMATION_AVAILABLE);
		} else {
			Iterator<Gericht> iterator = gerichts.iterator();
			while (iterator.hasNext()) {
				GerichtComposite gerichtComposite = new GerichtComposite(
						iterator.next(), speiseplan.getIconHashMapConverted());
				gerichtComposite.setStyleName("gericht");
				speisePlanContainer.addComponent(gerichtComposite);
			}
		}

	}

	private void showError(String nO_INFORMATION_AVAILABLE) {
		this.statusLabel.setValue(nO_INFORMATION_AVAILABLE);		
		this.speisePlanContainer.addComponent(statusLabel);
	}

	private void configureDateChooser() {
		dateChooser.setDateFormat("dd.MM.yyyy");
		dateChooser.setImmediate(true);
		// Display only year, month, and day in slash-delimited format
		addValueChangeListener();
		dateChooser.setPropertyDataSource(dateDateSource);

	}

	private void addValueChangeListener() {
		
		this.valueChangeListener = new Property.ValueChangeListener() {

			private static final long serialVersionUID = -270610080979051997L;

			public void valueChange(ValueChangeEvent event) {

				logger.trace("date clicked ... handling value change");
				String dateSelected = event.getProperty().getValue().toString();
				logger.trace("date selected is" + dateSelected);
				if (dateSelected == null) {
					logger.debug("dateselected is null... cannot publish date");
				} else {
					logger.debug("publishing date now");
					MensaPortletUI.eventbus.publish(new TruncatedDate(DateUtil.parseString(
							dateSelected, logger)));
				}
			}
		};
		dateChooser.addListener(this.valueChangeListener);
	}

	public void setTitelSpeisePlan(Label titelSpeisePlan) {
		this.titelSpeisePlan = titelSpeisePlan;
	}

	public Label getTitelSpeisePlan() {
		return titelSpeisePlan;
	}

	public void setZusatzstoffe(AbsoluteLayout zusatzstoffe) {
		this.zusatzstoffe = zusatzstoffe;
	}

	public AbsoluteLayout getZusatzstoffe() {
		return zusatzstoffe;
	}

	public void update(TruncatedDate selectedDate) {				
		this.dateChooser.removeListener(this.valueChangeListener);
		this.dateChooser.setValue(selectedDate);
		this.addValueChangeListener();
				
	}

}
