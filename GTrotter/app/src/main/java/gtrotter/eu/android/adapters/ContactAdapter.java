package gtrotter.eu.android.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gtrotter.eu.android.R;
import gtrotter.eu.android.models.Contact;
import gtrotter.eu.android.utilities.Fab.ContactFabAdd;
import gtrotter.eu.android.utilities.Fab.ContactFabDelete;
import gtrotter.eu.android.utilities.Fab.ContactFabMore;
import gtrotter.eu.android.utilities.Fab.ContactFabSort;
import gtrotter.eu.android.utilities.MyApplication;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private View mParent;
    private Context mContext;
    private List<Contact> mContacts, mOldList;
    private boolean mExpanded = false;
    private int mSelected = 0;
    private ContactFabMore mFabMore;
    private ContactFabDelete mFabDel;
    private ContactFabAdd mFabAdd;
    private ContactFabSort mFabSort;

    public ContactAdapter(View parent, List<Contact> contacts) {
        mParent = parent;
        mContacts = mOldList = contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.contact_item, parent, false);
        mFabMore = (ContactFabMore) mParent.findViewById(R.id.contact_fab);
        mFabDel = (ContactFabDelete) mParent.findViewById(R.id.contact_fab_delete);
        mFabAdd = (ContactFabAdd) mParent.findViewById(R.id.contact_fab_add);
        mFabSort = (ContactFabSort) mParent.findViewById(R.id.contact_fab_sort);

        mContacts = MyApplication.getInstance().sortContactsByName(mContacts);

        mFabMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFabDel.setState(ContactFabDelete.EContactDelete.CLASSIC);
                if (mExpanded) {
                    closeFab(false);
                } else {
                    displayFab(mFabAdd, View.VISIBLE);
                    displayFab(mFabSort, View.VISIBLE);
                    switch (mFabMore.getState()) {
                        case CLASSIC:
                            displayFab(mFabDel, View.GONE);
                            break;
                        case SELECT:
                            displayFab(mFabDel, View.VISIBLE);
                            break;
                        default:
                            displayFab(mFabDel, View.GONE);
                            break;
                    }
                    mExpanded = true;
                }
            }
        });

        mFabDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mFabDel.getState()) {
                    case CLASSIC:
                        deleteContacts();
                        mFabDel.setState(ContactFabDelete.EContactDelete.UNDO);
                        break;
                    case UNDO:
                        undo();
                        mFabDel.setState(ContactFabDelete.EContactDelete.CLASSIC);
                        break;
                    default:
                        break;
                }
            }
        });

        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unSelectAllContacts();
                hideMiniFab();
                mExpanded = false;
                mFabDel.setState(ContactFabDelete.EContactDelete.CLASSIC);
                mFabAdd.setState(ContactFabAdd.EContactFabAdd.CLASSIC);
                mFabMore.setState(ContactFabMore.EContactFabMore.CLASSIC);
            }
        });

        mFabSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFabSort.getState() == ContactFabSort.EContactFabSort.ALPHA) {
                    mFabSort.setState(ContactFabSort.EContactFabSort.FAVORITE);
                    mContacts = MyApplication.getInstance().sortContactsByFav(mContacts);
                    notifyDataSetChanged();
                } else {
                    mFabSort.setState(ContactFabSort.EContactFabSort.ALPHA);
                    mContacts = MyApplication.getInstance().sortContactsByName(mContacts);
                    notifyDataSetChanged();
                }
            }
        });

        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        final Contact current = mContacts.get(position);
        defaultBinding(holder, current);
        holder.favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current.ismFavor()) {
                    current.setFavorite(false);
                    holder.favor.setImageDrawable(ContextCompat.getDrawable(mContext, android.R.drawable.btn_star_big_off));
                } else {
                    current.setFavorite(true);
                    holder.favor.setImageDrawable(ContextCompat.getDrawable(mContext, android.R.drawable.btn_star_big_on));
                }
                notifyDataSetChanged();
            }
        });

        holder.clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mFabMore.getState()) {
                    case CLASSIC:
                        if (mExpanded) {
                            closeFab(true);
                            mExpanded = false;
                        } else {
                            //TODO: open new activity|fragment with Contact's details
                            MyApplication.getInstance().displayMessageShort("CONTACT CLICKED");
                        }
                        break;
                    case SELECT:
                        hideMiniFab();
                        unSelectAllContacts();
                        break;
                }

                mFabMore.setState(ContactFabMore.EContactFabMore.CLASSIC);
                mFabDel.setState(ContactFabDelete.EContactDelete.CLASSIC);
                mFabAdd.setState(ContactFabAdd.EContactFabAdd.CLASSIC);
            }
        });

        holder.clickable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView name = (TextView) v.findViewById(R.id.contact_letter);
                ImageView check = (ImageView) v.findViewById(R.id.contact_selected);
                if (current.ismSelected()) {
                    if (mSelected == 1) {
                        hideMiniFab();
                        mFabMore.setState(ContactFabMore.EContactFabMore.CLASSIC);
                    }
                    name.setVisibility(View.VISIBLE);
                    check.setVisibility(View.GONE);
                    current.setSelection(false);
                    --mSelected;
                } else {
                    displayMiniFab();
                    name.setVisibility(View.GONE);
                    check.setVisibility(View.VISIBLE);
                    current.setSelection(true);
                    ++mSelected;
                    mFabMore.setState(ContactFabMore.EContactFabMore.SELECT);
                }
                mFabDel.setState(ContactFabDelete.EContactDelete.CLASSIC);
                mFabAdd.setState(ContactFabAdd.EContactFabAdd.CLASSIC);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    private void defaultBinding(ContactViewHolder holder, Contact current) {
        holder.name.setText(current.getName());
        holder.phone.setText(current.getPhoneNumber());
        holder.letter.setText(current.getName().toUpperCase().subSequence(0, 1));
        if (current.ismFavor())
            holder.favor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_36dp));
        else
            holder.favor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_border_black_36dp));
        if (current.ismSelected()) {
            holder.letter.setVisibility(View.GONE);
            holder.selected.setVisibility(View.VISIBLE);
        } else {
            holder.letter.setVisibility(View.VISIBLE);
            holder.selected.setVisibility(View.GONE);
        }
    }

    private void closeFab(boolean all) {
        hideMiniFab();
        if (all)
            unSelectAllContacts();
        this.mExpanded = false;
    }

    private void unSelectAllContacts() {
        for (Contact contact : mContacts)
            contact.setSelection(false);
        notifyDataSetChanged();
    }

    private void deleteContacts() {
        List<Contact> newList = new ArrayList<>();
        for (Contact contact : mContacts)
            if (!contact.ismSelected())
                newList.add(contact);
        mOldList = mContacts;
        mContacts = newList;
        notifyDataSetChanged();
    }

    private void undo() {
        mContacts = mOldList;
        notifyDataSetChanged();
    }

    private void displayFab(FloatingActionButton fab, int visibility) {
        fab.setVisibility(visibility);
    }

    private void hideMiniFab() {
        displayFab(mFabDel, View.GONE);
        displayFab(mFabAdd, View.GONE);
        displayFab(mFabSort, View.GONE);
    }

    private void displayMiniFab() {
        displayFab(mFabDel, View.VISIBLE);
        displayFab(mFabAdd, View.VISIBLE);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contact, clickable;
        TextView name, phone, letter;
        ImageView avatar, favor, selected;

        public ContactViewHolder(View itemView) {
            super(itemView);
            contact = (LinearLayout) itemView.findViewById(R.id.contact);
            clickable = (LinearLayout) itemView.findViewById(R.id.contact_clickable);
            name = (TextView) itemView.findViewById(R.id.contact_name);
            phone = (TextView) itemView.findViewById(R.id.contact_phone);
            letter = (TextView) itemView.findViewById(R.id.contact_letter);
            avatar = (ImageView) itemView.findViewById(R.id.contact_avatar);
            favor = (ImageView) itemView.findViewById(R.id.contact_favor);
            selected = (ImageView) itemView.findViewById(R.id.contact_selected);
        }
    }

}

