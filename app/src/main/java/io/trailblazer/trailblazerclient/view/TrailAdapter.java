/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.squareup.picasso.Picasso;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.view.TrailAdapter.Holder;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Trail adapter.
 */
public class TrailAdapter extends Adapter<Holder> {


  private final Context context;
  private final List<Trail> trails;
  private final OnClickListener clickListener;
  private final OnContextClickListener contextClickListener;

  /**
   * Instantiates a new Trail adapter.
   *
   * @param context       the context
   * @param clickListener the click listener
   */
  public TrailAdapter(Context context,
      OnClickListener clickListener,
      OnContextClickListener contextClickListener) {
    this.context = context;
    this.clickListener = clickListener;
    this.contextClickListener = contextClickListener;
    this.trails = new ArrayList<>();

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

  /**
   * Sets trails.
   *
   * @param trails the trails
   */
  public void setTrails(List<Trail> trails) {
    this.trails.clear();
    this.trails.addAll(trails);
  }

  /**
   * The interface On click listener.
   */
  @FunctionalInterface
  public interface OnClickListener {

    /**
     * On click.
     *
     * @param view     the view
     * @param position the position
     * @param trail    the trail
     */
    void onClick(View view, int position, Trail trail);
  }

  public interface OnContextClickListener {

    /**
     * On click.
     *
     * @param position the position
     * @param trail    the trail
     */
    void onLongClick(Menu menu, int position, Trail trail);
  }


  /**
   * The type Holder.
   */
  public class Holder extends ViewHolder {

    private final View view;
    private ImageView background;
    private TextView creator;
    private TextView trailName;

    /**
     * Instantiates a new Holder.
     *
     * @param itemView the item view
     */
    public Holder(@NonNull View itemView) {
      super(itemView);
      background = itemView.findViewById(R.id.trail_image_background);
      creator = itemView.findViewById(R.id.creator_name);
      trailName = itemView.findViewById(R.id.trail_name);
      view = itemView;
    }

    private void bind(int position, Trail trail) {
      if (trail.getImageUrl() == null) {
        Picasso.get().load(R.mipmap.trail_background).into(background);
      }
      creator.setText(trail.getCreator().getUsername());
      trailName.setText(trail.getName());

      view.setOnClickListener((view) -> {
        clickListener.onClick(view, position, trail);
      });

      view.setOnCreateContextMenuListener((menu, v, menuInfo) ->
          contextClickListener.onLongClick(menu, position, trail));
    }
  }

}
