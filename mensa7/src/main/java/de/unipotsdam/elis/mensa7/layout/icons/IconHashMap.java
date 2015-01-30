package de.unipotsdam.elis.mensa7.layout.icons;

import java.util.HashMap;

import com.vaadin.server.ThemeResource;

import de.unipotsdam.elis.mensa7.provider.mensaParser.EssensTyp;

public class IconHashMap extends HashMap<EssensTyp, ThemeResource> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IconHashMap() {
		this.put(EssensTyp.Rind, new com.vaadin.server.ThemeResource("pics/kuh.png"));
		this.put(EssensTyp.Fisch, new ThemeResource("pics/fisch.png"));
		this.put(EssensTyp.Huhn, new ThemeResource("pics/hahn.png"));
//		this.put(EssensTyp.Lamm, new ThemeResource("pics/lamm.png"));
		this.put(EssensTyp.Vegetarisch, new ThemeResource("pics/ovo.png"));
		this.put(EssensTyp.Vegan, new ThemeResource("pics/vegan.png"));
		this.put(EssensTyp.Schwein, new ThemeResource("pics/sau.png"));
		
	}
}
