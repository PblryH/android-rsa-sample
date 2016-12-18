package brainattica.com.rsasample.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brainattica.com.rsasample.R;
import brainattica.com.rsasample.crypto.RSA;
import brainattica.com.rsasample.utils.Preferences;
import timber.log.Timber;

/**
 * Created by javiermanzanomorilla on 12/05/15.
 */
public class EncryptAndDecryptFragment extends Fragment implements PagerSlide {

    private TextView textToBeEncrypted;

    private TextView encryptedText;

    private TextView decryptedText;

    private Button encrypt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encrypt_or_decrypt, container, false);
        textToBeEncrypted = (TextView) view.findViewById(R.id.text_to_be_encrypted);
        encryptedText = (TextView) view.findViewById(R.id.encrypted_text);
        decryptedText = (TextView) view.findViewById(R.id.decrypted_text);
        encrypt = (Button) view.findViewById(R.id.encrypt);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Preferences.getString(Preferences.RSA_PRIVATE_KEY) == null) {
                    Toast.makeText(getActivity(), "There's now RSA KeyPair generated", Toast.LENGTH_LONG).show();
                    return;
                }
                if (textToBeEncrypted.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Enter a correct message to encrypt", Toast.LENGTH_LONG).show();
                    return;
                }
                String message = textToBeEncrypted.getText().toString();
                String encryptedMessage = RSA.encryptWithStoredKey(message);
                encryptedMessage = "Jk+bjPWro68vN/cK1bt9toEqMdVv46Ubm/kcspNWK8HxSjCea0MUnHeOpA6c8MqfKfPoo3DBfowe\n" +
                        "                                           6oaOwtuofkbx4G6emUNlkDXp4vFTW0b19an4oGHCc0lJvN7Jz4QtM3m+sWBd05tJTN6vu9uWUcXS\n" +
                        "                                           0jSFd54RsyFZfFI1wOA=";
//                String decryptedMessage = RSA.decryptWithStoredKey(encryptedMessage);
                String key = "-----BEGIN PRIVATE KEY-----\n" +
                        "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALXZLm7RmoQ+2dDw\n" +
                        "RYpfIAkOEWMOp08brGdaRGh8knrNOgVu2hP6qGKTzuxbpzCaIM5HGZYI//6PQt5X\n" +
                        "GVTDNS7FqyvDsPqH/arxkVawSY2aiEFEa+SMvwOQaUP5mPbo4xJ+Lz/952KRIWaf\n" +
                        "+vMgnywnmcf43brcNEjITcrOoXnHAgMBAAECgYAl6UxL4DjlfzDr5VuqGGDWYm9v\n" +
                        "hluNG4ja1y/R1L65CVYiUdT3O1eljYkjL4xwjoFuX9ZOuDued4GKVwA58qSAbK0w\n" +
                        "uAQ/9zIY9AaYyeJa8XGgVczCRCkd5sTyZer2nsRu+3vOA2IWJ7C6d/Cwnu3zitu4\n" +
                        "pdmsfVUbWdpUGCKOuQJBAOQKRj1+5BIST8PhH8f07RpdtsZOT801B7XhsaT0df3P\n" +
                        "yZs5KzrjkUn5U0RUpKBfd64y+WHZXDS+FUTi9BGAzBUCQQDMJQoTYKYbWEUffowA\n" +
                        "Ms91a1sBeh7bsXE0NNiFXryUwRKWzOg9YfWho29vwE1/DsZHFqkWRVxxbrsv0e3o\n" +
                        "TblrAkB3ZdHFHQ05UREmlFbZkSob5flu903dOejhmFw07DJjREpg1ZwG52QJAVxR\n" +
                        "qKfRuGqncNUWIIKgsu3b9aCpEXDJAkEAo00pzzBsD8m/mZ74TRxYGhyjsv3Ge4Vp\n" +
                        "AhMX9TBAeFouDZWiXZ/kBsYfWWyiUXY3JBy7a8ZWWaLzeCBdSIwMOwJBALxiSLy1\n" +
                        "TpCXuF/2rnx0sgSZE/EPyblwaVbGjF3CpsMAMfSOVjW5cyJdz8Z+XffbGDlGdZFS\n" +
                        "FwFM9ME1QMZKUTQ=\n" +
                        "-----END PRIVATE KEY-----";
                Preferences.putString(Preferences.RSA_PRIVATE_KEY, key);
                String decryptedMessage = RSA.decryptWithStoredKey(encryptedMessage);
                Timber.d("Encrypted Text: %s", encryptedMessage);
                Timber.d("Decrypted Text: %s", decryptedMessage);
                encryptedText.setText("Encrypted Text: " + encryptedMessage);
                decryptedText.setText("Decrypted Text: " + decryptedMessage);

                Preferences.putString(Preferences.ENCRYPTED_MESSAGE, encryptedMessage);
            }
        });
        return view;
    }

    @Override
    public List<Integer> getVisibleButtons() {
        List<Integer> res = new ArrayList<>();
        res.add(R.id.prev);
        res.add(R.id.next);
        return res;
    }

    @Override
    public String getSubtitle() {
        return "Encrypt / Decrypt";
    }

}
