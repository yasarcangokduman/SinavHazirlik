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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tr.com.example.sinavhazirlik.databinding.ActivityUyeBinding;

public class UyeActivity extends AppCompatActivity {
    ActivityUyeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_uye);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityUyeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void kaydet(View view) {

        String txteposta = binding.editTextTextEmailAddress.getText().toString().trim();
        String txtParola = binding.editTextTextPassword.getText().toString();
        String txtrParola = binding.editTextTextPassword1.getText().toString();

        if (!txteposta.isEmpty())
        {
            if (!txtParola.isEmpty())
            {
                if (!txtrParola.isEmpty())
                {
                    if (txtParola.equals(txtrParola))
                    {
                        FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(txteposta,txtParola)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(UyeActivity.this, "Kullanıcı Oluşturuldu !", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(UyeActivity.this, "Kullanıcı oluşturulamadı!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }else Toast.makeText(this, "Parololar Eşleşmiyor!", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(this, "Parola Tekrarı Boş Geçilemez!", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Parola Boş Gecilemez!", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "E-posta Boş Geçilemez!", Toast.LENGTH_SHORT).show();

    }
}