package com.example.momtobe.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.momtobe.R;
import com.example.momtobe.databinding.FragmentNutritionBinding;
import com.example.momtobe.models.NutritionAdvice;
import com.example.momtobe.remote_data.Api;
import com.example.momtobe.remote_data.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NutritionFragment extends Fragment {

    private FragmentNutritionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNutritionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonTrimester1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNutritionAdvice(1);
            }
        });

        binding.buttonTrimester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNutritionAdvice(2);
            }
        });

        binding.buttonTrimester3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNutritionAdvice(3);
            }
        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void fetchNutritionAdvice(int trimester) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        Api api = retrofitClient.getApi();
        Call<List<NutritionAdvice>> call = api.getNutritionAdviceByTrimester(trimester);
        call.enqueue(new Callback<List<NutritionAdvice>>() {
            @Override
            public void onResponse(Call<List<NutritionAdvice>> call, Response<List<NutritionAdvice>> response) {
                if (!response.isSuccessful()) {
                    Log.e("API", "Ошибка: " + response.code());
                    return;
                }

                List<NutritionAdvice> nutritionAdvices = response.body();
                if (nutritionAdvices != null) {
                    binding.containerNutritionAdvice.removeAllViews();
                    for (NutritionAdvice advice : nutritionAdvices) {
                        addNutritionAdviceToView(advice);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NutritionAdvice>> call, Throwable t) {
                Log.e("API", "Ошибка: " + t.getMessage());
            }
        });
    }

    private void addNutritionAdviceToView(NutritionAdvice advice) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_nutrition_advice, binding.containerNutritionAdvice, false);
//
//        TextView textViewDescription = view.findViewById(R.id.text_view_description);
//        ImageView imageViewPhoto = view.findViewById(R.id.image_view_photo);
//        TextView textViewWeek=view.findViewById(R.id.text_view_week);
//        TextView textViewTrimester=view.findViewById(R.id.text_view_trimester);
//
//        textViewWeek.setText(String.valueOf(advice.getWeek()));
//        textViewTrimester.setText(String.valueOf(advice.getTrimester()));
//        textViewDescription.setText("Семестр: %d",advice.getDescription());
        TextView textViewTrimester = view.findViewById(R.id.text_view_trimester);
        TextView textViewDescription = view.findViewById(R.id.text_view_description);
        TextView textViewWeek = view.findViewById(R.id.text_view_week);
        ImageView imageViewPhoto = view.findViewById(R.id.image_view_photo);

        // Convert int values to String and format text
        String trimesterText = String.format("Триместр: %d", advice.getTrimester());
        String weekText = String.format("Неделя: %d", advice.getWeek());
        String descriptionText = String.format("Описание: %s", advice.getDescription());

        // Set text to TextView components
        textViewTrimester.setText(trimesterText);
        textViewDescription.setText(descriptionText);
        textViewWeek.setText(weekText);

        Glide.with(this)
                .load(advice.getPhoto())
                .into(imageViewPhoto);

        binding.containerNutritionAdvice.addView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
