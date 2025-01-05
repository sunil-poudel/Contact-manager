package UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactmanager.R;

import java.util.List;

import Model.Contact;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{
    Context context;
    List<Contact> contactList;

    public ContactsAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.contactName.setText(contact.getContactName());
        holder.contactPhoneNumber.setText(contact.getContactPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView contactName;
        private TextView contactPhoneNumber;
        private TextView contactAddedDate;
        private ImageButton contactEditButton;
        private ImageButton contactDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.contact_name);
            contactPhoneNumber = itemView.findViewById(R.id.contact_phone_number);
            contactAddedDate = itemView.findViewById(R.id.contact_date_added);
            contactEditButton = itemView.findViewById(R.id.contact_edit);
            contactDeleteButton = itemView.findViewById(R.id.contact_delete);


        }
    }
}
