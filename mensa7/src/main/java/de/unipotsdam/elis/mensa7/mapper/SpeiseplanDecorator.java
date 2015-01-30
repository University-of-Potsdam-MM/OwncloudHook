package de.unipotsdam.elis.mensa7.mapper;
import java.util.HashMap;
import java.util.TreeSet;

import com.vaadin.server.ThemeResource;

import de.unipotsdam.elis.mensa7.layout.icons.IconHashMap;
import de.unipotsdam.elis.provider.mensa.CampusTyp;
import de.unipotsdam.elis.provider.mensa.Gericht;
import de.unipotsdam.elis.provider.mensa.Speiseplan;
import de.unipotsdam.elis.provider.mensa.SpeiseplanIconHashMapEntry;
import de.unipotsdam.elis.util.date.TruncatedDate;


public class SpeiseplanDecorator extends Speiseplan {
	private Speiseplan inner;

	public SpeiseplanDecorator(Speiseplan speiseplan) {
		this.inner = speiseplan;
	}
	
	public IconHashMap getIconHashMapConverted() {
		IconHashMap hashMap = new IconHashMap();
		for (SpeiseplanIconHashMapEntry speiseplanIconHashMapEntry : this.getIconHashMap()) {
			if (speiseplanIconHashMapEntry.getValue() != null)
				hashMap.put(speiseplanIconHashMapEntry.getKey(), new ThemeResource(speiseplanIconHashMapEntry.getValue().toString()));
		}
		return hashMap;
	}
	
	public HashMap<TruncatedDate, TreeSet<Gericht>> getGerichteHashMap() {
		HashMap<TruncatedDate, TreeSet<Gericht>> hashMap = new HashMap<TruncatedDate, TreeSet<Gericht>>();
		for (Gericht entry : inner.getMeal()) {
			TruncatedDate key = new TruncatedDate(entry.getDate().getTime());
			TreeSet<Gericht> value = new TreeSet<Gericht>(java.util.Arrays.asList(entry));
			if (!hashMap.containsKey(key)) {
				hashMap.put(key, value);
			} else {
				hashMap.get(key).add(entry);
			}
		}
		return hashMap;
	}
	
	@Override
	public CampusTyp getCampus() {
		return inner.getCampus();
	}
	
	@Override
	public Gericht[] getMeal() {
		return inner.getMeal();		
	}
	
	@Override
	public Gericht getMeal(int i) {
		return inner.getMeal(i);
	}
}
