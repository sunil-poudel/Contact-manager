package UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactmanager.R;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{
    Context context;
    List<Contact> contactList;
    DatabaseHandler db;

    public ContactsAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
        db = new DatabaseHandler(context);

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView contactName;
        private TextView contactPhoneNumber;
        private TextView contactAddedDate;
        private ImageButton contactEditButton;
        private ImageButton contactDeleteButton;
        private AlertDialog.Builder alertDialogBuilder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.contact_name);
            contactPhoneNumber = itemView.findViewById(R.id.contact_phone_number);
            contactAddedDate = itemView.findViewById(R.id.contact_date_added);
            contactEditButton = itemView.findViewById(R.id.contact_edit);
            contactDeleteButton = itemView.findViewById(R.id.contact_delete);

            contactDeleteButton.setOnClickListener(this);
            contactEditButton.setOnClickListener(this);

            alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());

        }

        @Override
        public void onClick(View v) {
            int buttonId = v.getId();
            int position = getAdapterPosition();

            if(buttonId==R.id.contact_delete){
//                Log.d("DELETE", "Delete contact: name->"+contactList.get(position).getContactName()+", phone->"+contactList.get(position).getContactPhoneNumber());
                alertDialogBuilder.setTitle("Alert!");
                alertDialogBuilder.setIcon(R.drawable.cross_icon);

                alertDialogBuilder.setMessage("Are you sure you want to delete this contact?\nThis action can't be undone!!!");

                alertDialogBuilder.setCancelable(true);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteContact(contactList.get(position));

                        // Remove the contact from the list and notify the adapter
                        contactList.remove(position); // Remove the item from your local list
                        notifyItemRemoved(position); // Notify RecyclerView that an item was removed
//                notifyItemRangeChanged(position, contactList.size()); // Optional: Refresh item positions

                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
    }
}
