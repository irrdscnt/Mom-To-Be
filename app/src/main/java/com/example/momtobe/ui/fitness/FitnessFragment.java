package com.example.momtobe.ui.fitness;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.momtobe.R;
import com.example.momtobe.databinding.FragmentFitnessBinding;
import com.example.momtobe.models.Fitness;

import com.example.momtobe.remote_data.Api;
import com.example.momtobe.remote_data.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FitnessFragment extends Fragment {

    private FragmentFitnessBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFitnessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonTrimester1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFitness(1);
            }
        });

        binding.buttonTrimester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFitness(2);
            }
        });

        binding.buttonTrimester3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFitness(3);
            }
        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
    private void fetchFitness(int trimester) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        Api api = retrofitClient.getApi();
        Call<List<Fitness>> call = api.getFitnessByTrimester(trimester);
        call.enqueue(new Callback<List<Fitness>>() {
            @Override
            public void onResponse(Call<List<Fitness>> call, Response<List<Fitness>> response) {
                if (!response.isSuccessful()) {
                    Log.e("API", "Ошибка: " + response.code());
                    return;
                }

                List<Fitness> fitness_list = response.body();
                if (fitness_list != null) {
                    binding.containerFitness.removeAllViews();
                    for (Fitness fitness : fitness_list) {
                        addFitnessToView(fitness);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Fitness>> call, Throwable t) {
                Log.e("API", "Ошибка: " + t.getMessage());
            }
        });
    }
    private void addFitnessToView(Fitness fitness) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_fitness, binding.containerFitness, false);

        TextView textViewTrimester = view.findViewById(R.id.text_view_trimester);
        TextView textViewDescription = view.findViewById(R.id.text_view_description);
        TextView textViewWeek = view.findViewById(R.id.text_view_week);
        ImageView imageViewPhoto = view.findViewById(R.id.image_view_photo);


        String trimesterText = String.format("Триместр: %d", fitness.getTrimester());
        String weekText = String.format("Неделя: %d", fitness.getWeek());
        String descriptionText = String.format("Описание: %s", fitness.getDescription());

        textViewTrimester.setText(trimesterText);
        textViewDescription.setText(descriptionText);
        textViewWeek.setText(weekText);

        Glide.with(this)
                .load(fitness.getPhoto())
                .into(imageViewPhoto);

        binding.containerFitness.addView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}