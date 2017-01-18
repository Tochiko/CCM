package com.dhbwProject.besuche;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.dhbwProject.backend.beans.Status;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BesuchAnlage extends Window {
	private static final long serialVersionUID = 1L;
	private dbConnect dbConnection;
	private Besuch bAnlage;
	
	private BesuchFelder fields;
	private VerticalLayout vlLayout;
	private Button btnCreate;	
	
	public BesuchAnlage(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.setContent(this.initContent());
		
		setCaptionAsHtml(true);
		setCaption("<center><h3>Termin anlegen</h3></center>");
		center();
		setWidth("450px");
		setHeight("600px");
		setDraggable(true);
		setClosable(true);
		setModal(false);
	}
	
	public BesuchAnlage(Date date){
		this();
		this.fields.setDateStart(date);
		this.fields.setDateEnd(date);
	}
	
	private void initFields(){
		this.fields = new BesuchFelder();
		this.btnCreate = new Button("Termin erstellen");
		this.btnCreate.setIcon(FontAwesome.PLUS);
		this.btnCreate.setWidth("300px");
		this.btnCreate.addClickListener(listener ->{
			try {
				this.bAnlage = this.dbConnection.createBesuch(new Besuch(0, fields.getTitel(),
						fields.getDateStart(), fields.getDateEnd(),
						fields.getAdresse(), fields.getStatus(), fields.getAnsprechpartner(),
						fields.getTeilnehmenr(), null, fields.getAutor()));				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.close();
		});
		this.fields.addComponent(this.btnCreate);
	}
	
	private void initVlLayout(){
		this.initFields();
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_CENTER);
	}
	
	private Panel initContent(){
		this.initVlLayout();
		Panel p = new Panel();
		p.setContent(vlLayout);
		return p;
	}
	
	protected Besuch getAnlage(){
		return this.bAnlage;
	}

}
