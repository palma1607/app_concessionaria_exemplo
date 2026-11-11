package br.com.unicuritiba.appconcessionaria;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class PlataformaRepository {

    public void getPlataformas(PlataformaListener listener){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2") //Ou endereço IP do computador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlataformaService service = retrofit.create(PlataformaService.class);

        service.getPlataformas().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Plataforma>> call,
                                   Response<List<Plataforma>> response) {

                if(response.isSuccessful()){

                    List<Plataforma> plataformas = response.body();
                    listener.onReceive(plataformas);

                }else {
                    listener.onReceive(null);
                }
            }

            @Override
            public void onFailure(Call<List<Plataforma>> call,
                                  Throwable t) {
                listener.onReceive(null);
            }
        });

    }

    public interface PlataformaListener{
        void onReceive(List<Plataforma> plataformas);
    }

    public static class Plataforma{

        private long id;
        private String nome;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }


    public interface PlataformaService{

        @GET("plataforma")
        Call<List<Plataforma>> getPlataformas();

    }

}
