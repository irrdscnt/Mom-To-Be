package com.example.momtobe.ui.babyinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.momtobe.R;
import com.example.momtobe.models.Baby;
import com.example.momtobe.models.NutritionAdvice;
import com.example.momtobe.remote_data.Api;
import com.example.momtobe.remote_data.RetrofitClient;
import com.example.momtobe.databinding.FragmentBabyinfoBinding;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BabyInfoFragment extends Fragment {

    private FragmentBabyinfoBinding binding;
    private Api api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBabyinfoBinding.inflate(inflater, container, false);

        // Initialize the API service
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        api = retrofitClient.getApi();

        binding.buttonGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weekStr = binding.editTextWeek.getText().toString();
                if (!weekStr.isEmpty()) {
                    int week = Integer.parseInt(weekStr);
                    getBabyInfo(week);
                }
            }
        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return binding.getRoot();
    }

    private void getBabyInfo(int week) {
        Call<List<Baby>> call = api.getBabyInfoByTrimester(week);
        call.enqueue(new Callback<List<Baby>>() {
            @Override
            public void onResponse(Call<List<Baby>> call, Response<List<Baby>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Baby> babyInfoList = response.body();
                    if (!babyInfoList.isEmpty()) {
                        Baby baby = babyInfoList.get(0); // Assuming we are interested in the first item
                        String info = "Неделя: " + baby.getWeek() + "\n" +
                                "Триместр: " + baby.getTrimester() + "\n" +
                                "Описание: " + baby.getDescription();
                        binding.textViewInfo.setText(info);
                        binding.textViewInfo.setVisibility(View.VISIBLE);
                        binding.textphoto.setVisibility(View.VISIBLE);
                        // Load the photo using Glide
                        Glide.with(BabyInfoFragment.this)
                                .load(baby.getPhoto())
                                .into(binding.imageViewBabyPhoto);
                    } else {
                        binding.textViewInfo.setText("No information available for this week.");
                    }
                } else {
                    binding.textViewInfo.setText("Failed to retrieve information.");
                }
            }

            @Override
            public void onFailure(Call<List<Baby>> call, Throwable t) {
                binding.textViewInfo.setText("Error: " + t.getMessage());
            }
        });
    }
    public void onBackButtonClicked(View view) {
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
