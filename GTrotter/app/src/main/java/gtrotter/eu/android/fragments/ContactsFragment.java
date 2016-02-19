package gtrotter.eu.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.adapters.ContactAdapter;
import gtrotter.eu.android.models.Contact;

public class ContactsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.contact_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.swapAdapter(new ContactAdapter(v, getData()), false);
        setHasOptionsMenu(true);
        return v;
    }

    private List<Contact> getData() {
        List<Contact> tmp = new ArrayList<>();
        tmp.add(new Contact("Baptiste", "0783876175", true, ""));
        tmp.add(new Contact("Ingrid", "0783876175", false, ""));
        tmp.add(new Contact("Tristan", "0783876175", false, ""));
        tmp.add(new Contact("Etienne", "0783876175", true, ""));
        tmp.add(new Contact("Marc", "0783876175", false, ""));
        tmp.add(new Contact("Quentin", "0783876175", true, ""));
        return tmp;
    }


}
