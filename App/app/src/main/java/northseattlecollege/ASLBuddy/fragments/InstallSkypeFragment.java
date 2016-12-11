package northseattlecollege.ASLBuddy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import northseattlecollege.ASLBuddy.R;
import northseattlecollege.ASLBuddy.SkypeResources;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInstallSkypeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InstallSkypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstallSkypeFragment extends Fragment {

    private OnInstallSkypeFragmentInteractionListener mListener;

    public InstallSkypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InstallSkypeFragment.
     */
    public static InstallSkypeFragment newInstance() {
        InstallSkypeFragment fragment = new InstallSkypeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_install_skype, container, false);

        Button installSkype = (Button) view.findViewById(R.id.button_install_skype);
        installSkype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(v);
            }
        });

        return view;
    }

    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onInstallSkypeFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInstallSkypeFragmentInteractionListener) {
            mListener = (OnInstallSkypeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCreateRequestFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnInstallSkypeFragmentInteractionListener {
        void onInstallSkypeFragmentInteraction();
    }
}
