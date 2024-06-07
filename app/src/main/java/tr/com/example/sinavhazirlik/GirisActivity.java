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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tr.com.example.sinavhazirlik.databinding.ActivityGirisBinding;

public class GirisActivity extends AppCompatActivity {
    ActivityGirisBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_giris);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            binding = ActivityGirisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void SifremiUnutum(View view) {
        Intent intent = new Intent(this, SifreUnutum.class);
        startActivity(intent);
    }

    public void Giris(View view) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String txtEposta =binding.editTextTextEmailAddress.getText().toString().trim();
        String txtParola=binding.editTextTextPassword.getText().toString();
        if (!txtEposta.isEmpty())
        {
            if (!txtParola.isEmpty())
            {
                firebaseAuth
                        .signInWithEmailAndPassword(txtEposta,txtParola)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(GirisActivity.this, "Giriş Başarıyla Yapıldı", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(GirisActivity.this, "Sistemde Böyle Bir Kayıt Bulunamadı!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent intent = new Intent(this, anamenuActivity.class);
                        startActivity(intent);
            }else Toast.makeText(this, "Parola Alanı Boş Bırakılamaz!!", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "E-posta Alanı Boş Bırakılamaz!!", Toast.LENGTH_SHORT).show();
    }
}