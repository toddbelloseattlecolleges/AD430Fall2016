package northseattlecollege.ASLBuddy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import northseattlecollege.ASLBuddy.CreateRequest;
import northseattlecollege.ASLBuddy.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCreateRequestFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRequestFragment extends Fragment
        implements SetRadiusFragment.OnSetRadiusFragmentInteractionListener {
    private static final String ARG_REQUEST_TYPE = "request_type";

    // TODO: Rename and change types of parameters
    private String requestType;

    private OnCreateRequestFragmentInteractionListener mListener;

    private SetRadiusFragment setRadiusFragment;

    public CreateRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param requestType
     * @return A new instance of fragment CreateRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRequestFragment newInstance(String requestType) {
        CreateRequestFragment fragment = new CreateRequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REQUEST_TYPE, requestType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requestType = getArguments().getString(ARG_REQUEST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_request, container, false);

        Button submitButton = (Button) view.findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRequest();
            }
        });

        setupRequestType(view);

        final Spinner requestPurposeSpinner = (Spinner) view.findViewById(R.id.spinner_request_purpose);
        requestPurposeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String selectedItem = ((TextView) v).getText().toString();
                updateOtherEditView(view, selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCreateRequestFragmentInteractionListener) {
            mListener = (OnCreateRequestFragmentInteractionListener) context;
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

    private void setupRequestType(View view) {
        if (requestType.compareTo(CreateRequest.REQUEST_TYPE_PHYSICAL) == 0) {
            // ToDo: Get default/saved radius from Settings
            setRadiusFragment = SetRadiusFragment.newInstance(.25f);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.set_radius_fragment_container, setRadiusFragment);
            transaction.commit();
        }
    }

    private void updateOtherEditView(View view, String selectedItem) {
        EditText otherDescEditText = (EditText) view.findViewById(R.id.edit_description);
        // ToDo: Replace hard-coded other with value from strings.xml
        if (selectedItem.compareTo("Other") == 0) {
            otherDescEditText.setEnabled(true);
        } else {
            otherDescEditText.setEnabled(false);
        }
    }

    private void submitRequest() {
        if (mListener != null) {
            mListener.onCreateRequestFragmentInteraction(setRadiusFragment == null ? 0 : setRadiusFragment.getRadius());
        }
    }

    @Override
    public void onSetRadiusFragmentInteraction(float radius) {

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
    public interface OnCreateRequestFragmentInteractionListener {
        void onCreateRequestFragmentInteraction(float radius);
    }
}
