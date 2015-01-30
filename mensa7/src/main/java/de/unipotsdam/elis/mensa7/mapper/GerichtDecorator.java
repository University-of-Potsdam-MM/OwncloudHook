package de.unipotsdam.elis.mensa7.mapper;

import java.util.Calendar;

import de.unipotsdam.elis.provider.mensa.EssensTyp;
import de.unipotsdam.elis.provider.mensa.Gericht;
import de.unipotsdam.elis.provider.mensa.Preise;
import de.unipotsdam.elis.provider.mensa.ZusatzstoffeTyp;

public class GerichtDecorator extends Gericht {
	
	private Gericht inner;

	public GerichtDecorator(Gericht gericht) {
		this.inner = gericht;
	}
	
	@Override
	public Calendar getDate() {
		return inner.getDate();
	}
	
	@Override
	public String getDescription() {
		return inner.getDescription();
	}
	
	@Override
	public int getOrder() {
		return inner.getOrder();
	}
	
	@Override
	public Preise getPrices() {
		return inner.getPrices();
	}
	
	@Override
	public String getTitle() {
		return inner.getTitle();
	}
	
	@Override
	public EssensTyp[] getType() {
		return inner.getType();
	}
	
	@Override
	public EssensTyp getType(int i) {
		return inner.getType(i);
	}
	
	@Override
	public ZusatzstoffeTyp getZusatzstoffe() {
		return inner.getZusatzstoffe();
	}
	
	

}
