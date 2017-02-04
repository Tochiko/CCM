package com.dhbwProject.benutzer;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Beruf;
import com.dhbwProject.backend.beans.Rolle;
import com.dhbwProject.backend.beans.Studiengang;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BenutzerFields extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	private TextField tfID;
	private TextField tfVorname;
	private TextField tfNachname;
	private TextField tfTelefonnummer;
	private TextField tfEmail;
	private ComboBox cbBeruf;
	private ComboBox cbRolle;
	private CheckBox chPassword;
	private ListSelect lsStudiengang;
	private dbConnect dbConnect;
	private LinkedList<Beruf> alleBerufe;
	private LinkedList<Rolle> alleRollen;	
	private TextArea taStudiengang;
	private Button btnLookupStudiengang;
	private LinkedList<Studiengang> alleStudiengaenge = new LinkedList<Studiengang>();

	
	public BenutzerFields() {
		this.setSizeUndefined();
		this.setSpacing(true);
		this.dbConnect = (dbConnect) VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.initID();
		this.initVorname();
		this.initNachname();
		this.initCbBerufe();
		this.initCbRolle();
		this.initLsStudiengang();
		this.initTelefonnummer();
		this.intitEmail();
		
		
	}
	
	private void initID() {
		this.tfID = new TextField();
		this.tfID.setInputPrompt("Anmeldename");
		this.tfID.setWidth("300px");
		this.addComponent(tfID);
	}
	
	private void initVorname() {
		this.tfVorname = new TextField();
		this.tfVorname.setInputPrompt("Vorname");
		this.tfVorname.setWidth("300px");
		this.addComponent(tfVorname);
		
	}
	
	private void initNachname() {
		this.tfNachname = new TextField();
		this.tfNachname.setInputPrompt("Nachname");
		this.tfNachname.setWidth("300px");
		this.addComponent(tfNachname);
	}
	
	private void initCbBerufe() {
		this.cbBeruf = new ComboBox();
		this.cbBeruf.setInputPrompt("Beruf");
		try {
			alleBerufe = dbConnect.getAllBeruf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Beruf beruf : alleBerufe) {
			this.cbBeruf.addItem(beruf.getBezeichnung());
		}
		this.cbBeruf.setNullSelectionAllowed(false);
		this.cbBeruf.setTextInputAllowed(false);
		this.cbBeruf.setWidth("300px");
		this.addComponent(cbBeruf);
	}
	
	private void initCbRolle() {
		this.cbRolle = new ComboBox();
		this.cbRolle.setInputPrompt("Rolle");
		try {
			alleRollen = dbConnect.getAllRolle();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Rolle rolle : alleRollen) {
			this.cbRolle.addItem(rolle.getBezeichnung());
		}
		this.cbRolle.setNullSelectionAllowed(false);
		this.cbRolle.setTextInputAllowed(false);
		this.cbRolle.setWidth("300px");
		this.addComponent(cbRolle);
	}
	
	
//	Bosse
	protected void initChPassword(){
		this.chPassword = new CheckBox("Passwort zurücksetzen?");
		this.chPassword.setValue(false);
		this.chPassword.setWidth("300px");
		this.addComponent(chPassword);
	}
	
	private void initLsStudiengang() {
		HorizontalLayout hl = new HorizontalLayout();
		this.taStudiengang = new TextArea("Studiengang");
		this.taStudiengang.setWidth("300px");
		this.taStudiengang.setReadOnly(true);

		this.btnLookupStudiengang = new Button();
		this.btnLookupStudiengang.setIcon(FontAwesome.REPLY);
		this.btnLookupStudiengang.setWidth("50px");
		this.btnLookupStudiengang.addClickListener(listener -> {
			this.alleStudiengaenge.clear();
			LookupStudiengang lookup = new LookupStudiengang();
			lookup.addCloseListener(CloseListener -> {
				alleStudiengaenge = lookup.getLSelection();
				this.setStudieng(lookup.getLSelection());
			});
			this.getUI().addWindow(lookup);
		});

		hl.setSizeUndefined();
		hl.setSpacing(true);
		hl.addComponent(this.taStudiengang);
		hl.addComponent(this.btnLookupStudiengang);
		this.addComponent(hl);
		
		
//		this.lsStudiengang = new ListSelect();
//		this.lsStudiengang.setMultiSelect(true);
//		this.lsStudiengang.setCaption("Studiengang");
//		try {
//			alleStudiengaenge = dbConnect.getAllStudiengang();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		for (Studiengang stg : alleStudiengaenge) {
//			lsStudiengang.addItem(stg.getBezeichnung());
//		}
//		this.lsStudiengang.setRows(lsStudiengang.size());
//		this.lsStudiengang.setWidth("300px");
//		this.addComponent(lsStudiengang);
	}
	
	private void initTelefonnummer() {
		this.tfTelefonnummer = new TextField();
		this.tfTelefonnummer.setInputPrompt("Telefonnummer");
		this.tfTelefonnummer.setWidth("300px");
		this.addComponent(tfTelefonnummer);
	}
	
	private void intitEmail() {
		this.tfEmail = new TextField();
		this.tfEmail.setInputPrompt("E-Mail");
		this.tfEmail.setWidth("300px");
		this.addComponent(tfEmail);
	}
	
	public void setID (Benutzer b){
		this.tfID.setValue(b.getId());
	}
	
	public String getID (){
		return this.tfID.getValue();
	}
	
	public void setVorname (Benutzer b){
		this.tfVorname.setValue(b.getVorname());
	}
	
	public String getVorname (){
		return this.tfVorname.getValue();
	}
	
	public void setNachname (Benutzer b){
		this.tfNachname.setValue(b.getNachname());
	}
	
	public String getNachname (){
		return this.tfNachname.getValue();
	}
	
	public void setBeruf (Benutzer b){
		this.cbBeruf.setValue(b.getBeruf().getBezeichnung());
	}
	
	public String getBeruf (){
		return (String) this.cbBeruf.getValue();
	}
	
	public void setRolle (Benutzer b){
		this.cbRolle.setValue(b.getRolle().getBezeichnung());
	}
	
	public String getRolle (){
		return (String) this.cbRolle.getValue();
	}
	
//	Bosse
	public void setPassword (boolean b){
		this.chPassword.setValue(b);
	}
	
	public boolean getPassword(){
		return this.chPassword.getValue();
	}
	
	
	public void setStudiengang (Benutzer b){
		this.lsStudiengang.clear();
		for (Studiengang st : b.getStudiengang()) {
			this.lsStudiengang.select(st.getBezeichnung());
		}
	}
	
	public LinkedList<String> getStudiengang (){
//		Set <Item>values=(Set<Item>) this.lsStudiengang.getValue();
		LinkedList<String> stg = new LinkedList<String>();
		for (Studiengang o : alleStudiengaenge) {
			stg.add(o.getBezeichnung());
		}
		return stg;
	}
	
	public void setTelefonnummer (Benutzer b){
		this.tfTelefonnummer.setValue(b.getTelefon());
	}
	
	public String getTelefonnummer (){
		return this.tfTelefonnummer.getValue();
	}
	
	public void setEmail (Benutzer b){
		this.tfEmail.setValue(b.getEmail());
	}
	
	public String getEmail (){
		return this.tfEmail.getValue();
	}
	
	public TextField getTfID() {
		return this.tfID;
	}
	
	public TextField getTfVorname() {
		return this.tfVorname;
	}
	
	public TextField getTfNachname() {
		return this.tfNachname;
	}
	
	public ComboBox getCbBeruf() {
		return this.cbBeruf;
	}
	
	public ComboBox getCbRolle() {
		return this.cbRolle;
	}
	
	public TextArea getTaStudiengang() {
		return this.taStudiengang;
	}
	
	public TextField getTfTelefonnummer() {
		return this.tfTelefonnummer;
	}
	
	public TextField getTfEmail() {
		return this.tfEmail;
	}

//	Bosse
	public CheckBox getChPassword(){
		return this.chPassword;
	}
	
	
	public void enableFields(boolean bool) {
		this.tfID.setEnabled(false);
		this.tfVorname.setEnabled(bool);
		this.tfNachname.setEnabled(bool);
		this.cbBeruf.setEnabled(bool);
		this.cbRolle.setEnabled(bool);
//		this.lsStudiengang.setEnabled(bool);
		this.tfTelefonnummer.setEnabled(bool);
		this.tfEmail.setEnabled(bool);
		this.chPassword.setEnabled(bool);
	}

	protected void setStudieng(LinkedList<Studiengang> studiengaenge) {
		String value = "";
		for (Studiengang stg : studiengaenge)
			value = value + stg.getBezeichnung() + "\n";
		this.taStudiengang.setReadOnly(false);
		this.taStudiengang.setValue(value);
		this.taStudiengang.setReadOnly(true);
	}
	

}
