///**
// * Copyright 2016 Google Inc. All Rights Reserved.
// * <p>
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * <p>
// * http://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package ai.meccamedinatv.mekkalive.online.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import ai.meccamedinatv.mekkalive.online.Q;
//import com.walhalla.meccamedina.R;
//import ai.meccamedinatv.mekkalive.online.message.Message;
//import ai.meccamedinatv.mekkalive.online.message.MessageAdapter;
//import com.walhalla.ui.DLog;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Demonstrate Firebase Authentication using a Google ID Token.
// */
//public class GoogleSignInActivity extends BaseActivity implements
//        View.OnClickListener, MessageAdapter.MessageAdapterCallback, OnFailureListener {
//
//    private static final String TAG = "@@@";
//    private static final int RC_SIGN_IN = 9001;
//
//    private RecyclerView myRecyclerView;
//    private FloatingActionButton fab;
//    private MessageAdapter adapter;
//    private FirebaseAuth mAuth;
//    private final List<Message> data = new ArrayList<>();
//
//    private GoogleSignInClient mGoogleSignInClient;
//    private String __UID = "";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//        setProgressBar(findViewById(R.id.progressBar));
//
//        // Button listeners
//        findViewById(R.id.signInButton).setOnClickListener(this);
//        findViewById(R.id.signOutButton).setOnClickListener(this);
//        findViewById(R.id.disconnectButton).setOnClickListener(this);
//
//        // [START config_signin]
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id0))
//                .requestEmail()
//                .build();
//        // [END config_signin]
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        mAuth = FirebaseAuth.getInstance();
//        myRecyclerView = findViewById(R.id.list);
//        adapter = new MessageAdapter(
//                this, data/*, DBR*/
//        );
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
//        myRecyclerView.setLayoutManager(manager);
//        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        myRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
//        myRecyclerView.setAdapter(adapter);
//        final EditText input = findViewById(R.id.input);
//        fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//            if (input.getText().toString().trim().equals("")) {
//                Toast.makeText(GoogleSignInActivity.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
//            } else {
//                try {
//                    new Thread(() -> {
//                        FirebaseUser USER = FirebaseAuth.getInstance().getCurrentUser();
//                        String name = (USER == null) ? "NULLaa" : USER.getDisplayName();
//                        String uid = (USER == null) ? "NULLaa" : USER.getUid();
//
//
//                        //ref.setValue("77777777").addOnFailureListener(e -> Log.d("@@@@", e.getLocalizedMessage()));
//
//                        String message = "" + input.getText().toString();
//                        sendMessage(message, name, uid);
//
////demo
////                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://mekka-70cf9.firebaseio.com");
////                        DatabaseReference collectionRef = database.getReference("aaaa");
////                        collectionRef.push().setValue("aaaaaaa1").addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                Log.d("@@@@", e.getLocalizedMessage());
////                            }
////                        });
////                        collectionRef.push().setValue("aaaaaaa2");
////                        collectionRef.push().setValue("aaaaaaa3").addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                Log.d("@@@@", e.getLocalizedMessage());
////                            }
////                        });
//                        // Write a message to the database
////                        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
////                        DatabaseReference mDbRef = mDatabase.getReference("Donor/Name");
////                        mDbRef.setValue("Parinitha Krishna").addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                Log.d("@@@@", e.getLocalizedMessage());
////                            }
////                        });
//
//                    }).start();
//
//                } catch (Exception e) {
//                    DLog.handleException(e);
//                }
//                input.setText("");
//            }
//        });
//
//    }
//
//    private void sendMessage(String message, String name, String uid) {
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference(Q.KEY_FIREBASE_DATASET).child(Q.KEY_FIREBASE_CHAT);
//        ref.push().setValue(new Message(message, name, uid))
//                .addOnFailureListener(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadMessages();
//        //demo();
//    }
//
////    private void demo() {
////        data.add(new Message("Abdelatif​", "♥️♥️"));
////        data.add(new Message("Ismail Lat Abu​", "very discipline and nice"));
////        data.add(new Message("Riyanti Riyanti", "​ya allah aku ingin sekali ksini"));
////        data.add(new Message("Anes Benchaib​", "I love you maka"));
////        data.add(new Message("Irfan Khan​", "MashaAllah"));
////        data.add(new Message("Irfan Khan", "​shubahAllah"));
////        data.add(new Message("Princess Fatima_0404​", "Mashallah ❤️"));
////        data.add(new Message("PANTS offical​", "subhanallah"));
////        data.add(new Message("Princess Fatima_0404", "​Mashallah"));
////        data.add(new Message("hehe labrent", "​اللهم أرزقني"));
////        data.add(new Message("Princess Fatima_0404​", "Mashallah"));
////        data.add(new Message("Zara Jim​", "Mashaallah Subhanallah Allahhu Akbar"));
////        data.add(new Message("Zara Jim​", "Heart touching Quran Taloat Mashaallah"));
////        for (Message d : data) {
////            sendMessage(d.user, d.text, new Date().getTime()+"");
////        }
////    }
//
//    private void loadMessages() {
//        try {
////            fff().addChildEventListener(this);
//
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference(Q.KEY_FIREBASE_DATASET).child(Q.KEY_FIREBASE_CHAT);
////            ref.addValueEventListener(new ValueEventListener() {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    if (Q.KEY_FIREBASE_CHAT.equals(snapshot.getKey())) {
////                        data.clear();
////                        for (DataSnapshot child : snapshot.getChildren()) {
////                            try {
////                                Message message = child.getValue(Message.class);
////                                if (message != null) {
////                                    data.add(message);
////                                    DLog.d("UPDATED");
////                                }
////                            } catch (DatabaseException w) {
////                                DLog.handleException(w);
////                            }
////                        }
////                        adapter.notifyDataSetChanged();
////                        if (adapter.getItemCount() > 0) {
////                            myRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
////                        }
////                    }
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError error) {
////                    DLog.handleException(error.toException());
////                }
////            });
//            ref.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
//                    try {
//                        DLog.d("key:: " + dataSnapshot.getRef().getKey() + "\t" + dataSnapshot.toString());
//                        //if (Const.KEY_CHAT.equals(dataSnapshot.getKey())) {
//                        Message value = dataSnapshot.getValue(Message.class);
//                        data.add(value);
//                        adapter.notifyDataSetChanged();
//                        if (adapter.getItemCount() > 0) {
//                            myRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
//                        }
//                        //}
//                    } catch (Exception e) {
//                        DLog.handleException(e);
//                    }
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
//                    DLog.d("3");
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                    DLog.d("2");
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
//                    DLog.d("1");
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    DLog.handleException(error.toException());
//                }
//            });
////            database.getReference().addValueEventListener(this);
////            database.getReference().addChildEventListener(this);
//
//        } catch (Exception e) {
//            DLog.handleException(e);
//        }
//    }
//
//    // [START on_start_check_user]
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//    // [END on_start_check_user]
//
//    // [START onactivityresult]
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                DLog.handleException(e);
//                Toast.makeText(this, "@" + e.getMessage(), Toast.LENGTH_LONG).show();
//                updateUI(null);
//            }
//        }
//    }
//    // [END onactivityresult]
//
//    // [START auth_with_google]
//    private void firebaseAuthWithGoogle(String idToken) {
//        // [START_EXCLUDE silent]
//        showProgressBar();
//        // [END_EXCLUDE]
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.disconnectButton), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // [START_EXCLUDE]
//                        hideProgressBar();
//                        // [END_EXCLUDE]
//                    }
//                });
//    }
//    // [END auth_with_google]
//
//    // [START signin]
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    // [END signin]
//
//    private void signOut() {
//        // Firebase sign out
//        mAuth.signOut();
//
//        // Google sign out
//        mGoogleSignInClient.signOut().addOnCompleteListener(this,
//                new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
//                    }
//                });
//    }
//
//    private void revokeAccess() {
//        // Firebase sign out
//        mAuth.signOut();
//
//        // Google revoke access
//        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
//                task -> updateUI(null));
//    }
//
//    private void updateUI(FirebaseUser user) {
//        hideProgressBar();
//        if (user != null) {
//            String email = user.getEmail();
//            String id = user.getUid();
//            DLog.d("@" + email + "\t" + id);
//            __UID = id;
//            findViewById(R.id.fab).setVisibility(View.VISIBLE);
//            findViewById(R.id.input).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.signInButton).setVisibility(View.GONE);
//            findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);
//        } else {
//            //((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
//            //((TextView) findViewById(R.id.detail)).setText(null);
//            findViewById(R.id.fab).setVisibility(View.GONE);
//            findViewById(R.id.input).setVisibility(View.GONE);
//
//            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
//            findViewById(R.id.signOutAndDisconnect).setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.signInButton) {
//            signIn();
//        } else if (i == R.id.signOutButton) {
//            signOut();
//        } else if (i == R.id.disconnectButton) {
//            revokeAccess();
//        }
//    }
//
//    @Override
//    public String getLoggedInUserName() {
//        return __UID;
//    }
//
//    @Override
//    public void onFailure(@NonNull Exception e) {
//        if (e.getMessage() != null) {
//            Log.d("@@@@", e.getMessage());
//        }
//    }
//}
