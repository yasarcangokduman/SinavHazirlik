package tr.com.example.sinavhazirlik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import tr.com.example.sinavhazirlik.databinding.ActivityAnamenuBinding;
import tr.com.example.sinavhazirlik.databinding.ActivityGirisBinding;

public class anamenuActivity extends AppCompatActivity {
    ActivityAnamenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anamenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityAnamenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(anamenuActivity.this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(anamenuActivity.this, GirisActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        else
        {
            db.collection("urunler").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<Urun> arrayList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Urun urun = document.toObject(Urun.class);
                            urun.setId(document.getId());
                            arrayList.add(urun);
                        }
                        UrunAdapter adapter = new UrunAdapter(anamenuActivity.this,arrayList);
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(anamenuActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void YeniUrun(View view) {
        Intent intent = new Intent(this, UrunEkle.class);
        startActivity(intent);
    }
}