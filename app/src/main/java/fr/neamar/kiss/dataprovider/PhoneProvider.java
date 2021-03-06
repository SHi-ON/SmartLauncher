package fr.neamar.kiss.dataprovider;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.regex.Pattern;

import fr.neamar.kiss.loader.LoadPhonePojos;
import fr.neamar.kiss.pojo.PhonePojo;
import fr.neamar.kiss.pojo.Pojo;

public class PhoneProvider extends Provider<PhonePojo> {
    public static final String PHONE_SCHEME = "phone://";
    private boolean deviceIsPhone = false;

    public PhoneProvider(Context context) {
        super(new LoadPhonePojos(context));

        PackageManager pm = context.getPackageManager();
        deviceIsPhone = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    public ArrayList<Pojo> getResults(String query) {
        ArrayList<Pojo> pojos = new ArrayList<>();

        // Append an item only if query looks like a phone number and device has phone capabilities
        if (deviceIsPhone && query.matches("^[0-9+ .]{2,}$")) {
            pojos.add(getResult(query));
        }

        return pojos;
    }


    public Pojo findById(String id) {
        return getResult(id.replaceFirst(Pattern.quote(PHONE_SCHEME), ""));
    }

    public Pojo getResult(String phoneNumber) {
        PhonePojo pojo = new PhonePojo();
        pojo.id = PHONE_SCHEME + phoneNumber;
        pojo.phone = phoneNumber;
        pojo.relevance = 20;
        return pojo;
    }
}
