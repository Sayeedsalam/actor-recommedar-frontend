package com.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	// User name
	String User_name = "";
	final Button login = new Button("Log In");
	// Define GUI components and containers
	DialogBox dlg;
	final HTML login_msg = new HTML("Registered User - Sign In", true);
	final HTML signup_msg = new HTML("New User - Sign UP", true);
	Button current_Actor = new Button();
	TextBox New_Actor_Text = new TextBox();
	Button New_Actor_Button = new Button("Add");
	TextBox New_Role_Text = new TextBox();
	final SuggestBox nameField = new SuggestBox(
			new Role_Suggestion().get_Roles(), New_Role_Text);
	Button New_Role_Button = new Button("Add");
	HTML New_Role_message = new HTML();
	TextBox New_Synonym_Text = new TextBox();
	Label _history = new Label("Update History");
	Button New_Synonym_Button = new Button("Add");
	HTML New_Synonym_message = new HTML();
	Button Submit = new Button("Submit");
	Button Dictionary_Preview = new Button("Dictionary_Preview");
	Button Log = new Button("Log");
	Button Log_out = new Button("Log Out");
	VerticalPanel Actor_vertical_Panel = new VerticalPanel();
	VerticalPanel Url_vertical_Panel = new VerticalPanel();
	VerticalPanel Role_vertical_Panel = new VerticalPanel();
	VerticalPanel Synonym_vertical_Panel = new VerticalPanel();
	List<Button> Actors = new ArrayList<Button>();
	List<Button> Roles = new ArrayList<Button>();
	List<Button> Delete_Roles = new ArrayList<Button>();
	List<Button> Synonyms = new ArrayList<Button>();
	List<Button> Delete_Synonyms = new ArrayList<Button>();
	List<Button> Delete_Actors = new ArrayList<Button>();
	List<TextBox> Sdate = new ArrayList<TextBox>();
	List<TextBox> Edate = new ArrayList<TextBox>();
	List<Integer> Updated_Roles = new ArrayList<Integer>();
	List<Integer> Deleted_Roles = new ArrayList<Integer>();
	List<Integer> Deleted_Synonyms = new ArrayList<Integer>();
	List<Integer> Updated_Synonyms = new ArrayList<Integer>();
	Button First_Url = new Button();

	// create public values
	String log = "";
	String delete_text = "Are you sure you want to delete";
	String delete_choice = "";
	int delete_index = -1;
	String Preview = "";
	String Selected_Actor = "";
	int window_height = Window.getClientHeight();
	int window_width = Window.getClientWidth();
	String login_response_text = "1";

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {

		/*
		 * Add content to the HTML containers
		 */
		// add short cut handler
		Url_vertical_Panel.add(First_Url);
		First_Url.setVisible(false);
		// RootPanel.get().addHandler(new Shortcuts_Handler(), );
		// ControlsContainer
		HorizontalPanel Controls = new HorizontalPanel();
		/*
		 * Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
		 *
		 * @Override public void onPreviewNativeEvent(NativePreviewEvent event)
		 * { NativeEvent ne = event.getNativeEvent(); if (ne.getCharCode() ==
		 * KeyCodes.KEY_A && ne.getShiftKey()) { New_Actor_Text.setFocus(true);
		 * New_Actor_Text.setText(""); } else if (ne.getCharCode() ==
		 * KeyCodes.KEY_S && ne.getShiftKey()) {
		 * New_Synonym_Text.setFocus(true); New_Synonym_Text.setText(""); } else
		 * if (ne.getCharCode() == KeyCodes.KEY_R && ne.getShiftKey()) {
		 * New_Role_Text.setFocus(true); New_Role_Text.setText(""); } else if
		 * (ne.getCharCode() == KeyCodes.KEY_U && ne.getShiftKey()) {
		 * First_Url.setFocus(true); } else if (ne.getCharCode() ==
		 * KeyCodes.KEY_C && ne.getShiftKey()) { Log.setFocus(true); }
		 *
		 * } });
		 */
		final ListBox lb = new ListBox();
		lb.addItem("Not Updated");
		lb.addItem("Updated");
		lb.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if (lb.getSelectedItemText() == "Not Updated") {
					// //////Window.alert("not updated");
					Get_All_Actors("IS NULL");
				} else {// //////Window.alert("updated");
					Get_All_Actors("IS NOT NULL");
				}

			}
		});

		// Make enough room for all five items (setting this value to 1 turns it
		// into a drop-down list).
		lb.setVisibleItemCount(1);

		// Add it to the root panel.
		// Controls.add(lb);
		Controls.add(Log);
		Controls.add(Log_out);
		Controls.setSpacing(30);
		RootPanel.get("ControlsContainer").add(Controls);
		// Actor
		VerticalPanel temp_vertical_Panel = new VerticalPanel();
		temp_vertical_Panel.setSpacing(10);
		FlowPanel Actor_header = new FlowPanel();
		New_Actor_Text.setText("New Actor");
		Actor_header.add(New_Actor_Text);
		Actor_header.add(New_Actor_Button);
		FlowPanel Update_history = new FlowPanel();
		// _history.setText("Update History");
		Update_history.add(_history);
		Update_history.add(lb);
		ScrollPanel Actors_sp = new ScrollPanel();
		temp_vertical_Panel.add(Actor_header);
		temp_vertical_Panel.add(Update_history);
		temp_vertical_Panel.add(Actor_vertical_Panel);
		Actors_sp.add(temp_vertical_Panel);
		DecoratorPanel ActorPanel = new DecoratorPanel();
		Actors_sp.setHeight("500px");
		// Actors_sp.add(Actor_header1);
		ActorPanel.add(Actors_sp);
		// ActorPanel.setStyleName("noOutline");
		RootPanel.get("ActorsContainer").add(ActorPanel);

		// url to be implemented here

		ScrollPanel Url_sp = new ScrollPanel();
		Url_sp.setHeight("500px");
		Url_sp.add(Url_vertical_Panel);
		Url_vertical_Panel.setSpacing(10);
		DecoratorPanel UrlPanel = new DecoratorPanel();
		// UrlPanel.setStyleName("noOutline");
		UrlPanel.add(Url_sp);
		RootPanel.get("UrlsContainer").add(UrlPanel);

		// Roles
		VerticalPanel temp_Role_Panel = new VerticalPanel();
		temp_Role_Panel.setSpacing(10);
		FlowPanel Role_header = new FlowPanel();
		New_Role_Text.setText("New Role");
		Role_header.add(nameField);
		nameField.addStyleName("suggestPopupContent");
		Role_header.add(New_Role_Button);
		ScrollPanel Roles_sp = new ScrollPanel();
		Roles_sp.setHeight("500px");
		temp_Role_Panel.add(Role_header);
		temp_Role_Panel.add(New_Role_message);
		temp_Role_Panel.add(Role_vertical_Panel);
		temp_Role_Panel.add(Dictionary_Preview);
		temp_Role_Panel.add(Submit);
		Submit.addClickHandler(new Role_SubmitHandler());
		Roles_sp.add(temp_Role_Panel);
		DecoratorPanel RolePanel = new DecoratorPanel();
		RolePanel.add(Roles_sp);
		// RolePanel.setStyleName("noOutline");
		RootPanel.get("RolesContainer").add(RolePanel);

		// Synonyms
		VerticalPanel temp_Synonym_Panel = new VerticalPanel();
		temp_Synonym_Panel.setSpacing(10);
		FlowPanel Synonym_header = new FlowPanel();
		New_Synonym_Text.setText("New Name");
		Synonym_header.add(New_Synonym_Text);
		Synonym_header.add(New_Synonym_Button);
		ScrollPanel Synonyms_sp = new ScrollPanel();
		Synonyms_sp.setHeight("500px");
		temp_Synonym_Panel.add(Synonym_header);
		temp_Synonym_Panel.add(New_Synonym_message);
		temp_Synonym_Panel.add(Synonym_vertical_Panel);
		Synonyms_sp.add(temp_Synonym_Panel);
		DecoratorPanel SynonymPanel = new DecoratorPanel();
		SynonymPanel.add(Synonyms_sp);
		// SynonymPanel.setStyleName("noOutline");
		RootPanel.get("SynonymsContainer").add(SynonymPanel);
		Window.getClientHeight();
		RootPanel.get().setSize(((window_width + 50)) + "px",
				(window_height - 20) + "px");
		int width = RootPanel.get().getOffsetWidth();
		RootPanel.get("ActorsContainer").setSize((.24 * width) + "px",
				(window_height - 20) + "px");
		RootPanel.get("UrlsContainer").setSize((.17 * width) + "px",
				(window_height - 20) + "px");
		RootPanel.get("SynonymsContainer").setSize((.24 * width) + "px",
				(window_height - 20) + "px");
		RootPanel.get("RolesContainer").setSize((.29 * width) + "px",
				(window_height - 20) + "px");
		ActorPanel.setWidth("100%");
		Actors_sp.setWidth("100%");
		New_Actor_Text.setWidth("50%");
		Url_sp.setWidth("100%");
		UrlPanel.setWidth("90%");
		Roles_sp.setWidth("100%");
		RolePanel.setWidth("100%");
		New_Role_Text.setWidth("50%");
		nameField.setHeight(New_Role_Button.getOffsetHeight() + "px");
		SynonymPanel.setWidth("100%");
		Synonyms_sp.setWidth("100%");
		New_Synonym_Text.setWidth("50%");

		dlg = new Login();
		int height = Window.getClientHeight();
		width = Window.getClientWidth();
		dlg.show();
		dlg.setAutoHideEnabled(false);
		dlg.setWidth("150%");
		dlg.setHeight("150%");
		dlg.setPopupPosition((width - 600) / 2, (height - 500) / 2);

		// Update_Role("pre", "Roosevelt", "2017", "2018");
		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				// //////////Window.alert(event.getHeight()+""+event.getWidth());
				// yourCustomLayoutAdjustmentMethod(event.getHeight(),
				// event.getWidth());
				// RootPanel.get("RolesContainer").setWidth((Window.getClientWidth()*.25)+"px");
				// RootPanel.get().setSize((event.getWidth())+"px",
				// event.getHeight()+"px");
				RootPanel.get().setSize(((window_width + 50)) + "px",
						(window_height - 20) + "px");
				int width = RootPanel.get().getOffsetWidth();
				RootPanel.get("ActorsContainer").setSize((.24 * width) + "px",
						event.getHeight() + "px");
				RootPanel.get("UrlsContainer").setSize((.17 * width) + "px",
						event.getHeight() + "px");
				RootPanel.get("SynonymsContainer").setSize(
						(.24 * width) + "px", event.getHeight() + "px");
				RootPanel.get("RolesContainer").setSize((.29 * width) + "px",
						event.getHeight() + "px");
				// setWidth((Window.getClientWidth()*.75)+"px");
			}
		});

		/*
		 * Add handlers
		 */
		New_Actor_Text.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				New_Actor_Text.setText("");
			}
		});
		New_Actor_Button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!New_Actor_Text.getText().isEmpty()) {
					Insert_Actor(New_Actor_Text.getText().replaceAll("\\s", "_"), User_name);
					// //////Window.alert(User_name);
					Insert_Actor_GUI(New_Actor_Text.getText().replaceAll("\\s", "_"));
					log += "<br/> "
							+ " - <br/>  ADDITION:  Actor with name      "
							+ New_Actor_Text.getText()
							+ " has been added<br/>  - <br/> ";
					New_Actor_Text.setText("New Actor");
				}

			}
		});

		New_Role_Text.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				New_Role_Text.setText("");
			}
		});
		New_Role_Button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!New_Role_Text.getText().isEmpty()
						&& Selected_Actor != null && !Selected_Actor.isEmpty()) {
					Insert_Role_GUI(New_Role_Text.getText().trim(), "", "");
					Updated_Roles.add(Roles.size() - 1);
					New_Actor_Text.setText("New Role");
					// String response =
					// Save_Role("http://104.198.76.143:8080/dictionary/saveRole/"
					// + User_name, New_Role_Text.getText(), Selected_Actor, "",
					// "");
					/*
					 * //////////Window.alert(response); if (response == "Success") {
					 * New_Role_message.setText("");
					 * Insert_Role_GUI(New_Role_Text.getText(), "", ""); log +=
					 * "<br/> " + " - <br/>  ADDITION:  Actor with name   " +
					 * Selected_Actor + "   Role with name " +
					 * New_Role_Text.getText() +
					 * " has been added<br/>  - <br/> ";
					 * New_Actor_Text.setText("New Role"); } else {
					 * New_Role_message.setText("Invalid Data");
					 * New_Role_message.addStyleName("error"); }
					 */
				}

			}
		});

		New_Synonym_Text.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				New_Synonym_Text.setText("");
			}
		});
		New_Synonym_Button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!New_Synonym_Text.getText().isEmpty()
						&& Selected_Actor != null && !Selected_Actor.isEmpty()) {
					// Insert_Synonym(New_Synonym_Text.getText(),
					// Selected_Actor);
					Insert_Synonym_GUI(New_Synonym_Text.getText().replaceAll("\\s", "_"));
					Updated_Synonyms.add(Synonyms.size() - 1);
					New_Synonym_Text.setText("New Name");
					// ////////Window.alert(Synonyms.size()+"");
					/*
					 * String response = Save_Synonym(
					 * "http://104.198.76.143:8080/dictionary/saveSynonym/" +
					 * User_name, New_Synonym_Text.getText(), Selected_Actor);
					 * if (response == "Success") {
					 * New_Synonym_message.setText("");
					 * Insert_Synonym_GUI(New_Synonym_Text.getText()); log +=
					 * "<br/> " + " - <br/>  ADDITION:  Actor  with name   " +
					 * Selected_Actor + "     Synonym with name " +
					 * New_Actor_Text.getText() +
					 * " has been added <br/>  - <br/> ";
					 * New_Synonym_Text.setText("New Name"); } else {
					 * New_Synonym_message.setText("Invalid Data");
					 * New_Synonym_message.addStyleName("error"); }
					 */

				}

			}
		});
		Log.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DialogBox dlg = new LogDialog();
				dlg.center();
				dlg.show();
				int height = Window.getClientHeight();
				int width = Window.getClientWidth();
				dlg.show();
				dlg.setAutoHideEnabled(false);
				dlg.setWidth("150%");
				dlg.setHeight("150%");
				dlg.setPopupPosition((width - 600) / 2, (height - 500) / 2);
			}
		});
		Log_out.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.reload();
			}
		});
		Dictionary_Preview.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				create_preview();
				DialogBox dlg = new Preview();
				dlg.center();
				dlg.show();
				int height = Window.getClientHeight();
				int width = Window.getClientWidth();
				dlg.show();
				dlg.setAutoHideEnabled(false);
				dlg.setWidth("150%");
				dlg.setHeight("100%");
				dlg.setPopupPosition((width - 600) / 2, (height - 500) / 2);
			}
		});
	}

	private PopupPanel showCustomDialog() {

		final PopupPanel dialog = new PopupPanel(false, true);

		// final DialogBox dialog = new DialogBox(true, true);

		// Set caption

		dialog.setTitle("Actions LOG");
		dialog.setStyleName("gwt-DialogBox");
		// Setcontent

		Label content = new Label(log);

		if (dialog.isAutoHideEnabled()) {

			dialog.setWidget(content);

		} else {

			HorizontalPanel vPanel = new HorizontalPanel();
			vPanel.setSpacing(2);

			vPanel.add(content);
			vPanel.add(new Label("<br/> " + " <br/> "));
			ScrollPanel sp = new ScrollPanel();
			sp.add(vPanel);
			HorizontalPanel hz = new HorizontalPanel();
			hz.add(sp);
			hz.add(new Button("Close", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					dialog.hide();

				}

			}));
			dialog.center();
			dialog.setModal(true);
			dialog.setGlassEnabled(false);
			dialog.setWidget(hz);

		}

		dialog.setSize((window_width / 2) + "px", "350px");
		// setPopupPositionAndShow(center);

		dialog.show();

		return dialog;

	}

	private void create_preview() {
		Preview = Selected_Actor;
		/*
		 *
		 *
		 * dddd
		 */
		for (int i = 0; i < Synonyms.size(); i++) {
			if (Deleted_Synonyms.indexOf(i) < 0) {
			Preview = Preview + " <br/>  + " + Synonyms.get(i).getText();
			}

		}
		for (int i = 0; i < Roles.size(); i++) {
			if (Deleted_Roles.indexOf(i) < 0) {
			Preview = Preview + "<br/> " + " &nbsp; &nbsp; &nbsp;	 ["
					+ Roles.get(i).getText();
			Preview = Preview + "  " + Sdate.get(i).getText() + "   -   ";
			Preview = Preview + "  " + Edate.get(i).getText() + "  ]";
			}
		}

	}

	private void Get_All_Actors(String history_option) {

		String methodname = "Get_All_Actors";
		greetingService.greetServer(history_option, "", methodname,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {

						// Show the RPC error message to the user

						// //////////Window.alert("Please try again later");
					}

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
						if (result.toString() == "failure") {
							// //////Window.alert("Please try again later");
						} else {

							try {
								Delete_Actors.clear();
								Actors.clear();
								Actor_vertical_Panel.clear();

								Button user = new Button("Actors");
								user.setEnabled(false);
								Actor_vertical_Panel.add(user);
								int width = RootPanel.get().getOffsetWidth();
								user.setWidth((width * .19) + "px");
								String[] splitArray = result.toString().split(
										",");
								for (int i = 0; i < splitArray.length; i++) {
									// FlowPanel Actor_header = new FlowPanel();
									Insert_Actor_GUI(splitArray[i]);
								}
							} catch (Exception ex) {
								//
							}

						}
					}

				});

	}
	private void Update_Actor(String user_name, String actor_name) {

		String methodname = "Update_Actor";
		greetingService.greetServer(user_name, "", methodname,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {

						// Show the RPC error message to the user

						// //////////Window.alert("Please try again later");
					}

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
						if (result.toString() == "failure") {
							// //////Window.alert("Please try again later");
						} else {

							try {

								}
							catch (Exception ex) {
								//
							}

						}
					}

				});

	}
	private void Insert_Actor_GUI(String Name) {
		if (!Name.isEmpty()) {
			Button user = new Button(Name);
			Button delete = new Button("X");
			Actor_DeleteHandler handler = new Actor_DeleteHandler();
			delete.addClickHandler(handler);
			delete.addKeyUpHandler(handler);
			Delete_Actors.add(delete);
			FlowPanel panel = new FlowPanel();
			panel.add(user);
			panel.add(delete);
			Actors.add(user);
			int width = RootPanel.get().getOffsetWidth();
			user.setWidth((width * .19) + "px");
			Actor_vertical_Panel.add(panel);
			ActorHandler actor_handler = new ActorHandler();
			user.addClickHandler(actor_handler);
			user.addKeyUpHandler(actor_handler);
		}

	}

	private void Insert_Role_GUI(String Name, String Sdate, String Edate) {
		if (!Name.isEmpty()) {
			// Updated_Roles.add(Updated_Roles.size());
			Button role = new Button(Name);
			Roles.add(role);
			TextBox sdate = new TextBox();
			TextBox edate = new TextBox();
			Button delete = new Button("X");
			Delete_Roles.add(delete);
			this.Sdate.add(sdate);
			this.Edate.add(edate);
			Role_DeleteHandler handler = new Role_DeleteHandler();
			delete.addClickHandler(handler);
			delete.addKeyUpHandler(handler);
			Role_UpdateHandler Uhandler = new Role_UpdateHandler();
			sdate.addClickHandler(Uhandler);
			sdate.addKeyUpHandler(Uhandler);
			edate.addClickHandler(Uhandler);
			edate.addKeyUpHandler(Uhandler);
			sdate.setText(Sdate);
			edate.setText(Edate);
			FlowPanel panel = new FlowPanel();
			panel.add(role);
			panel.add(sdate);
			panel.add(edate);
			panel.add(delete);
			Role_vertical_Panel.add(panel);
			int width = RootPanel.get().getOffsetWidth();
			sdate.setWidth((width * .06) + "px");
			edate.setWidth((width * .06) + "px");
			role.setWidth((width * .10) + "px");
			delete.setWidth((width * .02) + "px");

		}

	}

	private void Insert_Synonym_GUI(String Name) {
		if (!Name.isEmpty()) {
			Button synonym = new Button(Name);
			Synonyms.add(synonym);
			Button delete = new Button("X");
			Synonym_DeleteHandler handler = new Synonym_DeleteHandler();
			delete.addClickHandler(handler);
			delete.addKeyUpHandler(handler);
			Delete_Synonyms.add(delete);
			FlowPanel panel = new FlowPanel();
			panel.add(synonym);
			panel.add(delete);
			Synonym_vertical_Panel.add(panel);
			int width = RootPanel.get().getOffsetWidth();
			synonym.setWidth((width * .18) + "px");
			delete.setWidth((width * .02) + "px");
		}

	}

	private void Get_All_Roles(String name) {

		String methodname = "Get_All_Roles";

		greetingService.greetServer(name, "", methodname, new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {

				// Show the RPC error message to the user
				// //////////Window.alert("Please try again later");
			}

			@Override
			public void onSuccess(Object result) {
				// TODO Auto-generated method stub
				if (result.toString() == "failure") {
					// ////////////Window.alert("Please try again later");
				} else {

					try {
						Button role = new Button("Roles");
						Button sdate = new Button();
						Button edate = new Button();
						sdate.setText("Sdate");
						edate.setText("Edate");
						role.setEnabled(false);
						sdate.setEnabled(false);
						edate.setEnabled(false);
						FlowPanel panel = new FlowPanel();
						panel.add(role);
						panel.add(sdate);
						panel.add(edate);
						Role_vertical_Panel.add(panel);
						int width = RootPanel.get().getOffsetWidth();
						sdate.setWidth((width * .07) + "px");
						edate.setWidth((width * .07) + "px");
						role.setWidth((width * .10) + "px");
						String[] splitArray = result.toString()
								.replaceAll("null", " ").split("-");
						for (int i = 0; i < splitArray.length; i++) {
							String[] splitArray1 = splitArray[i].split(",");
							if (splitArray1[0] != null) {
								if (splitArray1[1] == "NULL" || splitArray1[1].equals(null)) {
									splitArray1[1] = "";
								}
								if (splitArray1[2] == "NULL" || splitArray1[2].equals(null)) {
									splitArray1[2] = "";
								}
								Insert_Role_GUI(splitArray1[0], splitArray1[1],
										splitArray1[2]);
							}

							// Roles.add(new Role(splitArray1[0],
							// splitArray1[1].substring(0, 15)));
						}
					} catch (Exception ex) {
						//
					}

				}
			}

		});

	}

	private void Get_All_Synonyms(String name) {

		String methodname = "Get_All_Synonyms";

		greetingService.greetServer(name, "", methodname, new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {

				// Show the RPC error message to the user
				// //////////Window.alert("Please try again later");
			}

			@Override
			public void onSuccess(Object result) {
				// TODO Auto-generated method stub
				if (result.toString() == "failure") {
					// ////////////Window.alert("Please try again later");
				} else {

					try {
						Button synonym = new Button("Alternative Names");
						synonym.setEnabled(false);
						FlowPanel panel = new FlowPanel();
						panel.add(synonym);
						Synonym_vertical_Panel.add(panel);
						int width = RootPanel.get().getOffsetWidth();
						synonym.setWidth((width * .18) + "px");
						String[] splitArray = result.toString().split("-");
						for (int i = 0; i < splitArray.length; i++) {
							if (splitArray[i] != null) {
								Insert_Synonym_GUI(splitArray[i]);
							}

						}
					} catch (Exception ex) {
						//
					}

				}
			}

		});

	}

	private void Insert_Actor(String name, String username) {

		String methodname = "Insert_Actor";

		greetingService.greetServer(name, username, methodname,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {

						// Show the RPC error message to the user

						// //////////Window.alert("Please try again later");
					}

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
						// //////Window.alert(result.toString());
						/*
						 * if (result.toString() == "failure") {
						 * ////////Window.alert("Please try again later"); } else {
						 * ////////Window.alert("Insertion done successfully"); }
						 */
						////Window.alert(result.toString());
					}

				});

	}

	private void Insert_Role(String role, String name) {

		String methodname = "Insert_Role";
		Save_Role(
				"http://104.198.76.143:8080/dictionary/saveRole/" + User_name,
				role, name, "", "");
		/*
		 * greetingService.greetServer(role, name, methodname, new
		 * AsyncCallback() {
		 *
		 * @Override public void onFailure(Throwable caught) {
		 *
		 * // Show the RPC error message to the user
		 *
		 * // //////////Window.alert("Please try again later"); }
		 *
		 * @Override public void onSuccess(Object result) { // TODO
		 * Auto-generated method stub if (result.toString() == "failure") { //
		 * //////////Window.alert("Please try again later"); } else { //
		 * //////////Window.alert("Insertion done successfully"); } }
		 *
		 * });
		 */

	}

	private void Update_Role(String role, String name, String Sdate,
			String Edate) {

		greetingService.greetServer(role, name, Sdate, Edate,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {

						// Show the RPC error message to the user

						// //////////Window.alert("Please try again later");
					}

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
						if (result.toString() == "failure") {
							// //////////Window.alert("Please try again later");
						} else {
							// //////////Window.alert("Insertion done successfully");
						}
					}

				});

	}

	private void Insert_Synonym(String Synonym, String name) {

		String methodname = "Insert_Synonym";
		Save_Synonym("http://104.198.76.143:8080/dictionary/saveSynonym/"
				+ User_name, Synonym, name);
		/*
		 * greetingService.greetServer(Synonym, name, methodname, new
		 * AsyncCallback() {
		 *
		 * @Override public void onFailure(Throwable caught) {
		 *
		 * // Show the RPC error message to the user
		 *
		 * // //////////Window.alert("Please try again later"); }
		 *
		 * @Override public void onSuccess(Object result) { // TODO
		 * Auto-generated method stub if (result.toString() == "failure") {
		 * //////////Window.alert("Please try again later"); } else {
		 * //////////Window.alert("Insertion done successfully"); } }
		 *
		 * });
		 */

	}

	private void Delete_Actor(String Actor_name, String name) {

		String methodname = "Delete_Actor";

		greetingService.greetServer(Actor_name, name, methodname,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {

						// Show the RPC error message to the user

						// //////////Window.alert("Please try again later");
					}

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
						// //////Window.alert(result.toString());
						/*
						 * if (result.toString() == "failure") {
						 * ////////Window.alert("Please try again later"); } else {
						 * ////////Window.alert("Deletion done successfully"); }
						 */
					}

				});

	}

	private void Delete_Role(String role, String name) {

		String methodname = "Delete_Role";

		greetingService.greetServer(role, name, methodname,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {

						// Show the RPC error message to the user

						// //////////Window.alert("Please try again later");
					}

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
						if (result.toString() == "failure") {
							// //////////Window.alert("Please try again later");
						} else {
							// //////////Window.alert("Deletion done successfully");
						}
					}

				});

	}

	private void Delete_Synonym(String Synonym, String name) {

		String methodname = "Delete_Synonym";

		greetingService.greetServer(Synonym, name, methodname,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {

						// Show the RPC error message to the user

						// //////////Window.alert("Please try again later");
					}

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
						if (result.toString() == "failure") {
							// //////Window.alert("Please try again later");
						} else {
							// //////Window.alert("Deletion done successfully");
						}
					}

				});

	}

	public void Get_All_Links(String Name) {
		String url = "https://kgsearch.googleapis.com/v1/entities:search?query="
				+ Name.replaceAll("\\s+", "+").toLowerCase()
				+ "&key=AIzaSyCpxmit-qyp_HFZfbPlwwXuVjfaiMenjqs&limit=1&indent=True";
		String test_url = "http://104.198.6.143:8080/dictionary/wikiForActorsV2/"
				+ Name;
		doGet(url);
		// Get_urls(test_url);
		Timer timeoutTimer = new Timer() {
			@Override
			public void run() {

				for (int i = 0; i < result1.size(); i++) {
					String name = result1.get(i).substring(
							result1.get(i).lastIndexOf("/") + 1);
					RadioButton radioButton1 = new RadioButton("radioGroup");

					Url_vertical_Panel.add(radioButton1);

					HTML link = new HTML("<a href=\"" + result1.get(i)
							+ "\" target=\"_blank\">  " + name.substring(0, 25)
							+ "</a>");
					// ////////////Window.alert(
					// "<a href=\""+result1.get(i)+"\" target=\"_blank\">Link Number "+(i+1)+"</a>");
					// HTML link = new
					// HTML("<a href=\""+"facebook.com"+"\" target=\"_blank\">Link Number "+i+"</a>");

					Url_vertical_Panel.add(link);
					// link.setWidth("80%");

				}
			}
		};
		timeoutTimer.schedule(500);

	}

	class ActorHandler implements ClickHandler, KeyUpHandler {

		// Fired when the user clicks on the sendButton.

		@Override
		public void onClick(ClickEvent event) {
			// sendNameToServer();

			current_Actor = (Button) event.getSource();
			load_data(current_Actor.getText());
			event.stopPropagation();
			event.preventDefault();

		}

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				// sendNameToServer();
				current_Actor = (Button) event.getSource();
				load_data(current_Actor.getText());
			}
			event.stopPropagation();
			event.preventDefault();

		}

		public void load_data(String Name) {
			Role_vertical_Panel.clear();
			Roles.clear();
			Delete_Roles.clear();
			Synonym_vertical_Panel.clear();
			Sdate.clear();
			Edate.clear();
			Synonyms.clear();
			Delete_Synonyms.clear();
			Selected_Actor = Name;
			Updated_Roles.clear();
			Updated_Synonyms.clear();
			Deleted_Roles.clear();
			Deleted_Synonyms.clear();
			Url_vertical_Panel.clear();
			Get_All_Links(Selected_Actor);
			Get_All_Roles(Selected_Actor);
			Get_All_Synonyms(Selected_Actor);
		}

	}

	class Shortcuts_Handler implements KeyUpHandler {

		// Fired when the user clicks on the sendButton.

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeKeyCode() == KeyCodes.KEY_A
					&& event.isAltKeyDown()) {
				// sendNameToServer();
			}
		}

	}

	class Role_UpdateHandler implements ClickHandler, KeyUpHandler {

		// Fired when the user clicks on the sendButton.

		@Override
		public void onClick(ClickEvent event) {
			// sendNameToServer();

			TextBox b = (TextBox) event.getSource();
			int index = -1;
			if (Sdate.indexOf(b) != -1) {
				index = Sdate.indexOf(b);
			} else {
				index = Edate.indexOf(b);
			}
			// //////////Window.alert(index+"    event");
			if (!Updated_Roles.contains(index)) {
				Updated_Roles.add(index);
			}

		}

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				// sendNameToServer();
				TextBox b = (TextBox) event.getSource();
				int index = -1;
				if (Sdate.indexOf(b) != -1) {
					index = Sdate.indexOf(b);
				} else {
					index = Edate.indexOf(b);
				}

				if (!Updated_Roles.contains(index)) {
					Updated_Roles.add(index);
				}
			}

		}

	}

	class Role_SubmitHandler implements ClickHandler, KeyUpHandler {

		// Fired when the user clicks on the sendButton.

		@Override
		public void onClick(ClickEvent event) {
			// sendNameToServer();
			submit();

		}

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
			submit();

		}

		public void submit() {
			boolean error = false;
			if (Selected_Actor != null && !Selected_Actor.isEmpty()) {


				try {
					for (int i = 0; i < Updated_Roles.size(); i++) {
						////Window.alert(Roles.get(index).getText().trim()+"");
						int index = Updated_Roles.get(i);
						String sdate = Sdate.get(index).getText();
						String edate = Edate.get(index).getText();
						String role = Roles.get(index).getText().trim();
						StringBuilder s_date = new StringBuilder();

						for (int l = 0; l < sdate.length()-1; l +=2) {
							s_date.append(sdate.substring(l, l + 2));
							if (s_date.length()!= sdate.length()+2) {
								s_date.append("/");
							}

						}
						StringBuilder e_date = new StringBuilder();

						for (int l = 0; l < edate.length()-1; l +=2) {
							e_date.append(edate.substring(l, l + 2));
							if (e_date.length()!= edate.length()+2) {
								e_date.append("/");
							}
						}
						// //////////Window.alert(index + "    "+ sdate);
						if (new Date_valedation().isValid(sdate, edate)) {
							// //////////Window.alert(role);
							sdate = s_date.toString();
							if (s_date.length() == 0) {
								sdate = "88/01/01";
							}
							if (e_date.length() < 8) {
								edate = sdate;
							}
							else {
								edate = e_date.toString();
							}

							if (edate.equals(null) || edate == "NULL") {
								edate = "";
							}
							if (sdate.equals(null) || sdate == "NULL") {
								sdate = "";
							}
							if (s_date.length() == 0) {
							sdate = "";
						}
						if (e_date.length() < 8) {
							edate = "";
						}
							final String ts_date = sdate;
							final String te_date = edate;
							final String t_role = role;
							try {
								/*Timer t = new Timer() {
								      @Override
								      public void run() {*/
								    	  String response = Save_Role(
													"http://104.198.76.143:8080/dictionary/saveRole/"
															+ User_name, t_role,
													Selected_Actor, ts_date, te_date);
								    	  Update_Role(t_role, Selected_Actor, ts_date, te_date);
								    	  Update_Actor(User_name,Selected_Actor);
								     /* }
								    };

								    // Schedule the timer to run once in 5 seconds.
								    t.schedule(1100);*/

							} catch (Exception e) {
								// TODO: handle exception
								//Update_Role(role, Selected_Actor, sdate, edate);
							}


						//	//Window.alert(response + "  role   " + role + "   sdate   " + sdate);
							////Window.alert("role   " + role + "   sdate   " + sdate + );
							/*if (s_date.length() == 0) {
								sdate = "";
							}
							if (e_date.length() < 8) {
								edate = "";
							}*/


							log += "<br/> "
									+ " - <br/>  UPDATE:  Role  "
									+ role
									+ "   Start_Date     "
									+ sdate
									+ "    End_Date      "
									+ edate
									+ " has been updated for Actor  with Name         "
									+ Selected_Actor + "<br/> " + " <br/> ";
						} else {
							int height = Sdate.get(index).getOffsetHeight();
							int width = Sdate.get(index).getOffsetWidth();
							Sdate.get(index).setStyleName("error");
							Edate.get(index).setStyleName("error");
							Sdate.get(index).setSize((width - 1) + "px",
									height + "px");
							Edate.get(index).setSize((width - 1) + "px",
									height + "px");
							error = true;
							//////Window.alert("Dates should have the following format YYMMDD");
						}

					}
					for (int i = 0; i < Updated_Synonyms.size(); i++) {
						int index = Updated_Synonyms.get(i);
						String Synonym = Synonyms.get(index).getText().replaceAll("\\s", "_");
						////Window.alert(Synonym);
						Insert_Synonym(Synonym, Selected_Actor);
						String response = Save_Synonym(
								"http://104.198.76.143:8080/dictionary/saveSynonym/"
										+ User_name, Synonym, Selected_Actor);
						////////Window.alert(response);
						log += "<br/> "
								+ " - <br/>  ADDITION:  Synonym  "
								+ Synonym
								+ " has been added for Actor  with Name         "
								+ Selected_Actor + "<br/> " + " <br/> ";
					}
				} catch (Exception e) {// ////////Window.alert(e.getMessage());

				}
				Updated_Roles.clear();
				Updated_Synonyms.clear();
				Deleted_Roles.clear();
				Deleted_Synonyms.clear();
				if (!error) {
					Role_vertical_Panel.clear();
					Roles.clear();
					Delete_Roles.clear();
					Synonym_vertical_Panel.clear();
					Sdate.clear();
					Edate.clear();
					Synonyms.clear();
					Delete_Synonyms.clear();
					current_Actor.setStyleName("submitted");
				}
			}

		}

	}

	class Role_DeleteHandler implements ClickHandler, KeyUpHandler {

		// Fired when the user clicks on the sendButton.

		@Override
		public void onClick(ClickEvent event) {
			// sendNameToServer();

			Button b = (Button) event.getSource();
			int index = Delete_Roles.indexOf(b);
			////Window.alert("       " + index + "     " + Roles.get(index).getText());
			Delete(index, Roles.get(index).getText());

		}

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
			/*if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				// sendNameToServer();
				Button b = (Button) event.getSource();
				int index = Delete_Roles.indexOf(b);
				Delete(index, Roles.get(index).getText());
			}*/

		}

		public void Delete(int index, String role) {
			Delete_Roles.get(index).addStyleName("error");
			Role_vertical_Panel.getWidget(index + 1).setVisible(false);
			Deleted_Roles.add(index);
			//Role_vertical_Panel.getWidget(index + 1).addStyleName("error");
			Delete_Role(role, Selected_Actor);
			/*Delete_Roles.remove(index);
			Roles.remove(index);
			Sdate.remove(index);
			Edate.remove(index);
			//Delete_Roles.get(index).setText("U");
			//Delete_Roles.get(index).addStyleName("error");//.setStyleName("error");
			Delete_Roles.remove(index);*/
			//.setStyleName("error");
			//Updated_Roles.add(index);


			log += "<br/> " + " - <br/>  DELETION:  Role  " + role
					+ " has been deleted for Actor  with Name         "
					+ Selected_Actor + "<br/> " + " <br/> ";
			// Selected_Actor = Name;

		}

	}

	class Actor_DeleteHandler implements ClickHandler, KeyUpHandler {

		// Fired when the user clicks on the sendButton.

		@Override
		public void onClick(ClickEvent event) {
			// sendNameToServer();

			Button b = (Button) event.getSource();
			int index = Delete_Actors.indexOf(b);
			Delete(index, Actors.get(index).getText());

		}

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				// sendNameToServer();
				Button b = (Button) event.getSource();
				int index = Delete_Actors.indexOf(b);
				Delete(index, Actors.get(index).getText());
			}

		}

		public void Delete(int index, String Name) {
			delete_text = "Are you sure you want to delete \" " + Name + " \"";
			delete_choice = Name;
			delete_index = index;
			DialogBox dlg = new Delete_Verification();
			dlg.center();
			dlg.show();
			dlg.setAutoHideEnabled(false);

		}

	}

	class Synonym_DeleteHandler implements ClickHandler, KeyUpHandler {

		// Fired when the user clicks on the sendButton.

		@Override
		public void onClick(ClickEvent event) {
			// sendNameToServer();

			Button b = (Button) event.getSource();
			int index = Delete_Synonyms.indexOf(b);
			Delete(index, Synonyms.get(index).getText());

		}

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
			/*if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				// sendNameToServer();
				Button b = (Button) event.getSource();
				int index = Delete_Synonyms.indexOf(b);
				Delete(index, Synonyms.get(index).getText());
			}*/

		}

		public void Delete(int index, String Synonym) {
			Delete_Synonyms.get(index).addStyleName("error");
			Synonym_vertical_Panel.getWidget(index + 1).setVisible(false);
			/*Synonym_vertical_Panel.remove(index + 1);
			Delete_Synonyms.remove(index);
			Synonyms.remove(index);
			Updated_Synonyms.remove(new Integer(index));*/
			Deleted_Synonyms.add(index);
			Delete_Synonym(Synonym, Selected_Actor);
			// Selected_Actor = Name;
			log += "<br/> " + " - <br/>  DELETION:  Synonym  " + Synonym
					+ " has been deleted for Actor  with Name         "
					+ Selected_Actor + "<br/> " + " <br/> ";

		}

	}

	public static final int STATUS_CODE_OK = 200;
	int index = 0;
	List<String> result1 = new ArrayList<String>(20);

	public void doGet(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					// Code omitted for clarity

				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					// Code omitted for clarity
					// result1 =
					// response.getText();//.getHeader("itemListElement");

					JSONValue jsonValue;
					JSONArray jsonArray;
					JSONObject jsonObject;
					JSONString jsonString;
					jsonValue = JSONParser.parseStrict(response.getText());
					// parseStrict is available in GWT >=2.1
					// But without it, GWT is just internally calling eval()
					// which is strongly discouraged for untrusted sources

					if ((jsonObject = jsonValue.isObject()) == null) {
						// //////////Window.alert("Error parsing the JSON 1");
						// Possibilites: error during download,
						// someone trying to break the application, etc.
					}

					jsonValue = jsonObject.get("itemListElement"); // Actually,
																	// this
																	// needs
					// a null check too
					if ((jsonArray = jsonValue.isArray()) == null) {
						// //////////Window.alert("Error parsing the JSON 2 ");
					}
					int count = 0;
					int i = 0;
					result1 = new ArrayList<String>(20);
					while (i < 200 && count < 35) {
						jsonValue = jsonArray.get(i++);
						if ((jsonObject = jsonValue.isObject()) == null) {
							// //////////Window.alert("Error parsing the JSON 3 ");
						}

						jsonValue = jsonObject.get("result");
						if ((jsonObject = jsonValue.isObject()) == null) {
							// //////////Window.alert("Error parsing the JSON 4 ");
						}
						// ////////////Window.alert();
						String result = jsonObject.get("@type").toString();
						if (result.contains("Person")) {
							try {
								jsonValue = jsonObject
										.get("detailedDescription");
								if ((jsonObject = jsonValue.isObject()) == null) {
									continue;
								}
							} catch (Exception e) {
								// TODO: handle exception
								continue;
							}

							if (jsonObject.toString() == null) {
								// your code here.
							} else {
								if (jsonObject.get("url").toString() == null
										|| jsonObject.get("url").toString()
												.contains("\"")) {
									// your code here.
									try {
										result = jsonObject.get("url")
												.toString();
										result1.add(result.substring(
												result.indexOf("\"") + 1,
												result.lastIndexOf("\"")));// .substring(result.indexOf("\"")+1,
																			// result.lastIndexOf("\""));
										count++;// ////////////Window.alert(result.substring(result.indexOf("\"")+1,
												// result.lastIndexOf("\"")));
									} catch (Exception e) {
										// TODO: handle exception
										// //////////Window.alert(e.getMessage());
									}
								}

							}

						}
					}
					// ////////////Window.alert( jsonObject.get("url")
					// .toString());}//
					/*
					 * JSONValue jsonValue =
					 * JSONParser.parse(response.getText());
					 * com.google.gwt.json.client.JSONArray x =
					 * jsonValue.isArray();
					 */
					// parseStrict is available in GWT >=2.1
					// But without it, GWT is just internally calling eval()
					// which is strongly discouraged for untrusted sources

				}

			});
		} catch (RequestException e) {
			// Code omitted for clarity
		}
	}

	public void Get_Actors(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		builder.setHeader("secret-key", "mySecretKey");

		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					// Code omitted for clarity

				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					// Code omitted for clarity
					// result1 =
					// response.getText();//.getHeader("itemListElement");
					try {
						Button user = new Button("Actors");
						user.setEnabled(false);
						Actor_vertical_Panel.add(user);
						int width = RootPanel.get().getOffsetWidth();
						user.setWidth((width * .19) + "px");
						String results = response.getText();
						results = results.substring(1, results.length() - 1);
						String[] splitArray = results.toString().split(",");
						for (int i = 0; i < splitArray.length; i++) {
							// FlowPanel Actor_header = new FlowPanel();
							Insert_Actor_GUI(splitArray[i].substring(1,
									splitArray[i].length() - 1));
						}

					} catch (Exception ex) {
						//
					}
					// //////////Window.alert(response.getText());
				}

			});
		} catch (RequestException e) {
			// Code omitted for clarity
		}
	}

	String response_text = "";

	public String Save_Synonym(String url, String synonym_name,
			String actor_name) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("secret-key", "mySecretKey");
		builder.setHeader("content-type", "application/json");
		JSONObject message = new JSONObject();
		message.put("synonym", new JSONString(synonym_name));
		message.put("name", new JSONString(actor_name));
		// //////////Window.alert(message.toString());
		try {
			Request response = builder.sendRequest(message.toString(),
					new RequestCallback() {
						@Override
						public void onError(Request request, Throwable exception) {
							// Code omitted for clarity
							response_text = "Error";
						}

						@Override
						public void onResponseReceived(Request request,
								Response response) {
							// Code omitted for clarity
							// result1 =
							// response.getText();//.getHeader("itemListElement");
							response_text = response.getText();
							////Window.alert(response_text + "   Save_Synonym");
						}

					});
		} catch (RequestException e) {
			// Code omitted for clarity
		}
		return response_text;
	}

	public String Save_Role(String url, String role_name, String actor_name,
			String start_date, String end_date) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		builder.setHeader("secret-key", "mySecretKey");
		builder.setHeader("content-type", "application/json");
		JSONObject message = new JSONObject();
		message.put("role", new JSONString(role_name));
		message.put("key", new JSONString(actor_name));
		message.put("start", new JSONString(start_date));
		message.put("end", new JSONString(end_date));
		// //////////Window.alert(message.toString());
		try {
			Request response = builder.sendRequest(message.toString(),
					new RequestCallback() {
						@Override
						public void onError(Request request, Throwable exception) {
							// Code omitted for clarity
							response_text = "Error";
						}

						@Override
						public void onResponseReceived(Request request,
								Response response) {
							// Code omitted for clarity
							// result1 =
							// response.getText();//.getHeader("itemListElement");
							response_text = response.getText();
							//Window.alert("    Save Role    " + response_text);
						}

					});
		} catch (RequestException e) {
			// Code omitted for clarity
		}
		return response_text;
	}


	public void Get_dict() {
		String url = "http://104.198.76.143:8080/dictionary/downloadDictionary";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		builder.setHeader("secret-key", "mySecretKey");

		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					// Code omitted for clarity

				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {


				}

			});
		} catch (RequestException e) {
			// Code omitted for clarity
		}
	}

	public void Get_urls(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		builder.setHeader("secret-key", "mySecretKey");

		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					// Code omitted for clarity

				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					result1 = new ArrayList<String>(20);
					String respons = response.getText();
					//////Window.alert(respons);
					respons = respons.substring(respons.indexOf("[") + 1);
					respons = respons.substring(0, respons.indexOf("]"));
					respons.replaceAll("\"", "");
					String[] urls = respons.split(",");
					for (int i = 0; i < urls.length; i++) {
						//////Window.alert(urls[i]);
						result1.add(urls[i]);
					}

				}

			});
		} catch (RequestException e) {
			// Code omitted for clarity
		}
	}

	class Preview extends DialogBox implements ClickHandler {
		public Preview() {
			super(true);
			setText("Dictionary Preview");
			Button closeButton = new Button("Close", this);
			HTML log_text = new HTML(Preview);
			ScrollPanel pnlScroll = new ScrollPanel(log_text);
			pnlScroll.setSize("600px", "420px");
			DockPanel dock = new DockPanel();
			dock.setSpacing(6);
			/*
			 * Image image = new Image(); image.setUrl(
			 * "https://www.javacodegeeks.com/wp-content/uploads/2012/12/JavaCodeGeeks-logo.png"
			 * );
			 */
			dock.add(pnlScroll, DockPanel.CENTER);
			dock.add(closeButton, DockPanel.SOUTH);

			dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_CENTER);
			dock.setWidth("100%");
			setWidget(dock);
		}

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}

	}

	class Delete_Verification extends DialogBox implements ClickHandler {
		public Delete_Verification() {
			super(true);
			setText("Delete Verification");
			Button yes = new Button("Yes", this);
			Button no = new Button("No", this);
			HTML log_text = new HTML(delete_text);
			ScrollPanel pnlScroll = new ScrollPanel(log_text);
			pnlScroll.setSize("300px", "80px");
			DockPanel dock = new DockPanel();
			dock.setSpacing(6);
			/*
			 * Image image = new Image(); image.setUrl(
			 * "https://www.javacodegeeks.com/wp-content/uploads/2012/12/JavaCodeGeeks-logo.png"
			 * );
			 */
			HorizontalPanel buttons = new HorizontalPanel();
			buttons.add(yes);
			buttons.add(no);
			buttons.setSpacing(6);
			dock.add(pnlScroll, DockPanel.CENTER);
			dock.add(buttons, DockPanel.SOUTH);
			yes.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Actor_vertical_Panel.remove(delete_index + 1);
					Delete_Actors.remove(delete_index);
					Actors.remove(delete_index);
					// //////Window.alert(Name.replaceAll("\\s+",""));
					Delete_Actor(delete_choice, "");
				}

			});
			no.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			dock.setCellHorizontalAlignment(buttons, DockPanel.ALIGN_CENTER);
			dock.setWidth("100%");
			setWidget(dock);
		}

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}

	}

	class LogDialog extends DialogBox implements ClickHandler {
		public LogDialog() {
			super(true);
			setText("Log");
			Button closeButton = new Button("Close", this);
			HTML log_text = new HTML(log);
			ScrollPanel pnlScroll = new ScrollPanel(log_text);
			pnlScroll.setSize("600px", "420px");
			DockPanel dock = new DockPanel();
			dock.setSpacing(6);
			/*
			 * Image image = new Image(); image.setUrl(
			 * "https://www.javacodegeeks.com/wp-content/uploads/2012/12/JavaCodeGeeks-logo.png"
			 * );
			 */
			dock.add(pnlScroll, DockPanel.CENTER);
			dock.add(closeButton, DockPanel.SOUTH);

			dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_CENTER);
			dock.setWidth("100%");
			setWidget(dock);
		}

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}

	}

	class Login extends DialogBox implements ClickHandler {
		public Login() {
			super(true);
			setText("User Authentication");
			HTML msg = new HTML("User Authentication", true);

			DockPanel dock = new DockPanel();
			dock.setSpacing(6);
			VerticalPanel signin = new VerticalPanel();
			final TextBox user_name = new TextBox();
			HTML user_name_panel = new HTML("Username", true);
			final PasswordTextBox pass = new PasswordTextBox();
			HTML Password_panel = new HTML("Password ", true);

			login.addDomHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Boolean special_cahr = user_name.getText().matches("[A-Za-z0-9 ]*");
					if (special_cahr){
					String ms = login(
							"http://104.198.76.143:8080/dictionary/loginUser",
							user_name.getText(), convertPassMd5(pass.getText()));
					}
					else {
						login_msg.setText("Username cannot contain special character");
						login_msg.setStyleName("MessageText");
					}
					////////Window.alert(login_response_text);
					/*if (!user_name.getText().matches("[^A-Za-z0-9 ]")) {


					if (ms == "Valid User") {
						hide();
						// Get_Actors("http://104.198.76.143:8080/dictionary/actors/"
						// + User_name);
						Get_All_Actors("IS NULL");
					} else if (ms == "1") {
						for (int i = 0; i < 10; i++) {
							ms = login(
									"http://104.198.76.143:8080/dictionary/loginUser",
									user_name.getText(),
									convertPassMd5(pass.getText()));
							//Window.getTitle();
							user_name.setText(user_name.getText());
							if (ms == "Valid User") {
								hide();
								// Get_Actors("http://104.198.76.143:8080/dictionary/actors/"
								// + User_name);
								Get_All_Actors("IS NULL");
								break;
							}
						}

						if (ms == "Invalid Credentials") {
							login_msg.setText(ms);
							login_msg.setStyleName("MessageText");
						}
						else if(ms != "Valid User"){
							//////Window.alert(ms);
							login_msg.setText("Please try again later");
							login_msg.setStyleName("MessageText");
						}

						else {
							hide();
							// Get_Actors("http://104.198.76.143:8080/dictionary/actors/"
							// + User_name);
							Get_All_Actors("IS NULL");
						}

					}
					}
					else {
						login_msg.setText("Username cannot contain special character");
						login_msg.setStyleName("MessageText");
					}*/
				}
			}, ClickEvent.getType());
			/*
			 * login.addClickHandler(new ClickHandler() {
			 *
			 * @Override public void onClick(ClickEvent event) { String ms =
			 * login("http://104.198.76.143:8080/dictionary/loginUser",
			 * user_name.getText(), convertPassMd5(pass.getText())); User_name =
			 * user_name.getText(); if (ms == "Valid User") { hide();
			 * //Get_Actors("http://104.198.76.143:8080/dictionary/actors/" +
			 * User_name); Get_All_Actors("IS NULL"); } else { if(ms != "1") {
			 * login_msg.setText(ms); login_msg.setStyleName("MessageText"); } }
			 * } });
			 */

			signin.add(login_msg);
			signin.add(user_name_panel);
			signin.add(user_name);
			signin.add(Password_panel);
			signin.add(pass);
			signin.add(login);
			signin.setSpacing(20);

			VerticalPanel signup = new VerticalPanel();
			final TextBox signup_first_name = new TextBox();
			HTML signup_user_first_name = new HTML("First Name ", true);
			final TextBox signup_last_name = new TextBox();
			HTML signup_Password_last_name = new HTML("Last Name ", true);
			final TextBox signup_user_name = new TextBox();
			HTML signup_user_name_panel = new HTML(
					"Username", true);
			final PasswordTextBox signup_pass = new PasswordTextBox();
			HTML signup_Password_panel = new HTML("Password ", true);

			Button signup_login = new Button("Sign Up");
			signup_login.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					Boolean special_cahr = signup_user_name.getText().matches("[A-Za-z0-9 ]*");
					if (special_cahr)

					{String message = New_user(
							"http://104.198.76.143:8080/dictionary/registerUser",
							signup_first_name.getText(),
							signup_last_name.getText(),
							signup_user_name.getText(),
							convertPassMd5(signup_pass.getText()));
					;

					}
					else
					{
						signup_msg
						.setText("Username cannot contain special character");
						signup_msg.setStyleName("MessageText");
					}


					// //////Window.alert(message);

				}

			});

			signup.add(signup_msg);
			signup.add(signup_user_first_name);
			signup.add(signup_first_name);
			signup.add(signup_Password_last_name);
			signup.add(signup_last_name);
			signup.add(signup_user_name_panel);
			signup.add(signup_user_name);
			signup.add(signup_Password_panel);
			signup.add(signup_pass);
			signup.add(signup_login);
			signup.setSpacing(20);

			HorizontalPanel authentication = new HorizontalPanel();
			authentication.add(signin);
			authentication.add(signup);
			authentication.setSpacing(50);
			authentication.setCellHorizontalAlignment(signup,
					HorizontalPanel.ALIGN_CENTER);
			authentication.setCellHorizontalAlignment(signin,
					HorizontalPanel.ALIGN_CENTER);
			dock.add(authentication, DockPanel.CENTER);

			// dock.setCellHorizontalAlignment(closeButton,
			// DockPanel.ALIGN_CENTER);
			setWidget(dock);
		}

		String response_text = "1";

		public String New_user(String url, String first_name, String last_name,
				String user_name, String pass) {
			RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
					url);
			builder.setHeader("secret-key", "mySecretKey");
			builder.setHeader("content-type", "application/json");
			JSONObject message = new JSONObject();
			message.put("username", new JSONString(user_name));
			message.put("hashedPassword", new JSONString(pass));
			message.put("firstName", new JSONString(first_name));
			message.put("lastName", new JSONString(last_name));
			// //////////Window.alert(message.toString());
			try {
				Request response = builder.sendRequest(message.toString(),
						new RequestCallback() {
							@Override
							public void onError(Request request,
									Throwable exception) {
								// Code omitted for clarity
								response_text = "Error";
							}

							@Override
							public void onResponseReceived(Request request,
									Response response) {
								// Code omitted for clarity
								// result1 =
								// response.getText();//.getHeader("itemListElement");
								response_text = response.getText();
									if (response_text == "registration Successful") {
										signup_msg.setText("Registration Successful");

									} else {
										//////Window.alert(message);
										if (response_text == "Username is taken") {
											signup_msg.setText(response_text);

										}

										else {
											signup_msg
													.setText("Please try again later");
										}
										signup_msg.setStyleName("MessageText");
									}

							}

						});
			} catch (RequestException e) {
				// Code omitted for clarity
			}
			return response_text;
		}



		public String login(String url, final String user_name, String pass) {
			RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
					url);
			builder.setHeader("secret-key", "mySecretKey");
			builder.setHeader("content-type", "application/json");
			JSONObject message = new JSONObject();
			message.put("username", new JSONString(user_name));
			message.put("hashedPassword", new JSONString(pass));
			// //////////Window.alert(message.toString());
			try {
				Request response = builder.sendRequest(message.toString(),
						new RequestCallback() {
							@Override
							public void onError(Request request,
									Throwable exception) {
								// Code omitted for clarity
								login_response_text = "Error";

							}

							@Override
							public void onResponseReceived(Request request,
									Response response) {
								// Code omitted for clarity
								// result1 =
								// response.getText();//.getHeader("itemListElement");
								login_response_text = response.getText();
								User_name = user_name;
								dlg.hide();
								Get_All_Actors("IS NULL");
								//////Window.alert(login_response_text);
								if (login_response_text == "Invalid Credentials") {
									login_msg.setText(login_response_text);
									login_msg.setStyleName("MessageText");
								}
								else if(login_response_text != "Valid User"){
									//////Window.alert(ms);
									login_msg.setText("Please try again later");
									login_msg.setStyleName("MessageText");
								}

								else {
								dlg.hide();
								User_name = user_name;
									// Get_Actors("http://104.198.76.143:8080/dictionary/actors/"
									// + User_name);
									Get_All_Actors("IS NULL");
									Get_dict();
									/*
									 *
									 *
									 *
									 * ddd
									 */
								}
								}

						});
			} catch (RequestException e) {
				// Code omitted for clarity
			}
			return login_response_text;
		}

		public String convertPassMd5(String pass) {
			String password = null;
			MessageDigest mdEnc;
			try {
				mdEnc = MessageDigest.getInstance("MD5");
				mdEnc.update(pass.getBytes(), 0, pass.length());
				pass = new BigInteger(1, mdEnc.digest()).toString(16);
				while (pass.length() < 32) {
					pass = "0" + pass;
				}
				password = pass;
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			return password;
		}

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}

		@Override
		protected void beginDragging(MouseDownEvent e) {
			e.preventDefault();
		}

	}
}
