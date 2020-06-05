package sow.elhadj.sow_fin_etude;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;



public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        Button btnlogout = view.findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(view1 -> alertsignout());
        return view;
    }



    private void alertsignout()
    {

        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getActivity(),MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

}
