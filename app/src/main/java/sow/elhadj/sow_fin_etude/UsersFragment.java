package sow.elhadj.sow_fin_etude;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class UsersFragment extends Fragment {


    public UsersFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_users, container, false);

        Button signalV = view.findViewById(R.id.volé);
        Button contactServiceEtat = view.findViewById(R.id.contactser);
      //  Button verifierInf = view.findViewById(R.id.inf);
        Button reiniMdp = view.findViewById(R.id.reinipass);

        reiniMdp.setOnClickListener(v ->startActivity(new Intent(getActivity(), ResetPasswordActivity.class)));

        signalV.setOnClickListener(v -> execute());
        contactServiceEtat.setOnClickListener(v ->execute());

        return view;
    }

    private void execute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Contactez le service d'Etat");
        builder.setMessage("05359-31540");
        builder.create().show();
    }


}
