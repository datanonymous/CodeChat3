package ko.alex.enigmachat3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;



public class Chat_Room extends AppCompatActivity {

    // https://code.tutsplus.com/tutorials/how-to-create-an-android-chat-app-using-firebase--cms-27397

//    private Button btn_send_msg;
//    private EditText input_msg;
//    private TextView chat_conversation;

    private String user_name, room_name;

    private DatabaseReference root;
    private String temp_key;

    // Instantiate encrypt and decrypt classes
    private Encrypt encryptedText = new Encrypt();
    private Decrypt decryptedText = new Decrypt();

    private FirebaseListAdapter<ChatMessage> adapter;

    private TextView messageText;
    private TextView messageUser;
    private TextView messageTime;

    private ListView listOfMessages;
    private ChatMessage chatItemSelected;
    private String itemSelected;

    private FloatingActionButton fab;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState){ // removed @Nullable Bundle savedInstanceState
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

//        btn_send_msg = findViewById(R.id.btn_send);
//        input_msg = findViewById(R.id.msg_input);
//        chat_conversation = findViewById(R.id.textView);

        listOfMessages = findViewById(R.id.list_of_messages);
        fab = findViewById(R.id.fab);
        input = findViewById(R.id.input);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle(" Room - " + room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        displayChatMessages();

//        btn_send_msg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Map<String,Object> map = new HashMap<String,Object>();
//                temp_key = root.push().getKey();
//                root.updateChildren(map);
//
//                DatabaseReference message_root = root.child(temp_key);
//                Map<String,Object> map2 = new HashMap<String,Object>();
//                map2.put("name",user_name);
//
//                String codedText;
//
//                codedText = encryptedText.Encrypt(input_msg.getText().toString());
//
//                map2.put("msg",codedText);
//
//                message_root.updateChildren(map2);
//                input_msg.setText("");
//            }
//        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                displayChatMessages();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                displayChatMessages();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    } // end of onCreate method



    private void displayChatMessages() {

        ListView listOfMessages = findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, root) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                messageText = v.findViewById(R.id.message_text);
                messageUser = v.findViewById(R.id.message_user);
                messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

        listOfMessages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) { // void rather than boolean, https://stackoverflow.com/questions/14340579/android-removing-item-from-listview-on-long-click

                chatItemSelected = adapter.getItem(position); // get the position of https://stackoverflow.com/questions/42073899/how-to-display-the-keys-from-a-firebase-database-with-android
                itemSelected = chatItemSelected.getMessageText();
                decryptedText = new Decrypt(); // DECRYPTED OUTPUT IS THE INSTANTIATED CLASS.  USING THE INSTANTIATED CLASS, CALL DECRYPT METHOD ON TEXT SELECTED
                Toast.makeText(getApplicationContext(),"DECRYPTED OUTPUT: " +  decryptedText.Decrypt(itemSelected),Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }






    public void buttonClicked(View view){

        String codedText = "";
        encryptedText = new Encrypt();
        codedText = encryptedText.Encrypt(input.getText().toString());

        if(codedText == "") { // This is to prevent blank information for getting entered
            Toast.makeText(this, "Please enter text into input field...", Toast.LENGTH_SHORT).show();
        } else{

            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            root.push().setValue(new ChatMessage(codedText, user_name));

            // Clear the input
            input.setText("");
        }

    }





//    private String chat_msg, chat_user_name;
//
//    private void append_chat_conversation(DataSnapshot dataSnapshot) {
//        Iterator i = dataSnapshot.getChildren().iterator();
//        while(i.hasNext()){
//            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
//            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();
//
//            chat_conversation.append(chat_user_name + " : " + chat_msg + " \n");
//        }
//    }


} // end of Chat_Room class
