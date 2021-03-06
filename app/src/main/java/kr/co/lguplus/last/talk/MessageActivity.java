package kr.co.lguplus.last.talk;

import java.util.ArrayList;
import java.util.Random;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import kr.co.lguplus.last.AlarmActivity;
import kr.co.lguplus.last.Application;
import kr.co.lguplus.last.PayActivity;
import kr.co.lguplus.last.ProfileActivity;
import kr.co.lguplus.last.R;
import kr.co.lguplus.last.search.SearchDemoActivity;
import kr.co.lguplus.last.StudyListActivity;

/**
 * MessageActivity is a main Activity to show a ListView containing Message items
 * 
 * @author Adil Soomro
 *
 */
public class MessageActivity extends ListActivity {
	/** Called when the activity is first created. */

	ArrayList<Message> messages;
	Application.AwesomeAdapter adapter;
	EditText text;
	static Random rand = new Random();	
	static String sender;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		text = (EditText) this.findViewById(R.id.text);
		
		sender = Utility.sender[rand.nextInt( Utility.sender.length-1)];
		this.setTitle(sender);

		/*It's menu that is shown by Profile*/
		ImageButton searchbtn = (ImageButton) findViewById(R.id.talk_search_btn);
		ImageButton alarmbtn = (ImageButton) findViewById(R.id.talk_alarm_btn);
		ImageButton studybtn = (ImageButton) findViewById(R.id.talk_study_btn);
		ImageButton billbtn = (ImageButton) findViewById(R.id.talk_pay_btn);
		ImageButton talkbtn = (ImageButton) findViewById(R.id.talk_talk_btn);
		ImageButton logobtn = (ImageButton) findViewById(R.id.talk_studyplus_logo);

		logobtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MessageActivity.this, ProfileActivity.class);
				startActivity(intent);

			}
		});
		talkbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MessageActivity.this, MessageActivity.class);
				startActivity(intent);

			}
		});
		searchbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MessageActivity.this, SearchDemoActivity.class);
				startActivity(intent);

			}
		});
		alarmbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MessageActivity.this, AlarmActivity.class);
				startActivity(intent);

			}
		});
		studybtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MessageActivity.this, StudyListActivity.class);
				startActivity(intent);

			}
		});
		billbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MessageActivity.this, PayActivity.class);
				startActivity(intent);

			}
		});

/***************************************************************************************/

		messages = new ArrayList<Message>();

	/*	messages.add(new Message("Hello", false));
		messages.add(new Message("Hi!", true));
		messages.add(new Message("Wassup??", false));
		messages.add(new Message("nothing much, working on speech bubbles.", true));
		messages.add(new Message("you say!", true));
		messages.add(new Message("oh thats great. how are you showing them", false));*/
		

		adapter = new Application.AwesomeAdapter(this, messages);
		setListAdapter(adapter);/*
		addNewMessage(new Message("mmm, well, using 9 patches png to show them.", true));*/
	}
	public void sendMessage(View v)
	{
		String newMessage = text.getText().toString().trim(); 
		if(newMessage.length() > 0)
		{
			text.setText("");
			addNewMessage(new Message(newMessage, true));
			new SendMessage().execute();
		}
	}
	private class SendMessage extends AsyncTask<Void, String, String>
	{
		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(2000); //simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.publishProgress(String.format("%s started writing", sender));
			try {
				Thread.sleep(2000); //simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.publishProgress(String.format("%s has entered text", sender));
			try {
				Thread.sleep(3000);//simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			return Utility.messages[rand.nextInt(Utility.messages.length-1)];
			
			
		}
		@Override
		public void onProgressUpdate(String... v) {
			
			if(messages.get(messages.size()-1).isStatusMessage)//check wether we have already added a status message
			{
				messages.get(messages.size()-1).setMessage(v[0]); //update the status for that
				adapter.notifyDataSetChanged(); 
				getListView().setSelection(messages.size()-1);
			}
			else{
				addNewMessage(new Message(true,v[0])); //add new message, if there is no existing status message
			}
		}
		@Override
		protected void onPostExecute(String text) {
			if(messages.get(messages.size()-1).isStatusMessage)//check if there is any status message, now remove it.
			{
				messages.remove(messages.size()-1);
			}
			
			addNewMessage(new Message(text, false)); // add the orignal message from server.
		}
		

	}
	void addNewMessage(Message m)
	{
		messages.add(m);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
	}
}