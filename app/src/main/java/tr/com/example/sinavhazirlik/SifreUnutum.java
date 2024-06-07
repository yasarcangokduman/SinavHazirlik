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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import tr.com.example.sinavhazirlik.databinding.ActivitySifreUnutumBinding;

public class SifreUnutum extends AppCompatActivity {
    ActivitySifreUnutumBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sifre_unutum);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding =ActivitySifreUnutumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void SifremiUnutum(View view) {
        String txtEposta =binding.editTextTextEmailAddress2.getText().toString().trim();
        if (!txtEposta.isEmpty())
        {
            FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(txtEposta)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SifreUnutum.this, "Kurarma E-Postası Gönderildi!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SifreUnutum.this, "Sistemde E-Posta Bulunamadı!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else Toast.makeText(this, "E posta Boş Bırakılamaz!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, GirisActivity.class);
        startActivity(intent);
    }
}