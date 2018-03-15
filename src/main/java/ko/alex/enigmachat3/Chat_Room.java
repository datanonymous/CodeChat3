package ko.alex.enigmachat3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
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
    //private String temp_key;

    // Instantiate encrypt and decrypt classes
//    private Encrypt encryptedText = new Encrypt();
//    private Decrypt decryptedText = new Decrypt();

    // BitShifter has the Encrypt and Decrypt methods...
    private BitShifter bitShifter = new BitShifter();

    private FirebaseListAdapter<ChatMessage> adapter;

    private TextView messageText;
    private TextView messageUser;
    private TextView messageTime;

    private ListView listOfMessages;
    private ChatMessage chatItemSelected;
    private String itemSelected;

    private FloatingActionButton fab;
    private EditText input;
    private EditText seedInput;

    Toolbar toolbar;

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
        seedInput = findViewById(R.id.seedInput);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle(" Room - " + room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        displayChatMessages();

        // Let user know that if seed number is 0, normal chat function can occur without encryption
        Toast.makeText(getApplicationContext(),"Hey " + user_name + "!  Did you know that if you set Seed Num to 0, encryption will not occur?",Toast.LENGTH_LONG).show();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button enabled

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
                //decryptedText = new Decrypt(); // DECRYPTED OUTPUT IS THE INSTANTIATED CLASS.  USING THE INSTANTIATED CLASS, CALL DECRYPT METHOD ON TEXT SELECTED


                // check to ensure that there is a seed value for decryption
                if(TextUtils.isEmpty(seedInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter a seed number for decryption", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"DECRYPTED OUTPUT: " +  bitShifter.Decrypt(itemSelected, Integer.parseInt(seedInput.getText().toString())),Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

    }






    public void buttonClicked(View view){

        String codedText = "";
        //encryptedText = new Encrypt();

        if(TextUtils.isEmpty(seedInput.getText().toString()) || TextUtils.isEmpty(input.getText().toString())) {  // use TextUtils.isEmpty(edittext) to check if edittext is empty
            Toast.makeText(getApplicationContext(), "Please ensure that there are values for each of the fields below...",Toast.LENGTH_SHORT).show();
        } else if(Long.parseLong(seedInput.getText().toString()) > 9000000000000000000L) { // program can't handle seed numbers greater than 9 million million million (9 quintillion)
            Toast.makeText(getApplicationContext(), "Please enter a seed number less than 9 million million million (9 quintillion)!",Toast.LENGTH_SHORT).show();
        } else if(Long.parseLong(seedInput.getText().toString()) == 0){ // if seed is 0, don't encrypt
            root.push().setValue(new ChatMessage(input.getText().toString(), user_name));
            // Clear the input
            input.setText("");
        } else {
            codedText = bitShifter.Encrypt(input.getText().toString(), Long.parseLong(seedInput.getText().toString()));

            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            root.push().setValue(new ChatMessage(codedText, user_name));

            // Clear the input
            input.setText("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.item1_id){
            Toast.makeText(this,"Item 1 selected",Toast.LENGTH_SHORT).show();
        } else if(id==R.id.item2_id){
            Toast.makeText(this,"Item 2 selected",Toast.LENGTH_SHORT).show();
        } else if(id==R.id.item3_id){
            Toast.makeText(this,"Item 3 selected",Toast.LENGTH_SHORT).show();
        } else if(id==android.R.id.home){
            Intent intentChatRoom = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentChatRoom);
        } else if(id==R.id.cat){
            Toast.makeText(this,"Cat selected",Toast.LENGTH_SHORT).show(); // https://materialdesignicons.com/       see menu -> main_menu.xml to see item layout
        } else if(id==R.id.qrcodescan){
            Toast.makeText(this,"QR code scan selected",Toast.LENGTH_SHORT).show(); // https://materialdesignicons.com/
        }

        return super.onOptionsItemSelected(item);
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