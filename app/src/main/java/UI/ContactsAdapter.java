package UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactmanager.AddContactActivity;
import com.example.contactmanager.R;

import java.time.LocalDateTime;
import java.util.Arrays;
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
        holder.contactAddedDate.setText(String.format("Last modified: %s", contact.getContactAddedAt()));
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
            else if(buttonId==R.id.contact_edit){
                View view = LayoutInflater.from(context).inflate(R.layout.edit_contact_popup, (ViewGroup) itemView.getRootView(), false);

                EditText editName = view.findViewById(R.id.editText_edit_name);
                EditText editPhone = view.findViewById(R.id.editText_edit_phone);
                editName.setText(contactList.get(getAdapterPosition()).getContactName());
                editPhone.setText(contactList.get(getAdapterPosition()).getContactPhoneNumber());

                alertDialogBuilder.setTitle("Edit contact");
                alertDialogBuilder.setIcon(R.drawable.pencil_icon);

                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setView(view);

                alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updatedName = editName.getText().toString();
                        String updatedPhone = editPhone.getText().toString();
                        String[] updatedDateTime = toDateTime(LocalDateTime.now());
                        int contactId = contactList.get(getAdapterPosition()).getId();
                        db.updateContact(new Contact(contactId, updatedName, updatedPhone, Arrays.toString(updatedDateTime)));

                        Contact c = contactList.get(getAdapterPosition());
                        c.setContactName(updatedName);
                        c.setContactPhoneNumber(updatedPhone);
                        c.setContactAddedAt(Arrays.toString(updatedDateTime));
                        notifyItemChanged(getAdapterPosition());

                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        // Get the window and set width and height
                        if (alertDialog.getWindow() != null) {
                            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
                            int screenHeight = context.getResources().getDisplayMetrics().heightPixels;

                            alertDialog.getWindow().setLayout(
                                    (int) (screenWidth * 0.9),        // 90% of screen width
                                    (int) (screenHeight * 0.3)       // 50% of screen height
                            );
                        }
                    }
                });

                alertDialog.show();

            }
        }
    }
    public String[] toDateTime(LocalDateTime localDateTime){
        //2025-01-06T09:43:51.710641
        char[] overallDate = localDateTime.toString().toCharArray();
        StringBuilder tempDate = new StringBuilder(), tempTime=new StringBuilder();
        String date;
        String time;
        for(int j=0; j<overallDate.length; j++){
            if(j<=9){
                tempDate.append(overallDate[j]);
            } else if(j>=11&&j<=15){
                tempTime.append(overallDate[j]);
            } else if(j!=10){
                break;
            }
        }
        date = String.valueOf(tempDate);
        time = String.valueOf(tempTime);
        return new String[]{date, time};
    }
}
