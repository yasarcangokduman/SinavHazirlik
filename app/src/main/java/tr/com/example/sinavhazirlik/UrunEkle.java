package tr.com.example.sinavhazirlik;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tr.com.example.sinavhazirlik.databinding.ActivityGirisBinding;
import tr.com.example.sinavhazirlik.databinding.ActivityUrunEkleBinding;
import tr.com.example.sinavhazirlik.databinding.ActivityUyeBinding;

public class UrunEkle extends AppCompatActivity {
    ActivityUrunEkleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_urun_ekle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityUrunEkleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void Kaydet(View view) {
        Intent intent = new Intent(this, anamenuActivity.class);
        startActivity(intent);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String txtUrunadi = binding.editTextText.getText().toString().trim();
        String txtUrunAdeti = binding.editTextNumber.getText().toString().trim();
        String txtUrunFiyati = binding.editTextNumber2.getText().toString().trim();

        if (!txtUrunadi.isEmpty() && !txtUrunFiyati.isEmpty() && !txtUrunAdeti.isEmpty()) {

            double decFiyat = Double.parseDouble(txtUrunFiyati);
            int intAdet = Integer.parseInt(txtUrunAdeti);

            Map<String, Object> urun = new HashMap<>();
            urun.put("urunAdi", Objects.requireNonNull(txtUrunadi));
            urun.put("fiyat", Objects.requireNonNull(decFiyat));
            urun.put("adet", Objects.requireNonNull(intAdet));

            db.collection("urunler")
                    .add(urun).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(UrunEkle.this, "Ürün Başarıyla Eklendi", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(UrunEkle.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            

                                    Toast.makeText(UrunEkle.this, "Ürün Kaydedilemedi", Toast.LENGTH_SHORT).show();


                        }
                    });
        } else {
            Toast.makeText(this, "Boş Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
        }
    }
}