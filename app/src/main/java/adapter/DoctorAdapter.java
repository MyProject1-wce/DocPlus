package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.docplus.Make_Appointment;
import com.example.dell.docplus.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 29-10-2017.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {
    private Context context;
    ArrayList <Model> models;
   // public static View.OnClickListener vonclick;
    public DoctorAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,null);
        DoctorHolder doctorHolder=new DoctorHolder(view);
        return doctorHolder;
    }
/*Image from firebase
FirebaseStorage storage = FirebaseStorage.getInstance();
StorageReference storageRef = storage.getReferenceFromUrl("gs://teszt-cd548.appspot.com");
StorageReference pathReference = storageRef.child("images/cross.png");

Glide.with(context)
       .using(new FirebaseImageLoader())
       .load(pathReference)
       .into(iv);*/
    @Override
    public void onBindViewHolder(DoctorHolder holder, final int position) {
        holder.circleImageView.setImageResource(models.get(position).getImageId());
        holder.name.setText(models.get(position).getName());
        holder.special.setText(models.get(position).getSpecial());
        holder.experience.setText(models.get(position).getExperience());
        holder.cname.setText(models.get(position).getCname());
        holder.cadd.setText(models.get(position).getCadd());
        holder.cbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Hello this is me!",Toast.LENGTH_LONG).show();
                String uid =models.get(position).getUserid();
                Intent intent=new Intent(view.getContext(), Make_Appointment.class);
                intent.putExtra("uid",uid);
                view.getContext().startActivity(intent);
            }
        });
          /* holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Hello this is me!",Toast.LENGTH_LONG).show();
            }
        });*/

    }



    @Override
    public int getItemCount() {
        return models.size();
    }


    public static class DoctorHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView name,special,experience,cname,cadd,cbook;
        //View view;
        public DoctorHolder(View itemView) {
            super(itemView);
            //view=itemView;
            circleImageView= (CircleImageView) itemView.findViewById(R.id.item_card_prof_image);
            name= (TextView) itemView.findViewById(R.id.item_card_name);
            special= (TextView) itemView.findViewById(R.id.item_card_speciality);
            experience= (TextView) itemView.findViewById(R.id.item_card_experience);
            cadd= (TextView) itemView.findViewById(R.id.item_card_cadd);
            cname= (TextView) itemView.findViewById(R.id.item_card_cname);
            cbook=(TextView)itemView.findViewById(R.id.item_card_bookapt);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Hello this is me!",Toast.LENGTH_LONG).show();

                }
            });*/

        }
       /* public  void setIcon(String url){
            Picasso.with(context)
                    .load(url)
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageView)
        }*/

    }
}
