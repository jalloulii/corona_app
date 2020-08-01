package com.example.coronastat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronastat.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Calendar;
import java.util.TimeZone;


public class CoronaLiveF extends Fragment {
    private TextInputLayout search_country_input;
    private Button btn_search;
    private TextView infected_number,deaths_number,recovered_number;
    private ProgressBar PB;

    public CoronaLiveF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_corona_live, container, false);

        search_country_input = view.findViewById(R.id.input_search_country);
        btn_search = view.findViewById(R.id.search_btn);
        infected_number = view.findViewById(R.id.infected_number);
        deaths_number = view.findViewById(R.id.deaths_number);
        recovered_number = view.findViewById(R.id.recovered_number);
        PB = view.findViewById(R.id.progressBar);

        getTwoWeeksCASES("tunisia");
        Toast.makeText(getActivity(), "tunisia values", Toast.LENGTH_SHORT).show();

    btn_search.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String country_name_str = search_country_input.getEditText().getText().toString() ;
            if(country_name_str.isEmpty()){

                Toast.makeText(getActivity(), "invalid country name", Toast.LENGTH_SHORT).show();

            }else {
                PB.setVisibility(View.VISIBLE);
                getTwoWeeksCASES(country_name_str);

            }
        }
    });


        return  view;
    }

    private void getTwoWeeksCASES(final String country_name_str) {
        String url ="https://covid-19-data.p.rapidapi.com/country?format=json&name="+country_name_str;
        Ion.with(getActivity())
                .load(url)
                .setHeader("x-rapidapi-host","covid-19-data.p.rapidapi.com")
                .setHeader("x-rapidapi-key","e53a291b93msh1a79879006c5817p1f951cjsnba6b12c4061b")
                .setHeader("useQueryString","true")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(e!=null){

                            Toast.makeText(getActivity(), "Exception "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        else
                        {
                            if(result.size()!=0){
                                JsonObject test = result.get(0).getAsJsonObject();
                                String confirmed = test.get("confirmed").getAsString() ;
                                String deaths= test.get("deaths").getAsString() ;
                                String recovered =test.get("recovered").getAsString() ;
                                infected_number.setText(confirmed);
                                deaths_number.setText(deaths);
                                recovered_number.setText(recovered);
                                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                                int day = calendar.get(Calendar.DAY_OF_WEEK);
                                int month = calendar.get(Calendar.MONTH)+1;
                                int year = calendar.get(Calendar.YEAR);
                                String date = year+"-"+month+"-"+day;
                                PB.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), country_name_str+" values", Toast.LENGTH_SHORT).show();
                        }else {
                                PB.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "invalid country name", Toast.LENGTH_SHORT).show();
                            }



                        }


                    }
                });
    }
}