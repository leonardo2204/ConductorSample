package leonardo2204.com.br.flowtests;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import leonardo2204.com.br.flowtests.model.Contact;

/**
 * Created by Leonardo on 05/03/2016.
 */
public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int HEADER_VIEW = 1;
    private final static int CONTACT_VIEW = 2;
    int headerCount = 0;
    Character previousChar;
    private List<Contact> contacts;
    private OnClickListener onClickListener;

    public ContactsAdapter() {
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void clearAdapter() {
        this.contacts.clear();
        notifyDataSetChanged();
    }

    public List<Contact> getContacts() {
        return Collections.unmodifiableList(contacts);
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CONTACT_VIEW)
            return new ContactsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false));
        else if (viewType == HEADER_VIEW)
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_header, parent, false));
        else
            throw new IllegalStateException("Type not declared");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Contact contact = contacts.get(position);

        if (holder instanceof ContactsViewHolder) {
            ((ContactsViewHolder) holder).name.setText(contact.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContactsAdapter.this.onClickListener != null) {
                        ContactsAdapter.this.onClickListener.onClick(contact);
                    }
                }
            });

        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).initialLetter.setText(String.valueOf(contact.getName().charAt(0)));
            ++headerCount;
        } else {
            throw new IllegalStateException("Type not declared");
        }

        previousChar = contact.getName().charAt(0);
    }

    @Override
    public int getItemViewType(int position) {
//        if(isHeaderView(position)){
//            return HEADER_VIEW;
//        }else{
        return CONTACT_VIEW;
        //   }
    }

    private boolean isHeaderView(final int position) {
        return position == 0 || (position == getItemCount() - 1 || contacts.get(position).getName().charAt(0) != contacts.get(position + 1).getName().charAt(0));
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    public interface OnClickListener {
        void onClick(Contact contact);
    }

    static class ContactsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.contact_name)
        TextView name;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.initial_letter)
        TextView initialLetter;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
