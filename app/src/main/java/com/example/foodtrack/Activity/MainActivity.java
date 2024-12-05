package com.example.foodtrack.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtrack.Adapter.NotificationHelper;
import com.example.foodtrack.Fragment.Home_Page;
import com.example.foodtrack.Fragment.fragment_product_detail;
import com.example.foodtrack.Fragment.product_detail_change_info;
import com.example.foodtrack.R;
import com.example.foodtrack.Fragment.checkout;
import com.example.foodtrack.SocketManager;
import com.example.foodtrack.databinding.ActivityMainBinding;
import com.example.foodtrack.Fragment.favorite_fragment;
import com.example.foodtrack.Fragment.food_fragment;
import com.example.foodtrack.Fragment.fragment_profile;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    private Socket mSocket;

    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;
    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String fragmentToLoad = intent.getStringExtra("fragmentToLoad");
        String ghiChu = intent.getStringExtra("ghiChu");
        Double tongTien =  intent.getDoubleExtra("tongTien",0);


        if (fragmentToLoad != null) {
            if (fragmentToLoad.equals("cartFragment")) {
                checkout checkoutFragment = new checkout();

                Log.d("tongTienMainActivity", String.valueOf(tongTien));

                Bundle bundle = new Bundle();
                bundle.putString("ghiChu", ghiChu);
                bundle.putDouble("tongTien", tongTien);

                checkoutFragment.setArguments(bundle);

                ReplaceFragment(checkoutFragment);
            }
            else if(fragmentToLoad.equals("product_detail_change_info")){
                product_detail_change_info detailChangeInfo = new product_detail_change_info();
                Bundle productBundle = intent.getBundleExtra("productBundle");
                if (productBundle != null) {
                    detailChangeInfo.setArguments(productBundle);
                }
                ReplaceFragment(detailChangeInfo);
            }
        } else {
            ReplaceFragment(new Home_Page());
        }

        notificationHelper = new NotificationHelper(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission();
        }
        ControlSocket();


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                ReplaceFragment(new Home_Page());
            } else if (item.getItemId() == R.id.explore) {
                ReplaceFragment(new food_fragment());
            } else if (item.getItemId() == R.id.cart) {
                Intent cart = new Intent(MainActivity.this, cart.class);
                startActivity(cart);
            } else if (item.getItemId() == R.id.favorite) {
                ReplaceFragment(new favorite_fragment());

            } else if (item.getItemId() == R.id.account) {
                ReplaceFragment(new fragment_profile());
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);


    }

    private void checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_NOTIFICATION_PERMISSION
            );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Người dùng từ chối quyền
                Toast.makeText(this, "Bạn đã từ chối quyền thông báo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ControlSocket() {
        mSocket = SocketManager.getInstance().getSocket();
        mSocket.on("SEND_NOTIFICATION_CLIENT", onRetrieveNotification);
    }

    private Emitter.Listener onRetrieveNotification = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (this != null) {
                MainActivity.this.runOnUiThread(() -> {
                    try {
                        JSONObject obj = (JSONObject) args[0];
                        String tieuDe = obj.getString("tieuDe");
                        String noiDung = obj.getString("noiDung");
//                        Date noiDung = obj.getString("noiDung");

                        notificationHelper.sendNotification(tieuDe,noiDung);
                    }
                    catch (JsonIOException e){
                        e.printStackTrace();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    };

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fragment instanceof fragment_product_detail) {
            ft.setCustomAnimations(
                    R.anim.anim_slide_up_product_detail,
                    R.anim.anim_fade_out_product_detail,
                    R.anim.anim_fade_in_product_detail,
                    R.anim.anim_slide_down_product_detail
            );
        }
        ft.add(R.id.frameLayout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}