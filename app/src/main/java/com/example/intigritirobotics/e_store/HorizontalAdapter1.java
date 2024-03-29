package com.example.intigritirobotics.e_store;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.intigritirobotics.R;

import java.util.List;

public class HorizontalAdapter1 extends RecyclerView.Adapter<HorizontalAdapter1.ViewHolder> {

    private final List<ViewAllModel> ViewAllModelList;

    public HorizontalAdapter1(List<ViewAllModel> ViewAllModelList) {
        this.ViewAllModelList = ViewAllModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_product_layout_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String index = ViewAllModelList.get(position).getId();
        String resource = ViewAllModelList.get(position).getImage();
        String title = ViewAllModelList.get(position).getTitle();
        int price = ViewAllModelList.get(position).getFinalPrice();
        float rating = ViewAllModelList.get(position).getTotalRating();
        viewHolder.setData(index,resource,title, price, rating);
    }

    @Override
    public int getItemCount() {
        return ViewAllModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView pic;
        private final TextView CategoryTitle;
        private final TextView ProductPrice;
        private final TextView ProductRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.horizontal_product_preview_pic);
            CategoryTitle = itemView.findViewById(R.id.horizontal_product_preview_title);
            ProductRating = itemView.findViewById(R.id.horizontal_product_preview_rating);
            ProductPrice = itemView.findViewById(R.id.horizontal_product_preview_price);
        }
        private void setData( final String index, String resource, String title, int price, float rating)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()
                    .placeholder(R.drawable.category_icon))
                    .into(pic);
            CategoryTitle.setText(title);
            String priceText = "Rs." + price +"/-";
            String ratingText = ""+ rating;
            ProductPrice.setText(priceText);
            ProductRating.setText(ratingText);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent( itemView.getContext(),ProductDetailActivity.class);
                intent.putExtra("Index",index);
                intent.putExtra("Title",title);
                intent.putExtra("ID", index);
                intent.putExtra("Price",""+price);
                intent.putExtra("Rating",""+rating);
                itemView.getContext().startActivity(intent);
            });

        }

    }

}