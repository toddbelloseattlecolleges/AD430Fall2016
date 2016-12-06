package northseattlecollege.ASLBuddy.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import northseattlecollege.ASLBuddy.CreateRequest;
import northseattlecollege.ASLBuddy.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRequestErrorFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestErrorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestErrorFragment extends Fragment {

    private OnRequestErrorFragmentInteractionListener mListener;

    public RequestErrorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RequestErrorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestErrorFragment newInstance() {
        RequestErrorFragment fragment = new RequestErrorFragment();
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
        View view =  inflater.inflate(R.layout.fragment_request_error, container, false);

        Button increaseRange = (Button) view.findViewById(R.id.button_increase_range);
        increaseRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(CreateRequest.REQUEST_TYPE_PHYSICAL);
            }
        });

        Button videoRequest = (Button) view.findViewById(R.id.button_video_request);
        videoRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(CreateRequest.REQUEST_TYPE_VIDEO);
            }
        });

        Button hearingTool = (Button) view.findViewById(R.id.button_hearing_tool);
        hearingTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(CreateRequest.REQUEST_TYPE_HEARING_TOOL);
            }
        });


        return view;
    }

    public void onButtonPressed(String requestType) {
        if (mListener != null) {
            mListener.onRequestErrorFragmentInteraction(requestType);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRequestErrorFragmentInteractionListener) {
            mListener = (OnRequestErrorFragmentInteractionListener) context;
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
    public interface OnRequestErrorFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRequestErrorFragmentInteraction(String requestType);
    }
}
