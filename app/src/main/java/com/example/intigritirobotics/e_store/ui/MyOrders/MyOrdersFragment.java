package com.example.intigritirobotics.e_store.ui.MyOrders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.intigritirobotics.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.intigritirobotics.e_store.MainHomeActivity.HomeloadingDialog;
import static com.example.intigritirobotics.e_store.MainHomeActivity.currentUserUId;
import static com.example.intigritirobotics.e_store.MainHomeActivity.firebaseFirestore;

public class MyOrdersFragment extends Fragment {
    private final List<MyOrderModel> myOrderModels = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        HomeloadingDialog.show();

        recyclerView =view.findViewById(R.id.my_orders_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());

        loadOrders();

        return view;
    }
    private void loadOrders() {
        firebaseFirestore.collection("/USERS/"+ currentUserUId +"/My Orders").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                myOrderModels.clear();

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    firebaseFirestore.document("/ORDERS/"+ documentSnapshot.get("order Id"))
                            .get()
                            .addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()) {
                                    DocumentSnapshot orderSnap = task1.getResult();
                                    myOrderModels.add(new MyOrderModel(
                                            orderSnap.getId(),
                                            orderSnap.get("order date").toString(),
                                            orderSnap.get("productQsIds").toString(),
                                            orderSnap.get("order status").toString(),
                                            orderSnap.get("productQty").toString(),
                                            orderSnap.get("productPrice").toString())
                                    );

                                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                    recyclerView.setLayoutManager(linearLayoutManager); // <-- Null Pointer Exception
                                    MyOrderAdapter adapter = new MyOrderAdapter(myOrderModels);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }
                            });

                }

            } else {
                String error = task.getException().getMessage();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                HomeloadingDialog.dismiss();

            }
            HomeloadingDialog.dismiss();


        });

    }

}