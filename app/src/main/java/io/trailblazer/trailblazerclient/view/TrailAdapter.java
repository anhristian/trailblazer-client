package io.trailblazer.trailblazerclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.view.TrailAdapter.Holder;
import java.util.List;

public class TrailAdapter extends Adapter<Holder> {


  private final Context context;
  private final List<Trail> trails;

  public TrailAdapter(Context context,
      List<Trail> trails) {
    this.context = context;
    this.trails = trails;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.trail_item, parent, false);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    Trail trail = trails.get(position);
    holder.bind(position, trail);
  }

  @Override
  public int getItemCount() {
    return trails.size();
  }

  public class Holder extends ViewHolder {

    TextView creator;
    TextView trailName;

    public Holder(@NonNull View itemView) {
      super(itemView);
      creator = itemView.findViewById(R.id.creator_name);
      trailName = itemView.findViewById(R.id.trail_name);
    }

    private void bind(int position, Trail trail) {
//      Picasso.get().load(new File(trail.getUrl())).into((ImageView) view.findViewById(R.id.gallery_image));
      trailName.setText(trail.getName());

    }
  }

}
