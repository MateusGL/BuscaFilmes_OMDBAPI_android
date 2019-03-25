package com.example.mateu.buscafilmes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ResutadoBusca extends AppCompatActivity {

    private TextView Titulo;
    private TextView Ano;
    private TextView Classificacao;
    private TextView Lancamento;
    private TextView Genero;
    private TextView Duracao;
    private ImageView foto;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resutado_busca);

        GetJson download = new GetJson();

        Titulo = (TextView)findViewById(R.id.textView2);
        Ano = (TextView)findViewById(R.id.textView3);
        Classificacao = (TextView)findViewById(R.id.textView4);
        Lancamento = (TextView)findViewById(R.id.textView5);
        Genero = (TextView)findViewById(R.id.textView6);
        Duracao = (TextView)findViewById(R.id.textView7);
        foto = (ImageView) findViewById(R.id.imageView);

        //Chama Async Task
        download.execute();
    }

    private class GetJson extends AsyncTask<Void, Void, FilmeObj> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(ResutadoBusca.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected FilmeObj doInBackground(Void... params) {
            Utils util = new Utils();

            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            TextView textView = findViewById(R.id.ResultadoBusca);
            textView.setText("Titulo buscado: " + message);
            message = message.replace( " ", "+" );

            String URL = "http://www.omdbapi.com/?t=" + message + "&apikey=a45a2a24";

            return util.getInformacao(URL);

            //return util.getInformacao("http://www.omdbapi.com/?i=tt3896198&apikey=a45a2a24");
            //return util.getInformacao("http://www.omdbapi.com/?t=Avengers&apikey=a45a2a24");
        }

        @Override
        protected void onPostExecute(FilmeObj filme){
            Titulo.setText(filme.getTitulo());
            Ano.setText(filme.getAno());
            Classificacao.setText(filme.getClassificacao());
            Lancamento.setText(filme.getLancamento());
            Genero.setText(filme.getGenero());
            Duracao.setText(filme.getduracao());
            foto.setImageBitmap(filme.getPoster());
            load.dismiss();
        }
    }

    public class FilmeObj {
        private String Titulo;
        private String Ano;
        private String Classificacao;
        private String Lancamento;
        private String duracao;
        private String Genero;
        private String Diretor;
        private String Escritor;
        private String Atores;
        private String Enredo;
        private String Idioma;
        private String Pais;
        private String Premios;
        private Bitmap Poster;


        public String getTitulo() {
            return Titulo;
        }

        public void setTitulo(String nome) {
            this.Titulo = nome;
        }

        public String getAno() {
            return Ano;
        }

        public void setAno(String Ano) {
            this.Ano = Ano;
        }

        public String getClassificacao() {
            return Classificacao;
        }

        public void setClassificacao(String Classificacao) {
            this.Classificacao = Classificacao;
        }

        public String getLancamento() {
            return Lancamento;
        }

        public void setLancamento(String Lancamento) {
            this.Lancamento = Lancamento;
        }

        public String getduracao() {
            return duracao;
        }

        public void setDuracao(String duracao) {
            this.duracao = duracao;
        }

        public String getGenero() {
            return Genero;
        }

        public void setGenero(String Genero) {
            this.Genero = Genero;
        }

        public String getDiretor() {
            return Diretor;
        }

        public void setDiretor(String Diretor) {
            this.Diretor = Diretor;
        }

        public String getEscritor() {
            return Escritor;
        }

        public void setEscritor(String Escritor) {
            this.Escritor = Escritor;
        }

        public String getAtores() {
            return Atores;
        }

        public void setAtores(String Atores) {
            this.Atores = Atores;
        }

        public String getEnredo() {
            return Enredo;
        }

        public void setEnredo(String Enredo) {
            this.Enredo = Enredo;
        }

        public String getIdioma() {
            return Idioma;
        }

        public void setIdioma(String Idioma) {
            this.Idioma = Idioma;
        }

        public String getPais() {
            return Pais;
        }

        public void setPais(String Pais) {
            this.Pais = Pais;
        }

        public String getPremios() {
            return Premios;
        }

        public void setPremios(String Premios) {
            this.Premios = Premios;
        }

        public Bitmap getPoster() {
            return Poster;
        }

        public void setPoster(Bitmap Poster) {
            this.Poster = Poster;
        }

    }

    public class Utils {

        public FilmeObj getInformacao(String end){
            String json;
            FilmeObj retorno;
            json = NetworkUtils.getJSONFromAPI(end);
            Log.i("Resultado", json);
            retorno = parseJson(json);

            return retorno;
        }

        private FilmeObj parseJson(String json){
            try {
                FilmeObj Filme = new FilmeObj();

                //JSONObject jsonObj = new JSONObject(json);
                JSONObject obj = new JSONObject(json);

                Filme.setTitulo("Titulo: " + obj.getString("Title"));
                Filme.setAno("Ano: " + obj.getString("Year"));
                Filme.setClassificacao("Classificação: " + obj.getString("Rated"));
                Filme.setLancamento("Data de lançamento: " + obj.getString("Released"));
                Filme.setDuracao("Duração: " + obj.getString("Runtime"));
                Filme.setGenero("Genero: " + obj.getString("Genre"));
                Filme.setDiretor("Diretor: " + obj.getString("Director"));
                Filme.setEscritor("Escritor: " + obj.getString("Writer"));
                Filme.setAtores("Atores: " + obj.getString("Actors"));
                Filme.setEnredo("Enredo: " + obj.getString("Plot"));
                Filme.setIdioma("Idioma: " + obj.getString("Language"));
                Filme.setPais("Pais: " + obj.getString("Country"));
                Filme.setPremios("Premios: " + obj.getString("Awards"));
                Filme.setPoster(baixarImagem(obj.getString("Poster")));

                return Filme;

            }catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }

        private Bitmap baixarImagem(String url) {
            try{
                URL endereco;
                InputStream inputStream;
                Bitmap imagem; endereco = new URL(url);
                inputStream = endereco.openStream();
                imagem = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return imagem;
            }catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class NetworkUtils {

        //Responsavel por carregar o Objeto JSON
        public static String getJSONFromAPI(String url){
            String retorno = "";
            try {
                URL apiEnd = new URL(url);
                int codigoResposta;
                HttpURLConnection conexao;
                InputStream is;

                conexao = (HttpURLConnection) apiEnd.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.connect();

                codigoResposta = conexao.getResponseCode();
                if(codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                    is = conexao.getInputStream();
                }else{
                    is = conexao.getErrorStream();
                }

                retorno = converterInputStreamToString(is);
                is.close();
                conexao.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            return retorno;
        }

        private static String converterInputStreamToString(InputStream is){
            StringBuffer buffer = new StringBuffer();
            try{
                BufferedReader br;
                String linha;

                br = new BufferedReader(new InputStreamReader(is));
                while((linha = br.readLine())!=null){
                    buffer.append(linha);
                }

                br.close();
            }catch(IOException e){
                e.printStackTrace();
            }

            return buffer.toString();
        }
    }

}

