package com.telran.a20_01_20_cw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    MenuItem deleteItem, saveItem, editItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(StoreProvider.getInstance().getToken() != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.root, new ListFragment())
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.root,new LoginFragment())
                    .commit();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        deleteItem = menu.findItem(R.id.deleteItem);
//        saveItem = menu.findItem(R.id.saveItem);
//        editItem = menu.findItem(R.id.editItem);
//        deleteItem.setVisible(false);
//        saveItem.setVisible(false);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.logoutItem){
//            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
//        }else if(item.getItemId() == R.id.editItem){
//            editItem.setVisible(false);
//            saveItem.setVisible(true);
//        }else if(item.getItemId() == R.id.saveItem){
//            editItem.setVisible(true);
//            saveItem.setVisible(false);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
