package leonardo2204.com.br.flowtests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import leonardo2204.com.br.flowtests.model.Contact;
import leonardo2204.com.br.flowtests.view.FirstView;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private final List<Contact> contacts;
    private FirstView.ContactListener contactListener;

    public ContactsAdapter(@NonNull List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setContactListener(FirstView.ContactListener contactListener) {
        this.contactListener = contactListener;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, final int position) {
        holder.name.setText(contacts.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContactsAdapter.this.contactListener != null) {
                    ContactsAdapter.this.contactListener.onClick(contacts.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ContactsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.contact_name)
        TextView name;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
